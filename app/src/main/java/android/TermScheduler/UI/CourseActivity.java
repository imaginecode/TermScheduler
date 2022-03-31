package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.TermScheduler.Adapters.CourseAdapter;
import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Course;
import android.content.Intent;
import android.os.Bundle;
import android.TermScheduler.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CourseActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private CourseAdapter mCourseAdapter;
    Repository repo;
    List<Course> mAllCourses;
    List<Course> associatedCourseList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        repo = new Repository(getApplication());
        setCourseRecyclerAndAdapter();
        getAllCourses();

        mCourseAdapter.setCourses(associatedCourseList);
    }


    public void setCourseRecyclerAndAdapter() {
        mRecyclerView = findViewById(R.id.courseListRecycler);
        mLayoutManager = new LinearLayoutManager(this);
        mCourseAdapter = new CourseAdapter(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mCourseAdapter);


    }

    public void getAllCourses() {
        mAllCourses = repo.getAllCourses();
        associatedCourseList = new ArrayList<>();
        for (Course course : mAllCourses) {
            if (course.getTermID() == DetailedTermActivity.getActiveTermID.toString()) {
                associatedCourseList.add(course);
            }
        }
    }

    public void courseDetails(View view) {
        Intent intent = new Intent(CourseActivity.this, DetailedCourseActivity.class);
        startActivity(intent);
    }
}