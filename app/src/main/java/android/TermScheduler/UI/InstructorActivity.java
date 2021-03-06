package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Instructor;
import android.TermScheduler.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class InstructorActivity extends AppCompatActivity {
    List<Instructor> mInstructorList;
    Repository repo;

    EditText editNameTxt;
    EditText editPhoneTxt;
    EditText editEmailTxt;
    Instructor mSelectedInstructor;
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
        for (Instructor instructor: mInstructorList)
        {
            if (instructor.getInstructorID() == mInstructorID)
            {
                mSelectedInstructor = instructor;
            }
        }

        if(mInstructorID == -1)
        {
            findViewById(R.id.deleteInstructor).setVisibility(View.INVISIBLE);
        }

            editNameTxt = findViewById(R.id.editInstructorName);
            editPhoneTxt = findViewById(R.id.editInstructorPhone);
            editEmailTxt = findViewById(R.id.editInstructorEmail);

        if (mInstructorID != -1 )
        {
            editNameTxt.setText(mSelectedInstructor.getInstructorName());
            editPhoneTxt.setText(mSelectedInstructor.getInstructorPhone());
            editEmailTxt.setText(mSelectedInstructor.getInstructorEmail());
        }

    }


    public void saveInstructor(View view) {

        String name = editNameTxt.getText().toString();
        String phone = editPhoneTxt.getText().toString();
        String email = editEmailTxt.getText().toString();

        mInstructorList = repo.getAllInstructor();
        //instructorID is being set to one because in main activity I do an initial insert for instructor that creates the ID 1
        int instructorID = 1;
        for (Instructor instructor : mInstructorList ){
            if (instructor.getInstructorID() >= instructorID){
                instructorID = instructor.getInstructorID();
            }
        }

        // How to save for new instructor
        if (mInstructorID == -1){


            if (name.trim().isEmpty() || phone.trim().isEmpty() || email.trim().isEmpty()) {

                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Empty Text Fields");
                alertDialog.setMessage("No  text fields can be left empty!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "I Understand",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                alertDialog.show();

            }
        else{
            Instructor insertInstructor = new Instructor(++instructorID, name, email, phone, mCourseID);
            repo.insertInstructor(insertInstructor);

                Intent intent = new Intent(InstructorActivity.this, DetailedCourseActivity.class);
                intent.putExtra("courseID", DetailedCourseActivity.activeCourseID);
                startActivity(intent);
        }

        }
        // Saving edited instructor
        else{
            Instructor newInstructor = new Instructor(mInstructorID, name, email, phone, mCourseID);
            repo.insertInstructor(newInstructor);

            //Going back to Detailed Course activity
            Intent intent = new Intent(InstructorActivity.this, DetailedCourseActivity.class);
            intent.putExtra("courseID", DetailedCourseActivity.activeCourseID);
            startActivity(intent);
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Saving state with this.finish()
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteInstructor(View view) {
        repo.deleteInstructor(mSelectedInstructor);
        Toast.makeText(InstructorActivity.this, "Instructor Deleted", Toast.LENGTH_LONG).show();

        //Navigating back to Term list after clicking delete button
        Intent intent = new Intent(this, DetailedCourseActivity.class);
        intent.putExtra("courseID", DetailedCourseActivity.activeCourseID);
        startActivity(intent);
    }
}