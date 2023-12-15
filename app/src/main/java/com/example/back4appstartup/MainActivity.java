package com.example.back4appstartup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txtView = findViewById(R.id.textView);

        ParseObject firstObject = new ParseObject("COM594");
        firstObject.put("message", "First message from COM594. Parse is now connected");

        firstObject.saveInBackground(e -> {
            if (e != null) {
                Toast.makeText(this, "Fail to add data.." + e.getLocalizedMessage(),
                        Toast.LENGTH_LONG).show();
            } else {
                txtView.setText("Data saved is : " + firstObject.get("message"));
            }
        });
    }
}