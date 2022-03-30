package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.TermScheduler.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CourseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
    }

    public void courseDetails(View view) {
        Intent intent = new Intent(CourseActivity.this, DetailedCourseActivity.class);
        startActivity(intent);
    }
}