package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.TermScheduler.Adapters.CourseAdapter;
import android.TermScheduler.Adapters.InstructorAdapter;
import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Course;
import android.TermScheduler.Entity.Instructor;
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

    EditText editNametxt;
    EditText editStartTxt;
    EditText editEndTxt;
    EditText editStatusTxt;
    EditText editNotesTxt;
    Calendar mCalendarStart = Calendar.getInstance();
    Calendar mCalendarEnd = Calendar.getInstance();
    SimpleDateFormat sdf;

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
        sdf = new SimpleDateFormat(myDateFormat, Locale.US);

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
        editNametxt = findViewById(R.id.editCourseName);
        editStartTxt = findViewById(R.id.editCourseStart);
        editEndTxt = findViewById(R.id.courseEndEdit);
        editStatusTxt = findViewById(R.id.statusEdit);
        editNotesTxt = findViewById(R.id.optionalNoteEdit);

        if(mCourseId != -1){
            editNametxt.setText(mSelectedCourse.getCourseTitle());
            editStartTxt.setText(mSelectedCourse.getStartDate());
            editEndTxt.setText(mSelectedCourse.getEndDate());
            editStatusTxt.setText(mSelectedCourse.getCourseStatus());
            editNotesTxt.setText(mSelectedCourse.getOptionalNote());
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

    private void updateDateStartText() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        editStartTxt.setText(sdf.format(mCalendarStart.getTime()));
    }

    private void updateDateEndText() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        editEndTxt.setText(sdf.format(mCalendarEnd.getTime()));
    }

    public void setDatePicker() {
        mStartDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                mCalendarStart.set(Calendar.YEAR, year);
                mCalendarStart.set(Calendar.MONTH, month);
                mCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                String myFormat = "MM/dd/yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                updateDateStartText();
            }
        };
        editStartTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                new DatePickerDialog(DetailedCourseActivity.this, mStartDatePicker, mCalendarStart.get(Calendar.YEAR),
                        mCalendarStart.get(Calendar.MONTH), mCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
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
                updateDateEndText();
            }
        };

        editEndTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DetailedCourseActivity.this, mEndDatePicker, mCalendarEnd.get(Calendar.YEAR), mCalendarEnd.get(Calendar.MONTH)
                        , mCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    //Inflating detailed course menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailed_course_menu, menu);
        return true;
    }

    //Set course Notifications
    public boolean onOptionsItemSelected (MenuItem item){

        try{switch (item.getItemId()){
            //This was causing a bug and I commented it out so it goes to application default
//            case android.R.id.home:
////                this.finish();
//                return true;
            case R.id.shareNote:
                Intent notesIntent = new Intent();
                notesIntent.setAction(Intent.ACTION_SEND);
                notesIntent.putExtra(Intent.EXTRA_TEXT, editNotesTxt.getText().toString());
                notesIntent.putExtra(Intent.EXTRA_TITLE, "Sharing Note");
                notesIntent.setType("text/plain");
                Intent noteIntentChooser = Intent.createChooser(notesIntent, null);
                startActivity(noteIntentChooser);
                return true;

            case R.id.courseStartAlert:
                String courseStartDate = editStartTxt.getText().toString();
                Date start = null;

                try{
                    start = sdf.parse(courseStartDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long startTrigger = start.getTime();
                Intent startIntent = new Intent(DetailedCourseActivity.this, MyReceiver.class);
                startIntent.putExtra("key", mSelectedCourse.getCourseTitle() + " course is beginning today");
                Toast.makeText(DetailedCourseActivity.this, "Start alert set", Toast.LENGTH_SHORT).show();
                PendingIntent firstSend = PendingIntent.getBroadcast(DetailedCourseActivity.this, MainActivity.alertNum++, startIntent, PendingIntent.FLAG_IMMUTABLE  );
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                startAlarmManager.set(AlarmManager.RTC_WAKEUP,startTrigger,firstSend);
                return true;

            case R.id.courseEndAlert:
                String courseEndDate = editEndTxt.getText().toString();
                Date end = null;

                try {
                    end = sdf.parse(courseEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long endTrigger = end.getTime();
                Intent lastIntent = new Intent(DetailedCourseActivity.this, MyReceiver.class);
                lastIntent.putExtra("key", mSelectedCourse.getCourseTitle() + " course ends today!");
                Toast.makeText(DetailedCourseActivity.this, "End alert set", Toast.LENGTH_SHORT);
                PendingIntent lastSend = PendingIntent.getBroadcast(DetailedCourseActivity.this, MainActivity.alertNum++, lastIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager)  getSystemService(Context.ALARM_SERVICE);
                endAlarmManager.set(AlarmManager.RTC_WAKEUP,endTrigger,lastSend);
                return true;
        }}

         catch(Exception e){
             AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Save Course before setting alert");
                alertDialog.setMessage("Course must be saved before setting alert!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Understood",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                alertDialog.show();
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

        String title = editNametxt.getText().toString();
        String start = editStartTxt.getText().toString();
        String end = editEndTxt.getText().toString();
        String status = editStatusTxt.getText().toString();
        String optionalNote = editNotesTxt.getText().toString();
        Course course;

        if(mCourseId == -1)
        {
            //checking for empty fields
            if (title.trim().isEmpty() || start.trim().isEmpty() || end.trim().isEmpty() || status.trim().isEmpty()) {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Empty Fields");
                alertDialog.setMessage("No fields can be left empty!");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Understood",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                alertDialog.show();

            }
            //Fields are all filled saving data
            else {
                if (optionalNote.trim().isEmpty()) {
                    optionalNote = " ";
                }

                List<Course> allCourses = repo.getAllCourses();
                int coursesSize = allCourses.size();
                int lastId = Integer.parseInt(String.valueOf(allCourses.get(coursesSize - 1).getCourseID()));
                course = new Course(lastId + 1, title, start, end,status, optionalNote, mTermId.toString());
                repo.insertCourse(course);
                // Navigating back to course list after saving entered data
                Intent intent = new Intent(this, CourseActivity.class);
                startActivity(intent);

            }
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