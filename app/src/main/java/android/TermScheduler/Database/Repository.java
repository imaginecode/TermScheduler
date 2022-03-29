package android.TermScheduler.Database;

import android.TermScheduler.DAO.AssessmentDAO;
import android.TermScheduler.DAO.CourseDAO;
import android.TermScheduler.DAO.InstructorDAO;
import android.TermScheduler.DAO.TermDAO;
import android.TermScheduler.Entity.Assessment;
import android.TermScheduler.Entity.Course;
import android.TermScheduler.Entity.Instructor;
import android.TermScheduler.Entity.Term;
import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private TermDAO mTermDAO;
    private CourseDAO mCourseDAO;
    private AssessmentDAO mAssessmentDAO;
    private InstructorDAO mInstructorDAO;



    private List<Term> mAllTerms;
    private List<Course> mAllCourses;
    private List<Assessment> mAllAssessments;
    private List<Instructor> mAllInstructors;

//    private int termID;
//    private int courseID;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor= Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public Repository(Application application){
        TermSchedulerDataBaseBuilder db=TermSchedulerDataBaseBuilder.getDatabase(application);
        mTermDAO=db.termDAO();
        mCourseDAO=db.courseDAO();
        mAssessmentDAO=db.assessmentDAO();
        mInstructorDAO=db.instructorDAO();

        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }

    }



    /** DOA functions for Term*/

    public void insertTerm(Term term){
        databaseExecutor.execute(()->{
            mTermDAO.insertTerm(term);
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

//    public Term getTermByID(int id){
//        return databaseExecutor.execute(() -> mTermDAO.getTermById(id));
//
//    }


    public Term getTermByID(int id) {
           return mTermDAO.getTermById(id);

    }




    public void deleteTerm(Term term) {
        databaseExecutor.execute(() -> {
            mTermDAO.deleteTerm(term);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllTerms() {
        databaseExecutor.execute(()->{
            mTermDAO.deleteAllTerms();
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /** DOA functions for Courses*/

    public void insertCourse(Course course){
        databaseExecutor.execute(()->{
            mCourseDAO.insertCourse(course);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Course>getAllCourses() {
        databaseExecutor.execute(() -> {
            mAllCourses = mCourseDAO.getAllCourses();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllCourses;

    }

//    public Term getTermByID(int id){
//        return databaseExecutor.execute(() -> mTermDAO.getTermById(id));
//
//    }


    public void deleteCourse(Course course) {
        databaseExecutor.execute(() -> {
            mCourseDAO.deleteCourse(course);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllCourses() {
        databaseExecutor.execute(()->{
            mCourseDAO.deleteAllCourses();
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /** DOA functions for Assessments*/

    public void insertAssessment(Assessment assessment){
        databaseExecutor.execute(()->{
            mAssessmentDAO.insertAssessment(assessment);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Assessment>getAllAssessments() {
        databaseExecutor.execute(() -> {
            mAllAssessments = mAssessmentDAO.getAllAssessments();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllAssessments;

    }

//    public Term getTermByID(int id){
//        return databaseExecutor.execute(() -> mTermDAO.getTermById(id));
//
//    }


    public void deleteAssessment(Assessment assessment) {
        databaseExecutor.execute(() -> {
            mAssessmentDAO.deleteAssessment(assessment);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllAssessments() {
        databaseExecutor.execute(()->{
            mAssessmentDAO.deleteAllAssessments();
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /** DOA functions for Instructors*/

    public void insertInstructor(Instructor instructor){
        databaseExecutor.execute(()->{
            mInstructorDAO.insertInstructor(instructor);
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public List<Instructor>getAllInstructor() {
        databaseExecutor.execute(() -> {
            mAllInstructors = mInstructorDAO.getAllInstructors();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllInstructors;

    }

//    public Term getTermByID(int id){
//        return databaseExecutor.execute(() -> mTermDAO.getTermById(id));
//
//    }


    public void deleteInstructor(Instructor instructor) {
        databaseExecutor.execute(() -> {
            mInstructorDAO.deleteInstructor(instructor);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllInstructors() {
        databaseExecutor.execute(()->{
            mInstructorDAO.deleteAllInstructors();
        });
        try{
            Thread.sleep(1000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }



}
