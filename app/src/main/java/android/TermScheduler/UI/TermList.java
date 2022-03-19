package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Term;
import android.content.Intent;
import android.os.Bundle;
import android.TermScheduler.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.List;

public class TermList extends AppCompatActivity {

    EditText editTermName;
    EditText editTermStart;
    EditText editTermEnd;
    String termName;
    String termStart;
    String termEnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        Repository repo=new Repository(getApplication());
        List<Term> terms=repo.getAllTerms();
        final TermAdapter adapter=new TermAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setTerms(terms);

        //This block of code is causing the whole app to crash

//        //Using variables termName Start and End to edit and save data
//        editTermName=findViewById(R.id.editTermName);
//        editTermStart=findViewById(R.id.editTermStart);
//        editTermEnd=findViewById(R.id.editTermEnd);
//        termName=getIntent().getStringExtra("name");
//        termStart=getIntent().getStringExtra("startDate");
//        termEnd=getIntent().getStringExtra("endDate");
//
//        //Getting text that ws set into strings termName and setting text that was edited
//        editTermName.setText(termName);
//        editTermStart.setText(termStart);
//        editTermEnd.setText(termEnd);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_termlist, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public void goToDetailedTerm(View view) {
        Intent intent = new Intent(TermList.this, DetailedTermView.class);
        startActivity(intent);
    }
}