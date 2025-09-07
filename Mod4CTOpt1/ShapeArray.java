/**
 * Driver class to demonstrate Shape polymorphism.
 */
public class ShapeArray {
    public static void main(String[] args) {
        // Instantiate one of each shape
        Shape s1 = new Sphere(3.0);        // radius = 3.0
        Shape s2 = new Cylinder(2.0, 5.0);  // radius = 2.0, height = 5.0
        Shape s3 = new Cone(2.5, 4.0);      // radius = 2.5, height = 4.0

        // Store in an array
        Shape[] shapeArray = { s1, s2, s3 };

        // Loop and print each using toString() to display results to terminal
        for (Shape s : shapeArray) {
            System.out.println(s.toString());
        }
    }
}
