package com.eficksan.mq4m1.audio;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.eficksan.mq4m1.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AudioRecordingActivity extends AppCompatActivity {

    private static final String TAG = AudioRecordingActivity.class.getSimpleName();
    private static final String EXTRA_RESULT_DATA
            = AudioRecordingActivity.class.getPackage() + ".EXTRA_RESULT_DATA";
    private static final int RECORD_AUDIO_PERMISSION = 202;

    @BindView(R.id.record_audio)
    View mRecordAudio;

    private boolean mIsRecordingNow = false;
    private MediaRecorder mRecorder;
    private String mOutputFilePath;
    Unbinder unbinder;

    public static Intent buildLauncherIntent(Context context) {
        return new Intent(context, AudioRecordingActivity.class);
    }

    public static String takeResult(Intent result) {
        return result.getStringExtra(EXTRA_RESULT_DATA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recording);
        Log.v(TAG, "onCreate");
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick(R.id.record_audio)
    public void handleRecordClick() {
        Log.v(TAG, "Click on record audio, is recording now " + mIsRecordingNow);
        if (mIsRecordingNow) {
            stopRecord();
        } else {
            startRecord();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RECORD_AUDIO_PERMISSION) {
            startRecord();
        }
    }

    private void startRecord() {
        Log.v(TAG, "startRecord");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permissions needed");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RECORD_AUDIO_PERMISSION);
            }
            return;
        }
        mIsRecordingNow = true;
        mRecordAudio.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_recording));

        mOutputFilePath = outputFilePath();

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mRecorder.setOutputFile(mOutputFilePath);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        mRecorder.start();
    }

    private void stopRecord() {
        Log.v(TAG, "stopRecord");
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        finishAndSetResult();
    }

    private String outputFilePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/mq4m1_" +
                System.currentTimeMillis() +
                ".3gp";
    }

    private void finishAndSetResult() {
        Intent result = new Intent();
        result.putExtra(EXTRA_RESULT_DATA, mOutputFilePath);
        setResult(RESULT_OK, result);
        finish();
    }
}
