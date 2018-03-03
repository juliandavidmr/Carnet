package crop;

import java.awt.Point;
import java.awt.geom.Ellipse2D;

public class Shape {

    private final Point center;   // fields
    private final int radius;
    private Ellipse2D ellipse;

    // constructor(s)
    public Shape(Point center, int radius) {
        this.center = center;
        this.radius = radius;
    }

    public boolean contains(Point p) {
        this.ellipse = new Ellipse2D.Double(
                0,
                0,
                radius * Config.RESIZE_WIDTH,
                radius * Config.RESIZE_HEIGHT
        );
        return this.ellipse.contains(p);
        // return this.center.distance(p) <= this.radius;
    }
}
