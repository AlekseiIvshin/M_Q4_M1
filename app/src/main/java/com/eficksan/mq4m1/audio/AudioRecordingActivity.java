package com.eficksan.mq4m1.audio;

import android.content.Context;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.eficksan.mq4m1.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

public class AudioRecordingActivity extends AppCompatActivity {

    private static final String TAG = AudioRecordingActivity.class.getSimpleName();
    private static final String EXTRA_RESULT_DATA
            = AudioRecordingActivity.class.getPackage() + ".EXTRA_RESULT_DATA";

    @BindView(R.id.record_audio)
    View mRecordAudio;

    private boolean mIsRecordingNow = false;
    private MediaRecorder mRecorder;
    private String mOutputFilePath;

    public static Intent buildLauncher(Context context) {
        return new Intent(context, AudioRecordingActivity.class);
    }

    public static String takeResult(Intent result) {
        return result.getStringExtra(EXTRA_RESULT_DATA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_recording);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }

    @OnClick(R.id.record_audio)
    public void handleRecordClick() {
        if (mIsRecordingNow) {
            stopRecord();
        } else {
            startRecord();
        }
    }

    private void startRecord() {
        mIsRecordingNow = true;
        mRecordAudio.setBackground(ContextCompat.getDrawable(this, R.drawable.mic_recording_background));

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
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        finishAndSetResult();
    }

    private String outputFilePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/mq4m1_" +
                System.currentTimeMillis() +
                ".3dp";
    }

    private void finishAndSetResult() {
        Intent result = new Intent();
        result.putExtra(EXTRA_RESULT_DATA, mOutputFilePath);
        setResult(RESULT_OK, result);
        finish();
    }
}
