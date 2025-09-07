/**
 * Represents a right circular cone with radius and height.
 */
public class Cone extends Shape {
    private final double radius;
    private final double height;

    /**
     * Construct a Cone.
     * @param radius radius of the cone (must be >= 0)
     * @param height height of the cone (must be >= 0)
     * @throws IllegalArgumentException if any dimension is negative
     */
    public Cone(double radius, double height) {
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
        // πr(r + s) where s = sqrt(r^2 + h^2)
        double slant = Math.sqrt(radius * radius + height * height);
        return Math.PI * radius * (radius + slant);
    }

    @Override
    public double volume() {
        // (1/3)πr^2 h
        return (1.0 / 3.0) * Math.PI * radius * radius * height;
    }

    @Override
    public String toString() {
        return String.format(
            "Cone { radius=%.2f, height=%.2f, surface_area=%.4f, volume=%.4f }",
            radius, height, surface_area(), volume()
        );
    }
}
