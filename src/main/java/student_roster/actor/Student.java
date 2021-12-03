package student_roster.actor;

public class Student {
    protected String firstName;
    protected String lastName;
    protected String asuriteId;
    protected String program;
    protected String level;
    protected String id;
    protected int position;

    public Student(String id, String firstName, String lastName, String program, String level, String asuriteId, int position) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.program = program;
        this.level = level;
        this.asuriteId = asuriteId;
        this.position = position;
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

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object student) {
        if(student instanceof Student) return this.asuriteId.equals(((Student)student).asuriteId);
        return false;
    }
}
