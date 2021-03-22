package edu.sjsu.android.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle myInput = this.getIntent().getExtras();

        TextView text = new TextView(this);
        text = findViewById(R.id.textView_Print_Name);
        text.setText("Hello " + (myInput.get("uname")));
    }
}