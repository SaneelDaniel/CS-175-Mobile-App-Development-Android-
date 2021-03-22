package edu.sjsu.android.test_hw2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Activity Class to view the details of items in the list
 * the information detail activity shows the information of a specific animal,
 * when clicked from the main view
 */
public class InformationDetail extends MainActivity {
    ImageView imageView;
    TextView nameTextView;
    TextView descriptionTextView;
    TextView descriptionTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_detail);
        imageView = findViewById(R.id.information_imageView);
        nameTextView = findViewById(R.id.information_textView);
        descriptionTextView = findViewById(R.id.infoDetails_description_textView);
        descriptionTitle = findViewById(R.id.description_title_Text);
        Intent intent = getIntent();
        int imageId = intent.getIntExtra("imageResID",0);
        String name = "Name:   " + intent.getStringExtra("animalName") + "\n\n";
        String desc = intent.getStringExtra("animalDesc");
        imageView.setImageResource(imageId);
        nameTextView.setText(name);
        descriptionTextView.setText(desc);
    }
}