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
import android.TermScheduler.Utilities.MyReceiver;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.TermScheduler.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedCourseActivity extends AppCompatActivity {

    public static Integer activeCourseID;

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

    List<Instructor> mAssociatedInstructorList;
    List<Instructor> mInstructorList;

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

        repo = new Repository(getApplication());
        mTermId = DetailedTermActivity.getActiveTermID;

        //gets and sets course information depending on if this is a new course being created or if its being edited
        getCourse();





        //Setting up Instructor Recycler
        getAllInstructors();
        setInstructorRecyclerView();

        setDatePicker();
    }

    public void getCourse() {
        mCourseId = getIntent().getIntExtra("courseID", -1);
        //all courses
        mCoursesList = repo.getAllCourses();

        //getting selected course
        for (Course course : mCoursesList) {
            if (mCourseId == course.getCourseID()) {
                mSelectedCourse = course;
                activeCourseID = mSelectedCourse.getCourseID();
            }
        }

                if(mCourseId == -1) {
            findViewById(R.id.goToAssociatedAssessments).setVisibility(View.INVISIBLE);
            findViewById(R.id.addInstructor).setVisibility(View.INVISIBLE);
            findViewById(R.id.deleteCourse).setVisibility(View.INVISIBLE);


        }
        mNameText = findViewById(R.id.editCourseName);
        mStartDate = findViewById(R.id.editCourseStart);
        mEndDate = findViewById(R.id.courseEndEdit);
        mStatus = findViewById(R.id.statusEdit);
        mNotes = findViewById(R.id.optionalNoteEdit);



        if(mCourseId != -1){
            mNameText.setText(mSelectedCourse.getCourseTitle());
            mStartDate.setText(mSelectedCourse.getStartDate());
            mEndDate.setText(mSelectedCourse.getEndDate());
            mStatus.setText(mSelectedCourse.getCourseStatus());
            mNotes.setText(mSelectedCourse.getOptionalNote());
            mTermId = Integer.valueOf(mSelectedCourse.getTermID());
        }
    }

    //Methods dealing with instructors

    public void instructorAdd(View view) {
        Intent intent = new Intent(DetailedCourseActivity.this, InstructorActivity.class);
        intent.putExtra("courseId", mCourseId);
        startActivity(intent);
    }

    public void getAllInstructors() {
        mInstructorList = repo.getAllInstructor();
        mAssociatedInstructorList = new ArrayList<>();
        for (Instructor instructor : mInstructorList) {
            if (Integer.valueOf(instructor.getCourseID()).equals(DetailedCourseActivity.activeCourseID)) {

                mAssociatedInstructorList.add(instructor);
            }
        }
    }

    


    public void setInstructorRecyclerView() {
        if (mCourseId != -1) {
//            getSelectedCourse();
            mRecyclerViewInstructor = findViewById(R.id.coursesRecycler);
            mLayoutManager = new LinearLayoutManager(this);
            mInstructorAdapter = new InstructorAdapter(this);
            mRecyclerViewInstructor.setLayoutManager(mLayoutManager);
            mRecyclerViewInstructor.setAdapter(mInstructorAdapter);
            mInstructorAdapter.setInstructor(mAssociatedInstructorList);
        }
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





    //Set course Notifications
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailed_course_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected (MenuItem item){

        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.shareNote:
                Intent notesIntent = new Intent();
                notesIntent.setAction(Intent.ACTION_SEND);
                notesIntent.putExtra(Intent.EXTRA_TEXT, mNotes.getText().toString());
                notesIntent.putExtra(Intent.EXTRA_TITLE, "Sharing Note");
                notesIntent.setType("text/plain");
                Intent noteIntentChooser = Intent.createChooser(notesIntent, null);
                startActivity(noteIntentChooser);
                return true;

            case R.id.courseStartAlert:
                String courseStartDate = mStartDate.getText().toString();
                Date start = null;

                try{
                    start = dateFormatter.parse(courseStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long startTrigger = start.getTime();
                Intent startIntent = new Intent(DetailedCourseActivity.this, MyReceiver.class);
                startIntent.putExtra("key", mSelectedCourse.getCourseTitle() + " course is beginning today");
                Toast.makeText(DetailedCourseActivity.this, "Start notification set", Toast.LENGTH_SHORT).show();
                PendingIntent startSend = PendingIntent.getBroadcast(DetailedCourseActivity.this, MainActivity.alertNum++, startIntent, 0  );
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP,startTrigger,startSend);
                return true;

            case R.id.courseEndAlert:
                String courseEndDate = mEndDate.getText().toString();
                Date end = null;

                try {
                    end = dateFormatter.parse(courseEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long endTrigger = end.getTime();
                Intent endIntent = new Intent(DetailedCourseActivity.this, MyReceiver.class);
                endIntent.putExtra("key", mSelectedCourse.getCourseTitle() + " course ends today!");
                Toast.makeText(DetailedCourseActivity.this, "End notification set", Toast.LENGTH_SHORT);
                PendingIntent endSend = PendingIntent.getBroadcast(DetailedCourseActivity.this, MainActivity.alertNum++, endIntent, 0);
                AlarmManager endAlarmManager = (AlarmManager)  getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP,endTrigger,endSend);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }




    //Deleting Course, No need to check for associated assessments as that is not specified in the requirements
    public void deleteCourse(View view) {
        repo.deleteCourse(mSelectedCourse);
        Toast.makeText(DetailedCourseActivity.this, "Course Deleted", Toast.LENGTH_LONG).show();

        //Navigating back to Term list after clicking delete button
        Intent intent = new Intent(this, CourseActivity.class);
        startActivity(intent);
    }

    //Saves course and also checks for empty fields
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


            // Prevents optional note from throwing a null pointer exception when not filled, puts an empty string in
            if (optionalNote.trim().isEmpty()) {
                optionalNote = " ";
            }

            course = new Course(mSelectedCourse.getCourseID(), title, start, end,status, optionalNote, mTermId.toString());
            repo.insertCourse(course);

            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);

        }
    }

    public void associatedAssessments(View view) {
        Intent intent = new Intent(DetailedCourseActivity.this, AssessmentActivity.class);
        startActivity(intent);
    }
}