import java.util.ArrayList;

public class StudentMain {
    public static void main(String[] args) {
        ArrayList<Student> students = new ArrayList<>();

        // 10 generic students plus myself, kept address very generic due to not sorting on this field
        students.add(new Student(10, "Greg Fritz", "Aurora, CO"));
        students.add(new Student(8, "Student 9", "City, State"));
        students.add(new Student(2, "Student 3", "City, State"));
        students.add(new Student(1, "Student 2", "City, State"));
        students.add(new Student(4, "Student 4", "City, State"));
        students.add(new Student(6, "Student 8", "City, State"));
        students.add(new Student(3, "Student 6", "City, State"));
        students.add(new Student(5, "Student 7", "City, State"));
        students.add(new Student(7, "Student 5", "City, State"));
        students.add(new Student(9, "Student 1", "City, State"));

        System.out.println("Original list:");
        for (Student s : students) System.out.println(s);

        // Sort by name
        SelectionSorter.selectionSort(students, new NameComparator());
        System.out.println("\nSorted by Name:");
        for (Student s : students) System.out.println(s);

        // Sort by roll number
        SelectionSorter.selectionSort(students, new RollNoComparator());
        System.out.println("\nSorted by Roll Number:");
        for (Student s : students) System.out.println(s);
    }
}