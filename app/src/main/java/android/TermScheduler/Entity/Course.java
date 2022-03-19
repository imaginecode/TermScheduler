package android.TermScheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class Course {
    @PrimaryKey(autoGenerate = true)
    private int courseID;

    private String courseTitle;
    private String startDate;
    private String endDate;
    private String status;
    private String instructorName;
    private String instructorCell;
    private String instructorEmail;
    private String optionalNote;
    private String termID;

    public Course(int courseID, String courseTitle, String startDate, String endDate, String status, String instructorName, String instructorCell, String instructorEmail, String optionalNote, String termID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.instructorName = instructorName;
        this.instructorCell = instructorCell;
        this.instructorEmail = instructorEmail;
        this.optionalNote = optionalNote;
        this.termID = termID;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseTitle='" + courseTitle + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                ", instructorName='" + instructorName + '\'' +
                ", instructorCell='" + instructorCell + '\'' +
                ", instructorEmail='" + instructorEmail + '\'' +
                ", optionalNote='" + optionalNote + '\'' +
                ", termID='" + termID + '\'' +
                '}';
    }

    public String getTermID() {
        return termID;
    }

    public void setTermID(String termID) {
        this.termID = termID;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public String getInstructorCell() {
        return instructorCell;
    }

    public void setInstructorCell(String instructorCell) {
        this.instructorCell = instructorCell;
    }

    public String getInstructorEmail() {
        return instructorEmail;
    }

    public void setInstructorEmail(String instructorEmail) {
        this.instructorEmail = instructorEmail;
    }

    public String getOptionalNote() {
        return optionalNote;
    }

    public void setOptionalNote(String optionalNote) {
        this.optionalNote = optionalNote;
    }
}
