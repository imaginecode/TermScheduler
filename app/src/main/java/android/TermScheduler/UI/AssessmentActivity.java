package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.TermScheduler.Adapters.AssessmentAdapter;
import android.TermScheduler.Adapters.CourseAdapter;
import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Assessment;
import android.TermScheduler.Entity.Course;
import android.content.Intent;
import android.os.Bundle;
import android.TermScheduler.R;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class AssessmentActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private AssessmentAdapter mAssessmentAdapter;
    Repository repo;

    List<Assessment> mAllAssessments;
    List<Assessment> associatedAssessmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        repo = new Repository(getApplication());
        getAllAssessments();
        setAssessmentRecyclerAndAdapter();

    }

    public void setAssessmentRecyclerAndAdapter() {
        mRecyclerView = findViewById(R.id.assessmentRecycler);
        mLayoutManager = new LinearLayoutManager(this);
        mAssessmentAdapter = new AssessmentAdapter(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAssessmentAdapter);
        mAssessmentAdapter.setAssessments(repo.getAllAssessments());
    }

    //filters out assessments by course id
    public void getAllAssessments() {
        mAllAssessments = repo.getAllAssessments();

        associatedAssessmentList = new ArrayList<>();
        for (Assessment assessment : mAllAssessments) {
            if (assessment.getCourseID() == (DetailedCourseActivity.activeCourseID)) {

                associatedAssessmentList.add(assessment);
            }
        }
    }



    public void addAssessment(View view) {
        Intent intent = new Intent(AssessmentActivity.this, DetailedAssessmentActivity.class);
        startActivity(intent);
    }
}