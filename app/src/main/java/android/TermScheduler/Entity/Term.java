package android.TermScheduler.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "terms")
public class Term {
    @PrimaryKey(autoGenerate = true)
    private int termID;

    private String termTitle;
    private Date termStart;
    private Date termEnd;


    public Term(int termID, String termTitle, Date termStart, Date termEnd) {
        this.termID = termID;
        this.termTitle = termTitle;
        this.termStart = termStart;
        this.termEnd = termEnd;
    }

    public int getTermID() {
        return termID;
    }

    public void setTermID(int termID) {
        this.termID = termID;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public Date getTermStart() {
        return termStart;
    }

    public void setTermStart(Date termStart) {
        this.termStart = termStart;
    }

    public Date getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(Date termEnd) {
        this.termEnd = termEnd;
    }


    @Override
    public String toString() {
        return "Terms{" +
                "termID=" + termID +
                ", termTitle='" + termTitle + '\'' +
                ", termStart=" + termStart +
                ", termEnd=" + termEnd +
                '}';
    }
}
