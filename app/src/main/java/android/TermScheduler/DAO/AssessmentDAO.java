package android.TermScheduler.DAO;

import android.TermScheduler.Entity.Assessment;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AssessmentDAO {

//adding addition functions to DOA for further extension possibility

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(Assessment assessment);

    @Update
    void update(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("DELETE FROM assessments")
    void deleteAllAssessments();

    @Query("SELECT * FROM assessments WHERE assessmentID = :id")
    Assessment getAssessmentById(int id);

    @Query("SELECT * FROM assessments WHERE courseID = :id")
    List<Assessment> getAllAssessmentsByCourseId(int id);

    @Query("SELECT * FROM assessments ORDER BY assessmentID ASC")
    List<Assessment> getAllAssessments();
}
