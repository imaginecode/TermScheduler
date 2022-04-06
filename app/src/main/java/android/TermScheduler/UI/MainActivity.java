package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Assessment;
import android.TermScheduler.Entity.Course;
import android.TermScheduler.Entity.Instructor;
import android.TermScheduler.Entity.Term;
import android.TermScheduler.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static int alertNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Repository repo = new Repository(getApplication());
        repo.getAllTerms();
        Term term = new Term(1,"Term 1", "3/18/2022", "5/30/2022");
        repo.insertTerm(term);
        Course course = new Course(1,"Test", "3/18/2022", "5/30/2022", "In Progress", "This is a optional note", "1" );
        repo.insertCourse(course);

        Instructor instructor = new Instructor(1,"bob","bob@10","580235789",1);
        repo.insertInstructor(instructor);

        Assessment assessment = new Assessment(1,"c196","5/18/2022","5/20/2022","Objective Assessment",1);
        repo.insertAssessment(assessment);
    }

    public void EnterHere(View view) {



        Intent intent = new Intent(MainActivity.this, TermActivity.class);
        startActivity(intent);
    }
}