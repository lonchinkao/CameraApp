package com.example.lonch.cameraapp;

import android.util.Log;
import android.widget.ImageButton;

/**
 * Created by lonch on 2016/6/18.
 */
public class frontback {

    String TAG = "frontback";

    public void setImagebutton(ImageButton button, String CameraId) {

        if (MyDebug.LOG) Log.d(TAG, "CameraId  = " + CameraId);
        button.setEnabled(false);
        if (CameraId == "0")
        {
           button.setImageResource(R.drawable.ic_camera_rear_white_48dp);
            CameraId = "1";
        }
        else
        {
            button.setImageResource(R.drawable.ic_camera_front_white_48dp);
            CameraId = "0";
        }
        button.setEnabled(true);

    }
}
