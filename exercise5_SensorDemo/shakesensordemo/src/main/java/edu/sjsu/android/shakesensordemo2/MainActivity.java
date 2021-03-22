package edu.sjsu.android.shakesensordemo2;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{
    private SensorManager sensorManager;
    private boolean color = false;
    private View view;
    private long lastUpdate;
    TextView textXAxis, textYAxis, textZAxis;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        textXAxis =(TextView) findViewById(R.id.xval);
        textYAxis =(TextView) findViewById(R.id.yval);
        textZAxis =(TextView) findViewById(R.id.zval);

        view = findViewById(R.id.shaker_textView);
        view.setBackgroundColor(Color.GRAY);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        lastUpdate = System.currentTimeMillis();
    }
    public void onSensorChanged(SensorEvent event)
    {
        if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            displayAccelerometer(event);
            checkShake(event);
        }
    }
    public void displayAccelerometer(SensorEvent event)
    {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        textXAxis.setText("X axis"+"\t\t"+ x);
        textYAxis.setText("Y axis"+"\t\t"+ y);
        textZAxis.setText("Z axis"+"\t\t"+ z);
    }
    private void checkShake(SensorEvent event)
    {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float accelerationSquareRoot = (x*x+y*y+z*z)/(SensorManager.GRAVITY_EARTH*SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();
        if(accelerationSquareRoot>=2)
        {
            if(actualTime-lastUpdate<200)
            {
                return;
            }
            lastUpdate=actualTime;
            Toast.makeText(this,"Don't Shake Me!",Toast.LENGTH_SHORT).show();
            if(color)
            {
                view.setBackgroundColor(Color.LTGRAY);
            }
            else
            {
                view.setBackgroundColor(Color.DKGRAY);
            }
            color=!color;
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }
    @Override
    protected void onResume()
    {
        super.onResume();
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
