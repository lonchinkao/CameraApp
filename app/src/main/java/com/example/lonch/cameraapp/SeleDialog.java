package com.example.lonch.cameraapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lonch on 2016/6/7.
 */
public class SeleDialog extends DialogFragment implements View.OnClickListener {

    static private Context mActivity;
    static private int SeleViewId;


    private int scene_mode = CameraMetadata.CONTROL_SCENE_MODE_DISABLED;
    private int color_effect = CameraMetadata.CONTROL_EFFECT_MODE_OFF;
    private int white_balance = CameraMetadata.CONTROL_AWB_MODE_AUTO;

    String TAG = "SeleDiag";

    private CaptureRequest.Builder mRequestBuilder;
    private CameraCaptureSession mCameraCaptureSession;
    private Handler mHandler;
    private  CameraCaptureSession.CaptureCallback mCaptureCallback;

    public static SeleDialog newInstance(Context m) {
        mActivity = m;
        return  new SeleDialog();
    }

    public void init (
            CaptureRequest.Builder mRequestBuilder,
            CameraCaptureSession mCameraCaptureSession,
            Handler mHandler,
            CameraCaptureSession.CaptureCallback mCaptureCallback) {

        this.mRequestBuilder = mRequestBuilder;
        this.mCameraCaptureSession = mCameraCaptureSession;
        this.mHandler = mHandler;
        this.mCaptureCallback = mCaptureCallback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selelist, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        view.findViewById(R.id.imageButton1).setOnClickListener(this);
        view.findViewById(R.id.imageButton2).setOnClickListener(this);
        view.findViewById(R.id.imageButton3).setOnClickListener(this);
        view.findViewById(R.id.imageButton4).setOnClickListener(this);
        view.findViewById(R.id.imageButton5).setOnClickListener(this);
        view.findViewById(R.id.imageButton6).setOnClickListener(this);
        setDialogPosition();
        return view;
    }

    private void setDialogPosition() {
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.LEFT);

