package com.allgoodpeopleus.evolution;

import java.util.ArrayList;

public class Flock {

	ArrayList<Boid> boids; // An ArrayList for all the boids
	
	public Flock() {
		boids = new ArrayList<Boid>(); // Initialize the ArrayList
	}
	
	void run() {
		for (Boid b : boids) {
			b.run(boids); // Passing the entire list of boids to each boid individually
		}
	}

	void addBoid(Boid b) {
		boids.add(b);
	}
}
