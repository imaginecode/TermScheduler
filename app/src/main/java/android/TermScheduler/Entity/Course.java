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
    private String courseStatus;
    private String optionalNote;
    private String termID;


    public Course(int courseID, String courseTitle, String startDate, String endDate, String courseStatus, String optionalNote, String termID) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.courseStatus = courseStatus;
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
                ", courseStatus='" + courseStatus + '\'' +
                ", optionalNote='" + optionalNote + '\'' +
                ", termID='" + termID + '\'' +
                '}';
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
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


    public String getOptionalNote() {
        return optionalNote;
    }

    public void setOptionalNote(String optionalNote) {
        this.optionalNote = optionalNote;
    }
}
