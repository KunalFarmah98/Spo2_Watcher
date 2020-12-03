package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.PowerManager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.kunalfarmah.Spo2Watcher.Math.Fft;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;

public class RecordVitalSigns extends AppCompatActivity {

    SharedPreferences sref;

    //Variables Initialization
    private static final AtomicBoolean processing = new AtomicBoolean(false);
    private SurfaceView preview = null;
    private static SurfaceHolder previewHolder = null;
    private static Camera camera = null;
    private static PowerManager.WakeLock wakeLock = null;
    private static TextView info;

    //Freq + timer variable
    private static long startTime = 0;
    private double SamplingFreq;

    // SPO2 variables
    private static double RedBlueRatio = 0;
    double Stdr = 0;
    double Stdb = 0;
    double sumred = 0;
    double sumblue = 0;
    public int o2;

    //Arraylist
    public ArrayList<Double> RedAvgList = new ArrayList<Double>();
    public ArrayList<Double> BlueAvgList = new ArrayList<Double>();
    public int counter = 0;

    //Toast
    private Toast mainToast;

    //DataBase
    public String user;

    //ProgressBar
    private ProgressBar ProgHeart;
    public int ProgP = 0;
    public int inc = 0;

    //Beats variable
    public int Beats = 0;
    public double bufferAvgB = 0;

    //RR variable
    public int Breath = 0;
    public double bufferAvgBr = 0;

    //BloodPressure variables
    public double Gen, Agg, Hei, Wei;
    public double Q = 4.5;
    private static int SP = 0, DP = 0;

    //Arraylist
    public ArrayList<Double> GreenAvgList = new ArrayList<Double>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_vital_signs);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("Usr");
            //The key argument here must match that used in the other activity
        }

        sref = getSharedPreferences("Info", MODE_PRIVATE);
        info = findViewById(R.id.info);

        //Get parameters from Db
