package com.example.lonch.cameraapp;

import android.util.Log;
import android.widget.ImageButton;

/**
 * Created by lonch on 2016/6/20.
 */
public class adjust {

    String TAG = "adjust";

    public int setadjustImagebutton(ImageButton button, int gadjust ) {

        if (MyDebug.LOG) Log.d(TAG, "setAutoFlash = ");
        button.setEnabled(false);
        switch (gadjust) {
            case 0:
                button.setImageResource(R.drawable.ic_image_flash_auto);

                break;
            case 1:
                button.setImageResource(R.drawable.ic_image_flash_on);

                break;
            case 2:
                button.setImageResource(R.drawable.ic_image_flare);
                 break;
            case 3:
                button.setImageResource(R.drawable.ic_image_flash_off);
                   break;
        }
        gadjust++;
        if(gadjust >3) gadjust = 0;
        button.setEnabled(true);

        return gadjust;
    }

}
