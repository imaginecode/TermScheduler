package android.TermScheduler.Database;

import android.TermScheduler.DAO.AssessmentDAO;
import android.TermScheduler.DAO.CourseDAO;
import android.TermScheduler.DAO.TermDAO;
import android.TermScheduler.Entity.Assessment;
import android.TermScheduler.Entity.Course;
import android.TermScheduler.Entity.Term;
import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public Repository(Application application){
        TermSchedulerDataBaseBuilder db=TermSchedulerDataBaseBuilder.getDatabase(application);
        mTermDAO=db.termDAO();
        mCourseDAO=db.courseDAO();
        mAssessmentDAO=db.assessmentDAO();

    }

    public void insert(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.insert(term);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Term>getAllTerms() {
        databaseExecutor.execute(() -> {
            mAllTerms = mTermDAO.getAllTerms();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllTerms;

    }

}
