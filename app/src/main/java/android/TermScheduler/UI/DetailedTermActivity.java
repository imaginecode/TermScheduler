package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.TermScheduler.R;
import android.view.View;

public class DetailedTermActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term_view);
    }

    public void saveTerm(View view) {
    }


    public void deleteCourse(View view) {
    }

    public void associatedCourses(View view) {
        Intent intent = new Intent(DetailedTermActivity.this, CourseActivity.class);
        startActivity(intent);
    }
}