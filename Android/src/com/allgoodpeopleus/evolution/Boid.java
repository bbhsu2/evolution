package com.allgoodpeopleus.evolution;

import java.util.ArrayList;

import evolution.shared.Vector2D;

public class Boid {
	Vector2D location;
	Vector2D velocity;
	Vector2D acceleration;
	double r;
	double maxforce; // Maximum steering force
	double maxspeed; // Maximum speed

	public Boid(double x, double y) {
		acceleration = new Vector2D(0.0, 0.0);

		// This is a new Vector2D method not yet implemented in JS
		// velocity = Vector2D.random2D();
		
		// Leaving the code temporarily this way so that this example runs in JS
		double angle = Math.random() * 2.0 * Math.PI;
		velocity = new Vector2D(Math.cos(angle), Math.sin(angle));
		
		location = new Vector2D(x, y);
		r = 2.0;
		maxspeed = 2.0;
		maxforce = 0.03;
	}

	void run(ArrayList<Boid> boids) {
		flock(boids);
		update();
		//borders();
		//render();
	}

	void applyForce(Vector2D force) {
		// We could add mass here if we want A = F / M
		acceleration.add(force);
	}

	// We accumulate a new acceleration each time based on three rules
	void flock(ArrayList<Boid> boids) {
		Vector2D sep = separate(boids);   // Separation
		Vector2D ali = align(boids);      // Alignment
		Vector2D coh = cohesion(boids);   // Cohesion
		// Arbitrarily weight these forces
		sep.mult(1.5);
		ali.mult(1.0);
		coh.mult(1.0);
		// Add the force vectors to acceleration
		applyForce(sep);
		applyForce(ali);
		applyForce(coh);
	}

	// Method to update location
	void update() {
		// Update velocity
		velocity.add(acceleration);
		// Limit speed
		velocity.limit(maxspeed);
		location.add(velocity);
		// Reset accelertion to 0 each cycle
		acceleration.mult(0.0);
	}

	// A method that calculates and applies a steering force towards a target
	// STEER = DESIRED MINUS VELOCITY
	Vector2D seek(Vector2D target) {
		Vector2D desired = Vector2D.sub(target, location);  // A vector pointing from the location to the target
		// Scale to maximum speed
		desired.normalize();
		desired.mult(maxspeed);
	
		// Above two lines of code below could be condensed with new Vector2D setMag() method
		// Not using this method until Processing.js catches up
		// desired.setMag(maxspeed);
		
		// Steering = Desired minus Velocity
		Vector2D steer = Vector2D.sub(desired, velocity);
		steer.limit(maxforce);  // Limit to maximum steering force
		return steer;
	}
	
	/*
	void render() {
		// Draw a triangle rotated in the direction of velocity
		double theta = velocity.heading2D() + radians(90);
		// heading2D() above is now heading() but leaving old syntax until Processing.js catches up
		
		fill(200, 100);
		stroke(255);
		pushMatrix();
		translate(location.x, location.y);
		rotate(theta);
		beginShape(TRIANGLES);
		vertex(0, -r*2);
		vertex(-r, r*2);
		vertex(r, r*2);
		endShape();
		popMatrix();
	}
	*/
	
	/*
	// Wraparound
	void borders() {
		if (location.x < -r) location.x = width+r;
		if (location.y < -r) location.y = height+r;
		if (location.x > width+r) location.x = -r;
		if (location.y > height+r) location.y = -r;
	}
	*/
	
	// Separation
	// Method checks for nearby boids and steers away
	Vector2D separate(ArrayList<Boid> boids) {
		double desiredseparation = 25.0;
		Vector2D steer = new Vector2D(0.0, 0.0);
		int count = 0;
		// For every boid in the system, check if it's too close
		for (Boid other : boids) {
			double d = Vector2D.dist(location, other.location);
			// If the distance is greater than 0 and less than an arbitrary amount (0 when you are yourself)
			if ((d > 0.0) && (d < desiredseparation)) {
				// Calculate vector pointing away from neighbor
				Vector2D diff = Vector2D.sub(location, other.location);
				diff.normalize();
				diff.div(d);        // Weight by distance
				steer.add(diff);
				count++;            // Keep track of how many
			}
		}
		// Average -- divide by how many
		if (count > 0) {
			steer.div((double)count);
		}
		
		// As long as the vector is greater than 0
		if (steer.mag() > 0.0) {
			// First two lines of code below could be condensed with new Vector2D setMag() method
			// Not using this method until Processing.js catches up
			// steer.setMag(maxspeed);
			
			// Implement Reynolds: Steering = Desired - Velocity
			steer.normalize();
			steer.mult(maxspeed);
			steer.sub(velocity);
			steer.limit(maxforce);
		}
		return steer;
	}
	
	// Alignment
	// For every nearby boid in the system, calculate the average velocity
	Vector2D align(ArrayList<Boid> boids) {
		double neighbordist = 50.0;
		Vector2D sum = new Vector2D(0, 0);
		int count = 0;
		for (Boid other : boids) {
			double d = Vector2D.dist(location, other.location);
			if ((d > 0.0) && (d < neighbordist)) {
				sum.add(other.velocity);
				count++;
			}
		}
		if (count > 0) {
			sum.div((double)count);
			// First two lines of code below could be condensed with new Vector2D setMag() method
			// Not using this method until Processing.js catches up
			// sum.setMag(maxspeed);
			
			// Implement Reynolds: Steering = Desired - Velocity
			sum.normalize();
			sum.mult(maxspeed);
			Vector2D steer = Vector2D.sub(sum, velocity);
			steer.limit(maxforce);
			return steer;
		} else {
			return new Vector2D(0.0, 0.0);
		}
	}
	
	// Cohesion
	// For the average location (i.e. center) of all nearby boids, calculate steering vector towards that location
	Vector2D cohesion(ArrayList<Boid> boids) {
		double neighbordist = 50.0;
		Vector2D sum = new Vector2D(0.0, 0.0); // Start with empty vector to accumulate all locations
		int count = 0;
		for (Boid other : boids) {
			double d = Vector2D.dist(location, other.location);
			if ((d > 0.0) && (d < neighbordist)) {
				sum.add(other.location); // Add location
				count++;
			}
		}
		if (count > 0) {
			sum.div(count);
			return seek(sum); // Steer towards the location
		} else {
			return new Vector2D(0.0, 0.0);
		}
	}
}
