package edu.sjsu.android.mybrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MyBrowser_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_browser__main);

        Intent receiver = getIntent();
        String data = receiver.getDataString();
        TextView textView = findViewById(R.id.text);
        textView.setText(data);
    }
}
