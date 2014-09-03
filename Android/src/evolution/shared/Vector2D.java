package evolution.shared;

public class Vector2D {

	double x;
	double y;
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void add(Vector2D vector) {
		this.x += vector.x;
		this.y += vector.x;
	}
	
	public static Vector2D add(Vector2D v1, Vector2D v2) {
		return new Vector2D(v1.x + v2.x, v1.y + v2.y);
	}

	public void sub(Vector2D vector) {
		this.x -= vector.x;
		this.y -= vector.y;
	}
	
	public static Vector2D sub(Vector2D v1, Vector2D v2) {
		return new Vector2D(v1.x - v2.x, v1.y - v2.y);
	}

	public void mult(double scalar) {
		this.x *= scalar;
		this.y *= scalar;
	}
	
	public static Vector2D mult(Vector2D vector, double scalar) {
		return new Vector2D(vector.x * scalar, vector.y * scalar);
	}

	public void div(double scalar) {
		this.x /= scalar;
		this.y /= scalar;
	}
	
	public static Vector2D div(Vector2D vector, double scalar) {
		return new Vector2D(vector.x / scalar, vector.y / scalar);
	}

	public void normalize() {
		if (!Utils.nearlyEqual(sqMag(), 0.0)) {
			div(mag());
		}
	}
	
	public double dist(Vector2D vector) {
		return dist(this, vector);
	}
	
	public static double dist(Vector2D v1, Vector2D v2) {
		Vector2D delta = sub(v1, v2);
		return delta.mag();
	}

	public double dot(Vector2D v) {
		return dot(this, v);
	}
	
	public static double dot(Vector2D v1, Vector2D v2) {
		return (v1.x * v2.x + v1.y * v2.y);
	}
	
	public double mag() {
		return (double)Math.sqrt(sqMag());
	}
	
	public double sqMag() {
		return x * x + y * y;
	}
	
	public void setMag(double magnitude) {
		normalize();
		mult(magnitude);
	}
	
	public void limit(double max) {
		if (sqMag() > max * max) {
			setMag(max);
		}
	}
}
