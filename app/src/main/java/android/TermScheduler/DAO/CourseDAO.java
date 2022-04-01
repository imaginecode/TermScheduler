package android.TermScheduler.DAO;

import android.TermScheduler.Entity.Course;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CourseDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(Course course);

    @Update
    void update(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("DELETE FROM courses")
    void deleteAllCourses();

    @Query("SELECT * FROM courses ORDER BY courseID ASC")
    List<Course> getAllCourses();

    @Query ("SELECT * FROM courses WHERE termID= :termID ORDER BY courseTitle ASC")
    LiveData<List<Course>> getAllAssociatedCourses(int termID);
}




