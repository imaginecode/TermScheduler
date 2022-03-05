package android.TermScheduler.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "terms")
public class Terms {
    @PrimaryKey(autoGenerate = true)
    private int termID;

    private String termTitle;
    private Date termStart;
    private Date termEnd;






}
