package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.TermScheduler.Adapters.TermAdapter;
import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Course;
import android.TermScheduler.Entity.Term;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.TermScheduler.R;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;



public class DetailedTermActivity extends AppCompatActivity {

    List<Term> termList;
    List<Course> associatedCourseList;
    Repository repo;

    TermAdapter mTermAdapter;

    EditText termName;
    EditText startDate;
    EditText endDate;

    Calendar mCalendarStart = Calendar.getInstance();
    Calendar mCalendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener mStartDatePicker;
    DatePickerDialog.OnDateSetListener mEndDatePicker;

    //Variables if this is an Edit instead of a create
    int mTermId;
    //tracks the term so that correct courses and assessments are loaded
    public static Integer getActiveTermID; // this should be redundant with put and get from the adapters.
    String mName;
    String mStartD;
    String mEndD;
    Term mSelectedTerm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_term_view);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        termName = findViewById(R.id.editTermTitle);
        startDate = findViewById(R.id.editStartDateTerm);
        endDate = findViewById(R.id.editEndDateTerm);
        repo = new Repository(getApplication());

        //Getting data for edit instead of create
        getTerm();
        getAndSetViewsById();
        setDatePicker();

    }

    public void getTerm() {
        mTermId = getIntent().getIntExtra("termId", -1);
        for (Term term : repo.getAllTerms()) {
            if (term.getTermID() == mTermId) {
                mSelectedTerm = term;
                getActiveTermID = term.getTermID();
            }
        }
        if (mSelectedTerm != null) {
            mName = mSelectedTerm.getTermTitle();
            mStartD = mSelectedTerm.getTermStart();
            mEndD = mSelectedTerm.getTermEnd();
        }
        termName.setText(mName);
        startDate.setText(mStartD);
        endDate.setText(mEndD);
    }

    //Sets date picker format and sets on click listeners
    public void setDatePicker() {
        mStartDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarStart.set(Calendar.YEAR, year);
                mCalendarStart.set(Calendar.MONTH, month);
                mCalendarStart.set(Calendar.DAY_OF_MONTH, day);

                updateDateStartTxt();
            }
        };
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DetailedTermActivity.this, mStartDatePicker, mCalendarStart.get(Calendar.YEAR), mCalendarStart.get(Calendar.MONTH)
                        , mCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        mEndDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarEnd.set(Calendar.YEAR, year);
                mCalendarEnd.set(Calendar.MONTH, month);
                mCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                updateDateEndTxt();
            }
        };
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DetailedTermActivity.this, mEndDatePicker, mCalendarEnd.get(Calendar.YEAR), mCalendarEnd.get(Calendar.MONTH)
                        , mCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void saveTerm(View view) {
        String title = termName.getText().toString();
        String start = startDate.getText().toString();
        String end = endDate.getText().toString();
        Term term;

// If the term is a new term check to make sure all fields are populated if not give an error box
        if(mTermId == -1)
        {
            if (title.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty()) {

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
            else {
                List<Term> allTerms = repo.getAllTerms();
                int termsSize = allTerms.size();
                if (!allTerms.isEmpty()) {
                    int lastId = allTerms.get(termsSize - 1).getTermID();

                    term = new Term(lastId + 1, title, start, end);

                }
                else { term = new Term(1, title, start, end); }

                repo.insertTerm(term);
                //Navigating back to Term list after clicking save button
                Intent intent = new Intent(this, TermActivity.class);
                startActivity(intent);
            }
        }
        else{

            Term updatedTerm = new Term(mTermId, title, start, end);
            repo.insertTerm(updatedTerm);

            Intent intent = new Intent(this, TermActivity.class);
            startActivity(intent);

        }


    }


        private void updateDateStartTxt() {
            String format = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            startDate.setText(sdf.format(mCalendarStart.getTime()));
        }

        private void updateDateEndTxt() {
            String format = "MM/dd/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
            endDate.setText(sdf.format(mCalendarEnd.getTime()));
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

    public void getAndSetViewsById() {
        if(mTermId == -1) {
            findViewById(R.id.deleteTerm).setVisibility(View.INVISIBLE);
            findViewById(R.id.viewAssociatedCourses).setVisibility(View.INVISIBLE);
        }
        termName = findViewById(R.id.editTermTitle);
        startDate = findViewById(R.id.editStartDateTerm);
        endDate = findViewById(R.id.editEndDateTerm);
    }

// Checks to see if there are any courses associated so that the delete function can be performed
    private boolean coursesAssociated(){
        associatedCourseList = repo.getAllCourses();
        for (Course course: associatedCourseList) {
            if(course.getTermID().toString().equals(getActiveTermID.toString()))
                return true;
        }
        return false;
    }

//Deletes term after checking for associated courses
    public void deleteTerm(View view) {

        if(coursesAssociated()){
            Toast.makeText(DetailedTermActivity.this, "Can't delete Courses Associated", Toast.LENGTH_LONG).show();
        }
        else{
            repo.deleteTerm(mSelectedTerm);
            Toast.makeText(DetailedTermActivity.this, "Term Deleted", Toast.LENGTH_LONG).show();

            //Navigating back to Term list after clicking delete button
            Intent intent = new Intent(this, TermActivity.class);
            startActivity(intent);
        }


    }

    public void associatedCourses(View view) {
        Intent intent = new Intent(DetailedTermActivity.this, CourseActivity.class);
        startActivity(intent);
    }
}