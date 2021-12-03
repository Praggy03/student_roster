package student_roster.actor;

public class Attendee extends Student implements Cloneable {
    private int attendanceMinutes;

    public Attendee() {
        super();
    }

    public Attendee(String asuriteId, int attendanceMinutes) {
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
            return (Attendee) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return  "Attendee: " + this.getAsuriteId() + ", minutes: " + this.getAttendanceMinutes();
    }
}
