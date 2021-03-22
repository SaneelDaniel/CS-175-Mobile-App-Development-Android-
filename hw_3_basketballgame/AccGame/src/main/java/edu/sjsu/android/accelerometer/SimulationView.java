package edu.sjsu.android.accelerometer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;


/**
 * Simulation View Class, that holds a View for the simulation
 * The Class holds sensors and reference to sensor objects,
                                 and acts as a sensor listener
 */
public class SimulationView extends View implements SensorEventListener {

    //List of private variables needed
    private Bitmap mField;
    private Bitmap mBasket;
    private Bitmap mBitmap;

    private static final int BALL_SIZE = 64;
    private static final int BASKET_SIZE = 80;

    private float mXOrigin;
    private float mYOrigin;
    private float mHorizontalBound;
    private float mVerticalBound;

    private Particle mBall = new Particle();

    private float mSensorX;
    private float mSensorY;
    private float mSensorZ;
    private long mSensorTimeStamp;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private Display mDisplay;
    private int displayWidth;
    private int displayHeight;
    private Point size;
    //List of private variables ends


    /**
     *Constructor for the simulation view that takes the context reference
                                          where the view is to be displayed
     * Initializes all variables and objects
     * @param context
     */
    public SimulationView(Context context) {
        super(context);

        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mDisplay = mWindowManager.getDefaultDisplay();

        mDisplay = mWindowManager.getDefaultDisplay();
        size = new Point();
        mDisplay.getSize(size);s
        displayWidth = size.x;
        displayHeight = size.y;

        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Bitmap field = BitmapFactory.decodeResource(getResources(), R.drawable.field1);
        mField = Bitmap.createScaledBitmap(field, displayWidth, displayHeight, true);

        Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        mBitmap = Bitmap.createScaledBitmap(ball, BALL_SIZE, BALL_SIZE, true);

        Bitmap basket = BitmapFactory.decodeResource(getResources(), R.drawable.basket);
        mBasket = Bitmap.createScaledBitmap(basket, BASKET_SIZE, BASKET_SIZE, true);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;
    }


    @Override
    /**
     * Overridden onDraw method that draws the respective components
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mField, 0, 0, null);
        canvas.drawBitmap(mBasket, mXOrigin - BASKET_SIZE/2 , mYOrigin - BASKET_SIZE/2, null);

        mBall.updatePosition(mSensorX, mSensorY, mSensorZ, (long) mSensorTimeStamp);
        mBall.resolveCollisionWithBounds(mHorizontalBound, mVerticalBound);

        canvas.drawBitmap(mBitmap,
                (mXOrigin - BALL_SIZE / 2) + mBall.mPosX,
                (mYOrigin - BALL_SIZE / 2) - mBall.mPosY, null);
        invalidate();
    }


    @Override
    /**
     * Method to deal with sensor change situations and update respective fields/values
     * mainly changes the x, y, & z coordinate values to update the view
     */
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;

        if(mDisplay.getRotation() == Surface.ROTATION_0){
           mSensorX = sensorEvent.values[0];
           mSensorY = sensorEvent.values[1];
        }
        else if(mDisplay.getRotation() == Surface.ROTATION_90){
           mSensorX = -sensorEvent.values[1];
           mSensorY = sensorEvent.values[0];
        }
        else if(mDisplay.getRotation() == Surface.ROTATION_180){
           mSensorX = -sensorEvent.values[0];
           mSensorY = -sensorEvent.values[1];
        }
        else if(mDisplay.getRotation() == Surface.ROTATION_270){
           mSensorX = sensorEvent.values[0];
           mSensorY = -sensorEvent.values[1];
        }
        mSensorZ = sensorEvent.values[2];
        mSensorTimeStamp = sensorEvent.timestamp;
    }


    @Override
    /**
     * overridden method not needed for current implementation
     */
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    /**
     * Method that registers listeners for sensors to the view
     */
    public void startSimulation() {
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Method that Un-Registers listeners for sensors to the view
     */
    public void stopSimulation() {
        mSensorManager.unregisterListener(this);
    }


    @Override
    /**
     * Method that fetches and initializes the current display dimensions
     */
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mXOrigin = (float) (w * 0.5);
        mYOrigin = (float) (h * 0.5) ;
        mHorizontalBound = (float) ((w) * 0.5);
        mVerticalBound = (float) ((h - BALL_SIZE) * 0.5);

        Bitmap field = BitmapFactory.decodeResource(getResources(), R.drawable.field1);
        mField = Bitmap.createScaledBitmap(field, displayWidth, displayHeight, true);

        Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        mBitmap = Bitmap.createScaledBitmap(ball, BALL_SIZE, BALL_SIZE, true);

        Bitmap basket = BitmapFactory.decodeResource(getResources(), R.drawable.basket);
        mBasket = Bitmap.createScaledBitmap(basket, BASKET_SIZE, BASKET_SIZE, true);

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = true;
        opts.inPreferredConfig = Bitmap.Config.RGB_565;

    }
}
