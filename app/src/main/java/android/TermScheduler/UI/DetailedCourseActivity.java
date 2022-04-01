package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.TermScheduler.Adapters.CourseAdapter;
import android.TermScheduler.Adapters.InstructorAdapter;
import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Course;
import android.TermScheduler.Entity.Instructor;
import android.TermScheduler.Entity.Term;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.TermScheduler.R;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DetailedCourseActivity extends AppCompatActivity {

    EditText mNameText;
    EditText mStartDate;
    EditText mEndDate;
    EditText mStatus;
    EditText mNotes;
    Calendar mCalendarStart = Calendar.getInstance();
    Calendar mCalendarEnd = Calendar.getInstance();
    SimpleDateFormat dateFormatter;

    DatePickerDialog.OnDateSetListener mStartDatePicker;
    DatePickerDialog.OnDateSetListener mEndDatePicker;



    Repository repo;


    List<Course> mCoursesList;
    Course mSelectedCourse;

    List<Instructor> listOfInstructors;

    private RecyclerView mRecyclerViewInstructor;
    private RecyclerView.LayoutManager mLayoutManager;
    private CourseAdapter mCourseAdapter;
    private InstructorAdapter mInstructorAdapter;

    Integer mTermId;
    int mCourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_course_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String myDateFormat = "MM/dd/yyyy";
        dateFormatter = new SimpleDateFormat(myDateFormat, Locale.US);

        mTermId = getIntent().getIntExtra("selectedTermId", -1);
        mCourseId = getIntent().getIntExtra("courseId", -1);

        repo = new Repository(getApplication());

        getAndSetViewsById();

        getAllCourses();


        getInstructorsInCourse();

        setRecyclerViews();


        setDatePicker();
    }

    public void instructorAdd(View view) {
        Intent intent = new Intent(DetailedCourseActivity.this, InstructorActivity.class);
        intent.putExtra("courseId", mCourseId);
        startActivity(intent);
    }

    public void getInstructorsInCourse() {
        listOfInstructors = new ArrayList<>();
        List<Instructor> list = repo.getAllInstructor();
        for (Instructor instructor : list){
            if (instructor.getCourseID() == mCourseId){
                listOfInstructors.add(instructor);
            }
        }
//       Try getting all instructors first before narrowing it down to just those associated
    }



    public void setRecyclerViews() {
        if (mCourseId != -1) {
            getSelectedCourse();


            mRecyclerViewInstructor = findViewById(R.id.coursesRecycler);

            mLayoutManager = new LinearLayoutManager(this);


            mInstructorAdapter = new InstructorAdapter(this);


            mRecyclerViewInstructor.setLayoutManager(mLayoutManager);


            mRecyclerViewInstructor.setAdapter(mInstructorAdapter);


            mInstructorAdapter.setInstructor(listOfInstructors);
        }
    }

    //Sets the text for editing course information
    public void getAndSetViewsById() {
        mNameText = findViewById(R.id.editCourseName);
        mStartDate = findViewById(R.id.editCourseStart);
        mEndDate = findViewById(R.id.courseEndEdit);
        mStatus = findViewById(R.id.statusEdit);
        mNotes = findViewById(R.id.optionalNoteEdit);
    }

    //3 methods  below for dating picking and formatting

    private void updateLabelStart() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        mStartDate.setText(sdf.format(mCalendarStart.getTime()));
    }

    private void updateLabelEnd() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        mEndDate.setText(sdf.format(mCalendarEnd.getTime()));
    }

    public void setDatePicker() {
        mStartDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarStart.set(Calendar.YEAR, year);
                mCalendarStart.set(Calendar.MONTH, month);
                mCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                updateLabelStart();
            }
        };
        mStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DetailedCourseActivity.this, mStartDatePicker, mCalendarStart.get(Calendar.YEAR), mCalendarStart.get(Calendar.MONTH)
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
                updateLabelEnd();
            }
        };

        mEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DetailedCourseActivity.this, mEndDatePicker, mCalendarEnd.get(Calendar.YEAR), mCalendarEnd.get(Calendar.MONTH)
                        , mCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



    public void getAllCourses() {
        mCoursesList = repo.getAllCourses();
    }
    public void getSelectedCourse() {

        for (Course course : mCoursesList) {
            if (mCourseId == course.getCourseID()) {
                mSelectedCourse = course;
            }
        }

        mNameText.setText(mSelectedCourse.getCourseTitle());
        mStartDate.setText(mSelectedCourse.getStartDate());
        mEndDate.setText(mSelectedCourse.getEndDate());
        mStatus.setText(mSelectedCourse.getCourseStatus());
        mNotes.setText(mSelectedCourse.getOptionalNote());
        mTermId = Integer.valueOf(mSelectedCourse.getTermID());
    }





    public void deleteCourse(View view) {
    }

    public void saveCourse(View view) {

        String title = mNameText.getText().toString();
        String start = mStartDate.getText().toString();
        String end = mEndDate.getText().toString();
        String status = mStatus.getText().toString();
        String optionalNote = mNotes.getText().toString();
        Course course;

        if(mCourseId == -1)
        {
//            if (title.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty() || status.trim().isEmpty()) {
//
//
//                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//                alertDialog.setTitle("Empty Fields");
//                alertDialog.setMessage("No fields can be left empty!");
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Understood",
//                        new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                dialogInterface.dismiss();
//                            }
//                        });
//                alertDialog.show();
//
//            }
//            else {
//
//            }

            if (optionalNote.trim().isEmpty()) {
                optionalNote = " ";
            }
            List<Course> allCourses = repo.getAllCourses();
            int coursesSize = allCourses.size();
            int lastId = Integer.parseInt(String.valueOf(allCourses.get(coursesSize - 1).getCourseID()));
            course = new Course(lastId + 1, title, start, end,status, optionalNote, mTermId.toString());
            repo.insertCourse(course);

            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);
        }
        else{


            // Prevents optional note from throwing a null pointer exception when not filled, put an empty string in
            if (optionalNote.trim().isEmpty()) {
                optionalNote = " ";
            }

            course = new Course(1, title, start, end,status, optionalNote, mTermId.toString());
            repo.insertCourse(course);

            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);

        }
    }
}