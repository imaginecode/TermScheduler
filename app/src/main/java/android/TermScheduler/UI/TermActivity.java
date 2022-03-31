package android.TermScheduler.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.TermScheduler.Adapters.TermAdapter;
import android.TermScheduler.Database.Repository;
import android.TermScheduler.Entity.Term;
import android.content.Intent;
import android.os.Bundle;
import android.TermScheduler.R;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class TermActivity extends AppCompatActivity {


//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_term_list);
//        RecyclerView recyclerView=findViewById(R.id.termRecyclerView);
//
//        Repository repo=new Repository(getApplication());
//        List<Term> terms=repo.getAllTerms();
//        final TermAdapter adapter=new TermAdapter(this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter.setTerms(terms);
//
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_termlist, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                this.finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//
//    }



    private RecyclerView mRecyclerView;
    private TermAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Repository repo;

    public List<Term> termList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // may need something here todo
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        repo = new Repository(getApplication());

        // Deals with adapter and recyclerview
        getTermsList();
        buildRecyclerView();

    }

    // get application
    public void getTermsList() {
        termList = repo.getAllTerms();
        //todo add course and assessment list here
    }

    // builder
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.termRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new TermAdapter(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.termsSetter(repo.getAllTerms());
        //todo add course version here
    }


    public void goToDetailedTerm(View view) {
        Intent intent = new Intent(TermActivity.this, DetailedTermActivity.class);
        startActivity(intent);
    }

}