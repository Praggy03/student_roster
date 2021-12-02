package student_roster.actor;

public class Student {
    protected String firstName;
    protected String lastName;
    protected String asuriteId;
    protected String program;
    protected String level;
    protected String id;

    public Student(String id, String firstName, String lastName, String program, String level, String asuriteId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.program = program;
        this.level = level;
        this.asuriteId = asuriteId;
    }

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
