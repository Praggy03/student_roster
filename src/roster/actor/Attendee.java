package src.roster.actor;

public class Attendee extends Student {
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
}
