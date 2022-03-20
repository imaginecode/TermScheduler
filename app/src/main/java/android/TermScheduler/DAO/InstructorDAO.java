package android.TermScheduler.DAO;

import android.TermScheduler.Entity.Assessment;
import android.TermScheduler.Entity.Instructor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface InstructorDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertInstructor(Instructor instructor);

    @Update
    void updateInstructor(Instructor instructor);

    @Delete
    void deleteInstructor(Instructor instructor);

    @Query("DELETE FROM instructor")
    void deleteAllInstructors();

    @Query("SELECT * FROM instructor WHERE instructorID = :id")
    Instructor getInstructorById(int id);

    @Query("SELECT * FROM instructor WHERE instructorID = :id")
    List<Instructor> getAllInstructorsByCourseId(int id);

    @Query("SELECT * FROM instructor ORDER BY instructorID ASC")
    List<Instructor> getAllInstructors();
}
