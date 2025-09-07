/**
 * Represents a sphere with a given radius.
 */
public class Sphere extends Shape {
    private final double radius;

    /**
     * Construct a Sphere.
     * @param radius radius of the sphere (must be >= 0)
     * @throws IllegalArgumentException if radius is negative
     */
    public Sphere(double radius) {
        if (radius < 0) {
            throw new IllegalArgumentException("Radius must be non-negative.");
        }
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double surface_area() {
        // 4πr^2
        return 4.0 * Math.PI * radius * radius;
    }

    @Override
    public double volume() {
        // (4/3)πr^3
        return (4.0 / 3.0) * Math.PI * radius * radius * radius;
    }

    @Override
    public String toString() {
        return String.format(
            "Sphere { radius=%.2f, surface_area=%.4f, volume=%.4f }",
            radius, surface_area(), volume()
        );
    }
}
