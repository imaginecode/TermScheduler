package android.TermScheduler.Database;

import android.TermScheduler.DAO.AssessmentDAO;
import android.TermScheduler.DAO.CourseDAO;
import android.TermScheduler.DAO.TermDAO;
import android.TermScheduler.Entity.Assessment;
import android.TermScheduler.Entity.Course;
import android.TermScheduler.Entity.Term;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Term.class, Course.class, Assessment.class}, version=1, exportSchema = false)
public abstract class TermSchedulerDataBaseBuilder extends RoomDatabase {
    public abstract TermDAO termDAO();
    public abstract CourseDAO courseDAO();
    public abstract AssessmentDAO assessmentDAO();

    public static volatile TermSchedulerDataBaseBuilder INSTANCE;

    static TermSchedulerDataBaseBuilder getDatabase(final Context context){
        if(INSTANCE == null) {
            synchronized (TermSchedulerDataBaseBuilder.class) {


                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TermSchedulerDataBaseBuilder.class, "termSchedulerDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();


                }
            }
        }
        return INSTANCE;
    }

}
