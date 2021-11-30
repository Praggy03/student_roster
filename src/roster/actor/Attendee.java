package src.roster.actor;

public class Attendee extends Student implements Cloneable {
    private int attendanceMinutes;

    public Attendee() { }

    public Attendee(String firstName, String lastName, String asuriteId, int attendanceMinutes) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.asuriteId = asuriteId;
        this.attendanceMinutes = attendanceMinutes;
    }

    public int getAttendanceMinutes() {
        return attendanceMinutes;
    }

    public void setAttendanceMinutes(int attendanceMinutes) {
        this.attendanceMinutes = attendanceMinutes;
    }

    @Override
    public Attendee clone() {
        try {
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return (Attendee) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
