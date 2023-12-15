package com.example.back4appstartup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button queryA, queryB, queryC, clearResults;
    RecyclerView resultList;
    ResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queryA = findViewById(R.id.queryAButton);
        queryB = findViewById(R.id.queryBButton);
        queryC = findViewById(R.id.queryCButton);
        clearResults = findViewById(R.id.clearResultsButton);
        resultList = findViewById(R.id.resultsRecyclerView);

        queryA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> publisherQuery =
                        new ParseQuery<ParseObject>("Publisher");
                publisherQuery.whereEqualTo("name", "Birch Distributions");
                ParseObject publisher = null;
                try {
                    publisher = publisherQuery.getFirst();
                    ParseQuery<ParseObject> bookQuery =
                            new ParseQuery<ParseObject>("Book");
                    bookQuery.whereEqualTo("publisher", publisher);
                    bookQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e == null) {
                                System.out.println("Size " + objects.size());
                                initData(objects);
                            } else {
                                Log.d("ParseQuery", e.getMessage());
                            }
                        }
                    });
                } catch (ParseException e ) {
                    e.printStackTrace();
                }
            }
        });

        queryB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> bookQuery = new ParseQuery<ParseObject>("Book");

                Calendar calendar = Calendar.getInstance();
                calendar.set(2010,1,1,59,59,59);
                Date date = calendar.getTime();

                bookQuery.whereGreaterThan("publishingDate", date);

                ParseQuery<ParseObject> bookStoreQuery =
                        new ParseQuery<ParseObject>("BookStore");
                bookStoreQuery.whereMatchesQuery("books", bookQuery);
                bookStoreQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e == null) {
                            System.out.println("Size" + objects.size());
                            initData(objects);
                        } else {
                            Log.d("ParseQuery", e.getMessage());
                        }
                    }
                });
            }
        });

        queryC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> authorQuery =
                        new ParseQuery<ParseObject>("Author");
                authorQuery.whereEqualTo("name", "Aaron Writer");
                ParseObject authorA = null;
                try {
                    authorA = authorQuery.getFirst();
                    ParseQuery<ParseObject> bookQuery = new ParseQuery<>("Book");
                    bookQuery.whereEqualTo("authors", authorA);
                    ParseQuery<ParseObject> bookStoreQuery =
                            new ParseQuery<> ("BookStore");
                    bookStoreQuery.whereMatchesQuery("books", bookQuery);

                    bookStoreQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            System.out.println("Size" + objects.size());
                            initData(objects);
                        }
                    });
                } catch (ParseException e ) {
                    e.printStackTrace();
                }
            }
        });

        clearResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.clearList();
            }
        });
    }

    public void initData(List<ParseObject> objects) {
        adapter = new ResultAdapter(this, objects);
        resultList.setLayoutManager(new LinearLayoutManager(this));
        resultList.setAdapter(adapter);
    }
}