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
    private RecyclerView mRecyclerView;
    private TermAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Repository repo;

    public List<Term> termList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }


    public void goToDetailedTerm(View view) {
        Intent intent = new Intent(TermActivity.this, DetailedTermActivity.class);
        startActivity(intent);
    }

}