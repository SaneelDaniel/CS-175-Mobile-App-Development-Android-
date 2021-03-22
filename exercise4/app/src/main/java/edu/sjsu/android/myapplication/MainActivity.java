package edu.sjsu.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button webBrowserButton;
    Button makeCallButtoon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webBrowserButton = findViewById(R.id.button_webBrowser);

        webBrowserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri myUriString = Uri.parse("http://www.amazon.com");

                Intent intent= new Intent(Intent.ACTION_VIEW, myUriString);

                Intent chooser = Intent.createChooser(intent,"Please Choose A Browser");

                startActivity(chooser);
            }
        });

        makeCallButtoon = findViewById(R.id.button3);
        makeCallButtoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "tel:+1669 278-6078";
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(number));
                startActivity(callIntent);
            }
        });


    }




}