        WindowManager.LayoutParams params = window.getAttributes();
        params.y = dpToPx(64);
        window.setAttributes(params);
    }

    private int dpToPx(int dp) {
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
    @Override
    public void onClick(View v) {
        SeleViewId = v.getId();
          switch (SeleViewId) {
             case R.id.imageButton1:
                 selectColorEffect(SceneMode, gSceneMode, "Scene Mode");
                 break;
             case R.id.imageButton2:
                 seekselect(mActivity);
                 break;
            case R.id.imageButton3:
                seekISO();
                break;
            case R.id.imageButton4:
                selectColorEffect(WhiteBalance, gWhiteBalance, "White Balance");
                break;
            case R.id.imageButton5:
                selectColorEffect(ColorEffect, gColorEffect,"Color Effect");
                break;
           }
    }


    int gColorEffect = 2;
    private String[] ColorEffect = new String[]{
            "aqua",
            "blackboard",
            "mono",
            "negative",
            "none",
            "posterize",
            "sepia",
            "solarize",
            "whiteboard"
    };
    private int[] ColorEffectData = new int[] {
            CameraMetadata.CONTROL_EFFECT_MODE_AQUA,
            CameraMetadata.CONTROL_EFFECT_MODE_BLACKBOARD,
            CameraMetadata.CONTROL_EFFECT_MODE_MONO,
            CameraMetadata.CONTROL_EFFECT_MODE_NEGATIVE,
            CameraMetadata.CONTROL_EFFECT_MODE_OFF,
            CameraMetadata.CONTROL_EFFECT_MODE_POSTERIZE,
            CameraMetadata.CONTROL_EFFECT_MODE_SEPIA,
            CameraMetadata.CONTROL_EFFECT_MODE_SOLARIZE,
            CameraMetadata.CONTROL_EFFECT_MODE_WHITEBOARD,
    } ;
    int gWhiteBalance = 3;

    String[] WhiteBalance = new String[]{
            "auto",
            "cloudy-daylight",
            "daylight",
            "fluorescent",
            "incandescent",
            "shade",
            "twilight",
            "warm-fluorescent",
    };
    private int[] WhiteBalanceData = new int[] {
      CameraMetadata.CONTROL_AWB_MODE_AUTO,
      CameraMetadata.CONTROL_AWB_MODE_CLOUDY_DAYLIGHT,
      CameraMetadata.CONTROL_AWB_MODE_DAYLIGHT,
      CameraMetadata.CONTROL_AWB_MODE_FLUORESCENT,
      CameraMetadata.CONTROL_AWB_MODE_INCANDESCENT,
      CameraMetadata.CONTROL_AWB_MODE_SHADE,
      CameraMetadata.CONTROL_AWB_MODE_TWILIGHT,
      CameraMetadata.CONTROL_AWB_MODE_WARM_FLUORESCENT,
        };

    int gSceneMode = 6;

    String[] SceneMode = new String[]{
            "action",
            "barcode",
            "beach",
            "candlelight",
            "auto",
            "fireworks",
            "high-speed-video"
            , "landscape"
            , "night"
            , "night-portrait"
            , "party"
            , "portrait"
            , "snow"
            , "sports"
            , "steadyphoto"
            , "sunset"
            , "theatre"
    };

    private int[] SceneModeData = new int[]{
            CameraMetadata.CONTROL_SCENE_MODE_ACTION,
            CameraMetadata.CONTROL_SCENE_MODE_BARCODE,
            CameraMetadata.CONTROL_SCENE_MODE_BEACH,
            CameraMetadata.CONTROL_SCENE_MODE_CANDLELIGHT,
            CameraMetadata.CONTROL_SCENE_MODE_DISABLED,
            CameraMetadata.CONTROL_SCENE_MODE_FIREWORKS,
            CameraMetadata.CONTROL_SCENE_MODE_LANDSCAPE,
            CameraMetadata.CONTROL_SCENE_MODE_NIGHT,
            CameraMetadata.CONTROL_SCENE_MODE_NIGHT_PORTRAIT,
            CameraMetadata.CONTROL_SCENE_MODE_PARTY,
            CameraMetadata.CONTROL_SCENE_MODE_PORTRAIT,
            CameraMetadata.CONTROL_SCENE_MODE_SNOW,
            CameraMetadata.CONTROL_SCENE_MODE_SPORTS,
            CameraMetadata.CONTROL_SCENE_MODE_STEADYPHOTO,
            CameraMetadata.CONTROL_SCENE_MODE_SUNSET,
            CameraMetadata.CONTROL_SCENE_MODE_THEATRE
    };

    public void dosomething(int checkedId)
    {
        AlertDialog.Builder ab = new AlertDialog.Builder(mActivity);

        ab.setMessage("Change to =" + checkedId);
        ab.setCancelable(false);

        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                dismiss();
            }
        });

        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                dismiss();
            }
        });

        AlertDialog alert = ab.create();
        alert.show();

    }
    private void selectColorEffect(String[] slist, int count, String title) {

       final Dialog dialog = new Dialog(mActivity);
        dialog.setTitle(title);
        dialog.setContentView(R.layout.radiobutton_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        List<String> stringList=new ArrayList<>();  // here is list
        for(int i=0;i<slist.length;i++) {
            stringList.add(slist[i]);
        }
        final RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
        rg.setBackgroundColor(Color.TRANSPARENT);

        for(int i=0;i<stringList.size();i++){
            RadioButton rb=new RadioButton(mActivity); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setId(i);
            rb.setText(stringList.get(i));
            rb.setTextColor(Color.WHITE);
            rg.addView(rb);
        }
        dialog.show();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int sele = checkedId;
                 switch (SeleViewId) {
                    case R.id.imageButton1:
                        updateSceneMode(sele);
                         break;
                       case R.id.imageButton4:
                        updateWhiteBalance(sele);
                           break;
                    case R.id.imageButton5:
                        updatePreviewColorEffect(sele);
                           break;
                }
            }
        });
    }

    private void updateSceneMode(int sele) {
        if( MyDebug.LOG )  Log.d(TAG, "update ScenceMode   ");
        scene_mode = SceneModeData[sele];
        String str  =  SceneMode[sele];
        Toast.makeText(mActivity, "Scene Mode " + str , Toast.LENGTH_SHORT).show();

        mRequestBuilder.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_USE_SCENE_MODE);
        mRequestBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, scene_mode);
        updatePreview();
    }
    private void updateWhiteBalance(int sele) {
        white_balance = WhiteBalanceData[sele];
        String str =  WhiteBalance[sele];
        Toast.makeText(mActivity, "White Balance " + str, Toast.LENGTH_SHORT).show();

        mRequestBuilder.set(CaptureRequest.CONTROL_AWB_MODE, white_balance);
        updatePreview();
    }

    public void updatePreviewColorEffect(int sele) {

        color_effect = ColorEffectData[sele];
        String str =  ColorEffect[sele];
        Toast.makeText(mActivity, "Color Effect " +str, Toast.LENGTH_SHORT).show();
        mRequestBuilder.set(CaptureRequest.CONTROL_EFFECT_MODE, color_effect);
        updatePreview();
    }

    private void updatePreview() {
        try {
            mCameraCaptureSession.setRepeatingRequest(mRequestBuilder.build(), mCaptureCallback, mHandler);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("updatePreview", "ExceptionExceptionException");
        }
    }

    private void seekISO() {
        Dialog at = new Dialog(mActivity);
        at.requestWindowFeature(Window.FEATURE_NO_TITLE);
        at.setContentView(R.layout.layoutseekiso);
        at.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        SeekBar seek = (SeekBar) at.findViewById(R.id.seekBarISO);
        final TextView txtView;
        txtView = (TextView) at.findViewById(R.id.textViewISOline);
        at.show();
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                txtView.setText("of : " + progress);
            }
            public void onStartTrackingTouch(SeekBar arg0) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void seekselect(Context mActivity) {
        Dialog at = new Dialog(mActivity);
        at.setTitle("Expore Time");
        at.setContentView(R.layout.layout_seek);
        at.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        SeekBar seek = (SeekBar) at.findViewById(R.id.seekBar1);
        final TextView txtView;
        txtView = (TextView) at.findViewById(R.id.textViewISOline);
        at.show();
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                txtView.setText("Expore Time : " + progress);
            }
          public void onStartTrackingTouch(SeekBar arg0) {
        }
        public void onStopTrackingTouch(SeekBar seekBar) {
         }
        });
    }



    private boolean setAEMode(CaptureRequest.Builder builder) {
        final long EXPOSURE_TIME_DEFAULT = 1000000000l / 30;
        int iso = 0;
        long exposure_time = EXPOSURE_TIME_DEFAULT;

        if (MyDebug.LOG) Log.d(TAG, "setAEMode 1");

        builder.set(CaptureRequest.CONTROL_AE_MODE, CameraMetadata.CONTROL_AE_MODE_OFF);
        builder.set(CaptureRequest.SENSOR_SENSITIVITY, iso);
        builder.set(CaptureRequest.SENSOR_EXPOSURE_TIME, exposure_time);
        builder.set(CaptureRequest.FLASH_MODE, CameraMetadata.FLASH_MODE_OFF);

        updatePreview();
        return true;

    }

  }