package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.TermScheduler.R;
import android.view.View;

public class DetailedCourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course_view);
    }

    public void instructorAdd(View view) {
        Intent intent = new Intent(DetailedCourseActivity.this, InstructorActivity.class);
        startActivity(intent);
    }

    public void associatedAssessments(View view) {
        Intent intent = new Intent(DetailedCourseActivity.this, AssessmentActivity.class);
        startActivity(intent);
    }

    public void deleteCourse(View view) {
    }

    public void saveCourse(View view) {
    }
}