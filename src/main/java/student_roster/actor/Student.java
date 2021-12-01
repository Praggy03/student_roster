package student_roster.actor;

public class Student {
    protected String firstName;
    protected String lastName;
    protected String asuriteId;

    public Student(String firstName, String lastName, String asuriteId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.asuriteId = asuriteId;
    }

    public Student() { }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    public String getAsuriteId() {
        return asuriteId;
    }

    public void setAsuriteId(String asuriteId) {
        this.asuriteId = asuriteId;
    }

    @Override
    public boolean equals(Object student) {
        if(student instanceof Student) return this.asuriteId.equals(((Student)student).asuriteId);
        return false;
    }
}
