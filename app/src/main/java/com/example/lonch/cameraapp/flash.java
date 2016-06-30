package com.example.lonch.cameraapp;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageButton;

/**
 * Created by lonch on 2016/6/10.
 */
public class flash {

    private boolean mFlashSupported = true;
    String TAG = "flash";
    flashState gLightning = flashState.AUTO;

    private enum flashState {
        ALL_OFF,
        AUTO,
        ALL_ON,
        TORCH,
      };


    public void setFlashSupported(Boolean available)
    {
        mFlashSupported = available == null ? false : available;
    }

    public boolean getFlashSupported()
    {
        return  mFlashSupported;
    }
    
    public int setflashImagebutton(ImageButton button ,
                                    int flashstate) {

        if (MyDebug.LOG) Log.d(TAG, "setAutoFlash = " + flashstate);
        button.setEnabled(false);
        switch (gLightning) {
             case ALL_OFF:
                button.setImageResource(R.drawable.ic_image_flash_auto);
                 flashstate = 0;
                gLightning = flashState.AUTO;
                break;
            case AUTO:
                button.setImageResource(R.drawable.ic_image_flash_on);
                flashstate = 1;
                gLightning = flashState.ALL_ON;
                break;
            case ALL_ON:
                button.setImageResource(R.drawable.ic_image_flare);
                flashstate = 2;
                gLightning = flashState.TORCH;
                break;
            case TORCH:
                button.setImageResource(R.drawable.ic_image_flash_off);
                flashstate = 3;
                gLightning = flashState.ALL_OFF;
                break;
          }
        button.setEnabled(true);

        return flashstate;
    }

    public void  setflash(         int flashstate,
                                   CaptureRequest.Builder mRequestBuilder,
                                   CameraCaptureSession mCameraCaptureSession,
                                   Handler mHandler,
                                   CameraCaptureSession.CaptureCallback mCaptureCallback) {

        if (MyDebug.LOG) Log.d(TAG, "setFlash = " + flashstate);

        switch (gLightning) {
            case ALL_OFF:
                mRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                mRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON);
                mRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);
                break;
            case AUTO:
                mRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                mRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON_AUTO_FLASH);
                mRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_SINGLE);
               break;
           case ALL_ON:
                mRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                mRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON_ALWAYS_FLASH);
                mRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_SINGLE);
                 break;
            case TORCH:
                 mRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO);
                mRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_ON);
                mRequestBuilder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_TORCH);
                 break;
            }

        try {
            mCameraCaptureSession.setRepeatingRequest(mRequestBuilder.build(), mCaptureCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("updatePreview", "ExceptionExceptionException");
        }
    }
}
