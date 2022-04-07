package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Assessment;
import android.TermScheduler.Entity.Course;
import android.TermScheduler.R;
import android.TermScheduler.Utilities.MyReceiver;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailedAssessmentActivity extends AppCompatActivity {

    EditText editTextTitle;
    EditText editTextType;
    EditText editTextStart;
    EditText editTextEnd;

    Repository repo;
    List<Assessment> mAssessmentList;
    List<Course> mCourseList;
    Course mCourse;

    Assessment mSelectedAssessment;

    Calendar mCalendarStart = Calendar.getInstance();
    Calendar mCalendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener mStartDatePicker;
    DatePickerDialog.OnDateSetListener mEndDatePicker;

    SimpleDateFormat dateFormatter;

    int mCourseID;
    int mAssessmentID;
//    int mAssessCourseID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repo = new Repository(getApplication());

        String myDateFormat = "MM/dd/yyyy";
        dateFormatter = new SimpleDateFormat(myDateFormat, Locale.US);

        mCourseID = DetailedCourseActivity.activeCourseID;
        mAssessmentID = getIntent().getIntExtra("assessmentID", -1);
//        mAssessCourseId = getIntent().getIntExtra("assessCourseId", -1);

        mAssessmentList = repo.getAllAssessments();
        for (Assessment assessment : mAssessmentList) {
            if (assessment.getAssessmentID() == mAssessmentID) {
                mSelectedAssessment = assessment;
            }
        }

        editTextTitle = findViewById(R.id.editAssessmentTitle);
        editTextStart = findViewById(R.id.editStartDateAssessment);
        editTextEnd = findViewById(R.id.editEndDateAssessment);
        editTextType = findViewById(R.id.AssessmentType);

        if (mAssessmentID != -1) {
            editTextTitle.setText(mSelectedAssessment.getAssessmentTitle());
            editTextStart.setText(mSelectedAssessment.getAssessmentStart());
            editTextEnd.setText(mSelectedAssessment.getAssessmentEnd());
            editTextType.setText(mSelectedAssessment.getAssessmentType());
        }

        setDates();


    }

    //Setting date pickers for start and end date fields

    public void setDates(){
        mStartDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarStart.set(Calendar.YEAR, year);
                mCalendarStart.set(Calendar.MONTH, month);
                mCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                UpdateLabelStart();
            }
        };
        mEndDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarEnd.set(Calendar.YEAR, year);
                mCalendarEnd.set(Calendar.MONTH, month);
                mCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
                UpdateLabelEnd();
            }
        };

        editTextStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DetailedAssessmentActivity.this, mStartDatePicker, mCalendarStart.get(Calendar.YEAR)
                        , mCalendarStart.get(Calendar.MONTH), mCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editTextEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DetailedAssessmentActivity.this, mEndDatePicker, mCalendarEnd.get(Calendar.YEAR),
                        mCalendarEnd.get(Calendar.MONTH), mCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

//Setting edit start and end fields
    public void UpdateLabelStart() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        editTextStart.setText(sdf.format(mCalendarStart.getTime()));
    }

    public void UpdateLabelEnd(){
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.US);

        editTextEnd.setText(sdf.format(mCalendarEnd.getTime()));
    }

//inflating menu for selecting assessment notifications

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.assessment_menu, menu);
        return true;
    }


    // Notifications for assessments while app is closed or open
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.assessmentStartNotification:
                String editTextDate = editTextStart.getText().toString();
                Date startDate = null;

                try {
                    startDate = dateFormatter.parse(editTextDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long timeTrigger = startDate.getTime();
                Intent intent = new Intent(DetailedAssessmentActivity.this, MyReceiver.class);
                intent.putExtra("key", mSelectedAssessment.getAssessmentTitle() + " assessment starts today!");
                Toast.makeText(DetailedAssessmentActivity.this, "Notifications On ", Toast.LENGTH_SHORT).show();

                PendingIntent send = PendingIntent.getBroadcast(DetailedAssessmentActivity.this, MainActivity.alertNum++,intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,timeTrigger, send);
                return true;

            case R.id.assessmentEndNotification:
                String editTextEndDate = editTextEnd.getText().toString();
                Date endDate = null;

                try {
                    endDate = dateFormatter.parse(editTextEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long endTrigger = endDate.getTime();
                Intent endIntent = new Intent(DetailedAssessmentActivity.this, MyReceiver.class);
                endIntent.putExtra("key", mSelectedAssessment.getAssessmentTitle() + " assessment ends today!");
                Toast.makeText(DetailedAssessmentActivity.this, "Assessment end notification set", Toast.LENGTH_SHORT).show();

                PendingIntent sendEnd = PendingIntent.getBroadcast(DetailedAssessmentActivity.this, MainActivity.alertNum++, endIntent, 0);
                AlarmManager endAlarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManger.set(AlarmManager.RTC_WAKEUP,endTrigger,sendEnd);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


//save assessment
    public void saveAssessment(View view) {

        String title, startDate, endDate,type;

        title = editTextTitle.getText().toString();
        startDate = editTextStart.getText().toString();
        endDate = editTextEnd.getText().toString();
        type = editTextType.getText().toString();

        mAssessmentList = repo.getAllAssessments();
        int assessmentID = 1;
        for (Assessment assessment : mAssessmentList) {
            if (assessment.getAssessmentID() >= assessmentID) {
                assessmentID = assessment.getAssessmentID();
            }
        }

        if (mAssessmentID != -1) {
            Assessment updatedAssessment = new Assessment(mAssessmentID, title, startDate, endDate, type, mCourseID);
            repo.insertAssessment(updatedAssessment);
        } else {
            Assessment newAssessment = new Assessment(++assessmentID, title, startDate, endDate, type,  mCourseID);
            repo.insertAssessment(newAssessment);
        }


        Intent intent = new Intent(this, AssessmentActivity.class);
//        if (mCourseId != -1) {
//            intent.putExtra("courseId", mCourseID);
//        } else {
//            intent.putExtra("courseId", mAssessCourseId);
//        }

        startActivity(intent);
    }





    public void deleteAssessment(View view) {
        repo.deleteAssessment(mSelectedAssessment);
        Toast.makeText(DetailedAssessmentActivity.this, "Assessment Deleted", Toast.LENGTH_LONG).show();

        //Navigating back to Term list after clicking delete button
        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }
}