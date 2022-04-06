package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Course;
import android.TermScheduler.Entity.Instructor;
import android.TermScheduler.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class InstructorActivity extends AppCompatActivity {


    List<Instructor> mInstructorList;
    Repository repo;

    EditText editTextName;
    EditText editTextPhone;
    EditText editTextEmail;

    List<Course> mCourseList;
    Course mCourse;

    Instructor selectedInstructor;

    int mCourseID;
    int mInstructorID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repo = new Repository(getApplication());
        //For some reason I was having issues sending the intent put and get. I was always getting the default value
        mCourseID = DetailedCourseActivity.activeCourseID;
        mInstructorID = getIntent().getIntExtra("instructorID", -1);

        setContentView(R.layout.activity_instructor_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getInstructor();




    }

    public void getInstructor(){

        mInstructorList = repo.getAllInstructor();
        for (Instructor instructor: mInstructorList){
            if (instructor.getInstructorID() == mInstructorID){
                selectedInstructor = instructor;
            }
        }

        editTextName = findViewById(R.id.editInstructorName);
        editTextPhone = findViewById(R.id.editInstructorPhone);
        editTextEmail = findViewById(R.id.editInstructorEmail);

        if (mInstructorID != -1 ){
            editTextName.setText(selectedInstructor.getInstructorName());
            editTextPhone.setText(selectedInstructor.getInstructorPhone());
            editTextEmail.setText(selectedInstructor.getInstructorEmail());
        }



    }



    public void saveInstructor(View view) {

        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String email = editTextEmail.getText().toString();

        mInstructorList = repo.getAllInstructor();
        //instructorID is being set to one because in main activity I do an initial insert for instructor that creates the ID 1
        int instructorID = 1;
        for (Instructor instructor : mInstructorList ){
            if (instructor.getInstructorID() >= instructorID){
                instructorID = instructor.getInstructorID();
            }
        }


        if (mInstructorID == -1){
            Instructor insertInstructor = new Instructor(++instructorID, name, email, phone, mCourseID);
            repo.insertInstructor(insertInstructor);
        }else{

            Instructor newInstructor = new Instructor(mInstructorID, name, email, phone, mCourseID);
            repo.insertInstructor(newInstructor);
        }


        //Going back to Detailed Course activity

        //This needs to be changed to a different intent or variable to keep track of courseID
        Intent intent = new Intent(this, DetailedCourseActivity.class);

        intent.putExtra("courseId", DetailedCourseActivity.activeCourseID);

        startActivity(intent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}