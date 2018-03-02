package crop;

import java.awt.*;   // for Graphics

// Each Circle object represents a circle in the 2D plane
// with a given center and radius.
public class Circle {

    private Point center;   // fields
    private int radius;

    // constructor(s)
    public Circle(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    // Returns the area of this circle.
    public double getArea() {
        return Math.PI * Math.pow(this.radius, 2);
    }

    // Returns the circumference of this circle (distance around the circle).
    public double getCircumference() {
        return 2 * Math.PI * this.radius;
    }

    // Returns whether the given point lies inside this circle.
    public boolean contains(Point p) {
        return this.center.distance(p) <= this.radius;
    }

}
