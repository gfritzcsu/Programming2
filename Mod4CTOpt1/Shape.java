/**
 * Abstract base class for 3D shapes.
 */
public abstract class Shape {
    /**
     * Compute the surface area of the shape.
     * @return surface area as a double
     */
    public abstract double surface_area();

    /**
     * Compute the volume of the shape.
     * @return volume as a double
     */
    public abstract double volume();
}
