package com.example.guy.gamebattleship;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.Vibrator;
import android.widget.GridView;
import android.widget.ImageView;


import com.example.guy.gamebattleship.Logic.*;

/**
 * Created by ajayhack on 14/2/18.
 */

public class MyService extends Service implements SensorEventListener {

    private Vibrator v;
    private Sensor accelerometer;
    private SensorManager sm;
    private double xRate;
    private boolean changed=true;
    private int counter=0;
    private final int ROTATE_MAX=100;
    private final IBinder binder = new MyLocalService();
    private boolean isDestroyed=false;
    private boolean update=true;
    private ServiceCallbacks serviceCallbacks;
    private long endTime;
    public MyService() {
    }

    @Override

    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        sm= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sm.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isDestroyed=false;
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        isDestroyed=true;
        v.cancel();
    }
    public void updageSensor()
    {
        update=true;
    }


    public void setIsDestroy(boolean isDestroy){
        isDestroyed=isDestroy;
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!isDestroyed) {
            if (changed) {
                xRate = event.values[0];
                changed = false;
            } else {
                if(update)
                {
                    update=false;
                    xRate = event.values[0];
                    endTime = System.currentTimeMillis() + 5*1000;
                }
                if (Math.abs(xRate - event.values[0]) > 0.12) {
                    counter++;
                    if (System.currentTimeMillis() > endTime) {
                        endTime = System.currentTimeMillis() + 5*1000;
                        if (serviceCallbacks != null) {
                            serviceCallbacks.Moving();
                        }
                    }
                } else {
                    endTime = System.currentTimeMillis() + 5*1000;
                    if (serviceCallbacks != null) {
                        serviceCallbacks.show();
                    }
                }
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    public void setCallbacks(ServiceCallbacks callbacks) {
        serviceCallbacks = callbacks;
    }

    class MyLocalService extends Binder{
        MyService getService()
        {
            return MyService.this;
        }
    }
}