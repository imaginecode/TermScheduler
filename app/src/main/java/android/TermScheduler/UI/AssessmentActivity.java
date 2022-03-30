package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.TermScheduler.R;
import android.view.View;

public class AssessmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);
    }


    public void addAssessment(View view) {
        Intent intent = new Intent(AssessmentActivity.this, DetailedAssessmentActivity.class);
        startActivity(intent);
    }
}