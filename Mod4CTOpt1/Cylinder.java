/**
 * Represents a right circular cylinder with radius and height.
 */
public class Cylinder extends Shape {
    private final double radius;
    private final double height;

    /**
     * Construct a Cylinder.
     * @param radius radius of the cylinder (must be >= 0)
     * @param height height of the cylinder (must be >= 0)
     * @throws IllegalArgumentException if any dimension is negative
     */
    public Cylinder(double radius, double height) {
        if (radius < 0 || height < 0) {
            throw new IllegalArgumentException("Radius and height must be non-negative.");
        }
        this.radius = radius;
        this.height = height;
    }

    public double getRadius() {
        return radius;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public double surface_area() {
        // 2πr(h + r)
        return 2.0 * Math.PI * radius * (height + radius);
    }

    @Override
    public double volume() {
        // πr^2 h
        return Math.PI * radius * radius * height;
    }

    @Override
    public String toString() {
        return String.format(
            "Cylinder { radius=%.2f, height=%.2f, surface_area=%.4f, volume=%.4f }",
            radius, height, surface_area(), volume()
        );
    }
}
