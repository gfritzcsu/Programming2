public class Student {
    private int rollno;
    private String name;
    private String address;

    public Student(int rollno, String name, String address) {
        this.rollno = rollno;
        this.name = name;
        this.address = address;
    }

    public int getRollno() { return rollno; }
    public String getName() { return name; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
    return "Student: " +
           "Roll Number: " + rollno +
           ", Name: " + name +
           ", Address: " + address +
           "";
    }
}