//            Hei = Integer.parseInt(Data.getheight(user));
//            Wei = Integer.parseInt(Data.getweight(user));
//            Agg = Integer.parseInt(Data.getage(user));
//            Gen = Integer.parseInt(Data.getgender(user));

        Hei = sref.getInt("Height", 178);
        Wei = sref.getInt("Weight", 75);
        Agg = sref.getInt("Age", 20);

        String gen = sref.getString("Gender", "M");
        if (gen.equalsIgnoreCase("M"))
            Gen = 1;
        else
            Gen = 0;

        if (Gen == 1) {
            Q = 5;
        }

        // XML - Java Connecting
        preview = (SurfaceView) findViewById(R.id.preview);
        previewHolder = preview.getHolder();
        previewHolder.addCallback(surfaceCallback);
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        ProgHeart = (ProgressBar) findViewById(R.id.VSPB);
        ProgHeart.setProgress(0);

        // WakeLock Initialization : Forces the phone to stay On
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "DoNotDimScreen:");
    }

    //Prevent the system from restarting your activity during certain configuration changes,
    // but receive a callback when the configurations do change, so that you can manually update your activity as necessary.
    //such as screen orientation, keyboard availability, and language

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    //Wakelock + Open device camera + set orientation to 90 degree
    //store system time as a start time for the analyzing process
    //your activity to start interacting with the user.
    @Override
    public void onResume() {

        super.onResume();

        wakeLock.acquire();

        camera = Camera.open();

        camera.setDisplayOrientation(90);

        startTime = System.currentTimeMillis();


    }

    //call back the frames then release the camera + wakelock and Initialize the camera to null
    //Called as part of the activity lifecycle when an activity is going into the background, but has not (yet) been killed. The counterpart to onResume().
    //When activity B is launched in front of activity A,
    //this callback will be invoked on A. B will not be created until A's onPause() returns, so be sure to not do anything lengthy here.
    @Override
    public void onPause() {
        super.onPause();
        wakeLock.release();
        camera.setPreviewCallback(null);
        camera.stopPreview();
        camera.release();
        camera = null;

    }

    //getting frames data from the camera and start the measuring process
    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {

        /**
         * {@inheritDoc}
         */
        @Override
        public void onPreviewFrame(byte[] data, Camera cam) {
            //if data or size == null ****
            if (data == null) throw new NullPointerException();
            Camera.Size size = cam.getParameters().getPreviewSize();
            if (size == null) throw new NullPointerException();

            //Atomically sets the value to the given updated value if the current value == the expected value.
            if (!processing.compareAndSet(false, true)) return;

            //put width + height of the camera inside the variables
            int width = size.width;
            int height = size.height;

            //RGB intensities initialization
            double GreenAvg;
            double RedAvg;
            double BlueAvg;

            RedAvg = ImageProcessing.decodeYUV420SPtoRedBlueGreenAvg(data.clone(), height, width, 1); //1 stands for red intensity, 2 for blue, 3 for green
            sumred = sumred + RedAvg;
            BlueAvg = ImageProcessing.decodeYUV420SPtoRedBlueGreenAvg(data.clone(), height, width, 2); //1 stands for red intensity, 2 for blue, 3 for green
            sumblue = sumblue + BlueAvg;
            GreenAvg = ImageProcessing.decodeYUV420SPtoRedBlueGreenAvg(data.clone(), height, width, 3); //Getting Green intensity after applying image processing on frame data, 3 stands for green

            RedAvgList.add(RedAvg);
            BlueAvgList.add(BlueAvg);
            GreenAvgList.add(GreenAvg);

            ++counter; //countes number of frames in 30 seconds

            if(ProgHeart.getProgress() >= ProgHeart.getMax()){
                ProgHeart.setProgress(0);
                info.setText(getResources().getString(R.string.analysing));
            }

            //To check if we got a good red intensity to process if not return to the condition and set it again until we get a good red intensity
            if (RedAvg < 200) {
                inc = 0;
                ProgP = inc;
                ProgHeart.setProgress(ProgP);
                processing.set(false);
            }

            long endTime = System.currentTimeMillis();
            double totalTimeInSecs = (endTime - startTime) / 1000d; //to convert time to seconds
            if (totalTimeInSecs >= 30) { //when 30 seconds of measuring passes do the following " we chose 30 seconds to take half sample since 60 seconds is normally a full sample of the heart beat

                startTime = System.currentTimeMillis();
                SamplingFreq = (counter / totalTimeInSecs);
                Double[] Red = RedAvgList.toArray(new Double[RedAvgList.size()]);
                Double[] Blue = BlueAvgList.toArray(new Double[BlueAvgList.size()]);
                double HRFreq = Fft.FFT(Red, counter, SamplingFreq);
                double bpm = (int) ceil(HRFreq * 60);

                double meanr = sumred / counter;
                double meanb = sumblue / counter;

                for (int i = 0; i < counter - 1; i++) {

                    Double bufferb = Blue[i];

                    Stdb = Stdb + ((bufferb - meanb) * (bufferb - meanb));

                    Double bufferr = Red[i];

                    Stdr = Stdr + ((bufferr - meanr) * (bufferr - meanr));

                }

                double varr = sqrt(Stdr / (counter - 1));
                double varb = sqrt(Stdb / (counter - 1));

                double R = (varr / meanr) / (varb / meanb);

                double spo2 = 100 - 5 * (R);
                o2 = (int) (spo2);

                if ((o2 < 80 || o2 > 99) || (bpm < 45 || bpm > 200)) {
                    inc = 0;
                    ProgP = inc;
                    ProgHeart.setProgress(ProgP);
                    mainToast = Toast.makeText(getApplicationContext(), "Measurement Failed. Please don't remove your finger before 30 seconds or measure in a darker environment.", Toast.LENGTH_SHORT);
                    mainToast.show();
                    info.setText("Recording and Measuring");
                    startTime = System.currentTimeMillis();
                    counter = 0;
                    processing.set(false);
                    return;
                }

            }

            if (RedAvg != 0) {
                ProgP = inc++ / 34;
                ProgHeart.setProgress(ProgP);
            }

            processing.set(false);

            if (totalTimeInSecs >= 30) { //when 30 seconds of measuring passes do the following " we chose 30 seconds to take half sample since 60 seconds is normally a full sample of the heart beat

                Double[] Green = GreenAvgList.toArray(new Double[GreenAvgList.size()]);
                Double[] Red = RedAvgList.toArray(new Double[RedAvgList.size()]);

                SamplingFreq = (counter / totalTimeInSecs); //calculating the sampling frequency

                double HRFreq = Fft.FFT(Green, counter, SamplingFreq); // send the green array and get its fft then return the amount of heartrate per second
                double bpm = (int) ceil(HRFreq * 60);
                double HR1Freq = Fft.FFT(Red, counter, SamplingFreq);  // send the red array and get its fft then return the amount of heartrate per second
                double bpm1 = (int) ceil(HR1Freq * 60);

                // The following code is to make sure that if the heartrate from red and green intensities are reasonable
                // take the average between them, otherwise take the green or red if one of them is good

                if ((bpm > 45 || bpm < 200)) {
                    if ((bpm1 > 45 || bpm1 < 200)) {

                        bufferAvgB = (bpm + bpm1) / 2;
                    } else {
                        bufferAvgB = bpm;
                    }
                } else if ((bpm1 > 45 || bpm1 < 200)) {
                    bufferAvgB = bpm1;
                }

                if (bufferAvgB < 45 || bufferAvgB > 200) { //if the heart beat wasn't reasonable after all reset the progresspag and restart measuring
                    inc = 0;
                    ProgP = inc;
                    ProgHeart.setProgress(ProgP);
                    mainToast = Toast.makeText(getApplicationContext(), "Measurement Failed", Toast.LENGTH_SHORT);
                    mainToast.show();
                    info.setText(getResources().getString(R.string.measuring));
                    startTime = System.currentTimeMillis();
                    counter = 0;
                    processing.set(false);
                    return;
                }

                Beats = (int) bufferAvgB;
            }


            if (RedAvg != 0) { //increment the progressbar

                ProgP = inc++ / 34;
                ProgHeart.setProgress(ProgP);
            }

            //keeps taking frames till 30 seconds
            processing.set(false);

            //To check if we got a good red intensity to process if not return to the condition and set it again until we get a good red intensity
            if (RedAvg < 200) {
                inc = 0;
                ProgP = inc;
                counter = 0;
                ProgHeart.setProgress(ProgP);
                processing.set(false);
            }

            //if all those variable contains a valid values then swap them to results activity and finish the processing activity
            if ((Beats != 0)  && (o2 != 0)) {
                Intent i = new Intent(RecordVitalSigns.this, VitalSignsResults.class);
                i.putExtra("O2R", o2);
                i.putExtra("bpm", Beats);
                i.putExtra("Usr", user);
                startActivity(i);
                finish();
            }

// for testing
            /*Intent i = new Intent(RecordVitalSigns.this, VitalSignsResults.class);
            i.putExtra("O2R", 92);
            i.putExtra("bpm", 60);
            i.putExtra("Usr", user);
            startActivity(i);
            finish();*/

            //keeps incrementing the progress bar and keeps the loop until we have a valid values for the previous if state
            if (RedAvg != 0) {
                ProgP = inc++ / 34;
                ProgHeart.setProgress(ProgP);
            }
            processing.set(false);
        }
    };

    private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(previewHolder);
                camera.setPreviewCallback(previewCallback);
            } catch (Throwable t) {

            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);

            Camera.Size size = getSmallestPreviewSize(width, height, parameters);
            if (size != null) {
                parameters.setPreviewSize(size.width, size.height);
            }

            camera.setParameters(parameters);
            camera.startPreview();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
        }
    };

    private static Camera.Size getSmallestPreviewSize(int width, int height, Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;
                    if (newArea < resultArea) result = size;
                }
            }
        }

        return result;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(RecordVitalSigns.this, MonitoringInstructions.class);
        i.putExtra("Usr", user);
        startActivity(i);
        finish();
    }
}

