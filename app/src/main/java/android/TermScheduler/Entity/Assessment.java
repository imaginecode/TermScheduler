package android.TermScheduler.Entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "assessments")
public class Assessment {
    @PrimaryKey(autoGenerate = true)
    private int assessmentID;
    private String assessmentTitle;
    private Date assessmentStart;
    private Date assessmentEnd;
    private String assessmentType;
//may need a foreign key for CourseID


    public Assessment(int assessmentID, String assessmentTitle, Date assessmentStart, Date assessmentEnd, String assessmentType) {
        this.assessmentID = assessmentID;
        this.assessmentTitle = assessmentTitle;
        this.assessmentStart = assessmentStart;
        this.assessmentEnd = assessmentEnd;
        this.assessmentType = assessmentType;
    }

    @Override
    public String toString() {
        return "Assessments{" +
                "assessmentID=" + assessmentID +
                ", assessmentTitle='" + assessmentTitle + '\'' +
                ", assessmentStart=" + assessmentStart +
                ", assessmentEnd=" + assessmentEnd +
                ", assessmentType='" + assessmentType + '\'' +
                '}';
    }

    public int getAssessmentID() {
        return assessmentID;
    }

    public void setAssessmentID(int assessmentID) {
        this.assessmentID = assessmentID;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public Date getAssessmentStart() {
        return assessmentStart;
    }

    public void setAssessmentStart(Date assessmentStart) {
        this.assessmentStart = assessmentStart;
    }

    public Date getAssessmentEnd() {
        return assessmentEnd;
    }

    public void setAssessmentEnd(Date assessmentEnd) {
        this.assessmentEnd = assessmentEnd;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }


}
