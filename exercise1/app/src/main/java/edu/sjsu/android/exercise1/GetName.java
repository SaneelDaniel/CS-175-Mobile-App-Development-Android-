package edu.sjsu.android.exercise1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetName extends Activity implements android.view.View.OnClickListener {

    EditText name;
    Button submit_button;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_get_name);
        name = findViewById(R.id.editText_Get_Name);
        submit_button = findViewById(R.id.button_submit);
        submit_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent myIntent = new Intent(this,MainActivity.class);
        myIntent.putExtra("uname",name.getText().toString());
        this.startActivity(myIntent);
    }
}
