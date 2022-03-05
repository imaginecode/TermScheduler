package android.TermScheduler.Entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "courses")
public class Courses {
    @PrimaryKey(autoGenerate = true)
    private int courseID;

    private String courseTitle;
    private Date startDate;
    private Date endDate;
    private String status;
}
