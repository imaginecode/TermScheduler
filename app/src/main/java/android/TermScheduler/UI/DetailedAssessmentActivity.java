package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Assessment;
import android.TermScheduler.Entity.Course;
import android.TermScheduler.R;
import android.TermScheduler.Utilities.MyReceiver;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
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

    EditText editTitle;
    EditText editType;
    EditText editStart;
    EditText editEnd;
    Repository repo;
    List<Assessment> mAssessmentList;

    Assessment mSelectedAssessment;
    Calendar mCalendarStart = Calendar.getInstance();
    Calendar mCalendarEnd = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener mStartDatePicker;
    DatePickerDialog.OnDateSetListener mEndDatePicker;
    SimpleDateFormat sdf;
    int mCourseID;
    int mAssessmentID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_assessment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String myDateFormat = "MM/dd/yyyy";
        sdf = new SimpleDateFormat(myDateFormat, Locale.US);
        repo = new Repository(getApplication());

        setAssessmentViews();

        setDates();
    }

    // Setting assessment Views and populating editTexts
    public void setAssessmentViews(){


        mCourseID = DetailedCourseActivity.activeCourseID;
        mAssessmentID = getIntent().getIntExtra("assessmentID", -1);

        mAssessmentList = repo.getAllAssessments();
        for (Assessment assessment : mAssessmentList) {
            if (assessment.getAssessmentID() == mAssessmentID) {
                mSelectedAssessment = assessment;
            }
        }

        if(mAssessmentID == -1) {
            findViewById(R.id.deleteAssessment).setVisibility(View.INVISIBLE);
        }

        editTitle = findViewById(R.id.editAssessmentTitle);
        editStart = findViewById(R.id.editStartDateAssessment);
        editEnd = findViewById(R.id.editEndDateAssessment);
        editType = findViewById(R.id.AssessmentType);

        if (mAssessmentID != -1) {
            editTitle.setText(mSelectedAssessment.getAssessmentTitle());
            editStart.setText(mSelectedAssessment.getAssessmentStart());
            editEnd.setText(mSelectedAssessment.getAssessmentEnd());
            editType.setText(mSelectedAssessment.getAssessmentType());
        }
    }

    //Setting date pickers for start and end date fields

    public void setDates(){
        mStartDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarStart.set(Calendar.YEAR, year);
                mCalendarStart.set(Calendar.MONTH, month);
                mCalendarStart.set(Calendar.DAY_OF_MONTH, day);
                updateDateStartText();
            }
        };
        mEndDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mCalendarEnd.set(Calendar.YEAR, year);
                mCalendarEnd.set(Calendar.MONTH, month);
                mCalendarEnd.set(Calendar.DAY_OF_MONTH, day);
                updateDateEndText();
            }
        };

        editStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DetailedAssessmentActivity.this, mStartDatePicker, mCalendarStart.get(Calendar.YEAR)
                        , mCalendarStart.get(Calendar.MONTH), mCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        editEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(DetailedAssessmentActivity.this, mEndDatePicker, mCalendarEnd.get(Calendar.YEAR),
                        mCalendarEnd.get(Calendar.MONTH), mCalendarEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

//Setting edit start and end fields
    public void updateDateStartText() {
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);

        editStart.setText(sdf.format(mCalendarStart.getTime()));
    }

    public void updateDateEndText(){
        String format = "MM/dd/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.US);

        editEnd.setText(sdf.format(mCalendarEnd.getTime()));
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

        try{switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

            case R.id.assessmentStartNotification:
                String editTextDate = editStart.getText().toString();
                Date startDate = null;

                try {
                    startDate = sdf.parse(editTextDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long timeTrigger = startDate.getTime();
                Intent intent = new Intent(DetailedAssessmentActivity.this, MyReceiver.class);
                intent.putExtra("key", mSelectedAssessment.getAssessmentTitle() + " assessment starts today!");
                Toast.makeText(DetailedAssessmentActivity.this, "Notifications On ", Toast.LENGTH_SHORT).show();

                PendingIntent send = PendingIntent.getBroadcast(DetailedAssessmentActivity.this, MainActivity.alertNum++,intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,timeTrigger, send);
                return true;

            case R.id.assessmentEndNotification:
                String editTextEndDate = editEnd.getText().toString();
                Date endDate = null;

                try {
                    endDate = sdf.parse(editTextEndDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Long endTrigger = endDate.getTime();
                Intent endIntent = new Intent(DetailedAssessmentActivity.this, MyReceiver.class);
                endIntent.putExtra("key", mSelectedAssessment.getAssessmentTitle() + " assessment ends today!");
                Toast.makeText(DetailedAssessmentActivity.this, "Assessment end notification set", Toast.LENGTH_SHORT).show();

                PendingIntent sendEnd = PendingIntent.getBroadcast(DetailedAssessmentActivity.this, MainActivity.alertNum++, endIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManger = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                endAlarmManger.set(AlarmManager.RTC_WAKEUP,endTrigger,sendEnd);
                return true;
        }}
        catch(Exception e){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Save Assessment before setting alert");
            alertDialog.setMessage("Assessment must be saved before setting alert!");
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


//save assessment
    public void saveAssessment(View view) {

        String assessmentName, startDate, endDate,type;


        assessmentName = editTitle.getText().toString();
        startDate = editStart.getText().toString();
        endDate = editEnd.getText().toString();
        type = editType.getText().toString();

        mAssessmentList = repo.getAllAssessments();

        int assessmentID = 1;
        //Adding to assessment list and assessment ID is set to 1 as I have inserted 1 ID already in the main activity
        for (Assessment assessment : mAssessmentList) {
            if (assessment.getAssessmentID() >= assessmentID)
            {
                assessmentID = assessment.getAssessmentID();
            }
        }

        if (mAssessmentID != -1) {
            Assessment updatedAssessment = new Assessment(mAssessmentID, assessmentName, startDate, endDate, type, mCourseID);
            repo.insertAssessment(updatedAssessment);

            Intent intent = new Intent(this, AssessmentActivity.class);
            startActivity(intent);
        } else {

            if (assessmentName.trim().isEmpty() || startDate.trim().isEmpty() || endDate.trim().isEmpty() || type.trim().isEmpty()) {

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
                Assessment newAssessment = new Assessment(++assessmentID, assessmentName, startDate, endDate, type,  mCourseID);
                repo.insertAssessment(newAssessment);

                //Navigating back to assessment list after clicking delete button
                Intent intent = new Intent(this, AssessmentActivity.class);
                startActivity(intent);
            }

            }

        }



    public void deleteAssessment(View view) {
        repo.deleteAssessment(mSelectedAssessment);
        Toast.makeText(DetailedAssessmentActivity.this, "Assessment Deleted", Toast.LENGTH_LONG).show();

        //Navigating back to assessment list after clicking delete button
        Intent intent = new Intent(this, AssessmentActivity.class);
        startActivity(intent);
    }
}