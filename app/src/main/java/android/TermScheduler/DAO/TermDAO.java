package android.TermScheduler.DAO;

import android.TermScheduler.Entity.Term;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TermDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTerm(Term term);

    @Update
    void updateTerm(Term term);

    @Delete
    void deleteTerm(Term term);

    @Query("SELECT * FROM terms ORDER BY termID ASC")
    List<Term> getAllTerms();

    @Query("DELETE FROM terms WHERE termID = :id")
    void deleteTermById(int id);

    @Query("SELECT * FROM terms WHERE termID = :id")
    Term getTermById(int id);

    @Query("DELETE FROM terms")
    void deleteAllTerms();


}
