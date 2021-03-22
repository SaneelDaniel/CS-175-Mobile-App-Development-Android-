package edu.sjsu.android.test_hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

/**
 * information activity class
 * which is called when the action bar information option is selected.
 */
public class Information extends MainActivity {

    // UI Components on the view
    TextView mainText;
    TextView telephone;
    ImageView zooImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        mainText = findViewById(R.id.information_textView_Welcome);
        telephone = findViewById(R.id.zooInfo_tel_textView);
        telephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uri = "tel: 669 278-5078";
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
                startActivity(intent);
            }
        });
        zooImage = findViewById(R.id.zoo_info_imageView);
        zooImage.setImageResource(R.drawable.zoo);
    }
}