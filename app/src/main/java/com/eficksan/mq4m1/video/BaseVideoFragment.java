package com.eficksan.mq4m1.video;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.eficksan.mq4m1.R;

/**
 * Created by Aleksei Ivshin
 * on 18.10.2016.
 */

public class BaseVideoFragment extends Fragment {
    private static final String TAG = BaseVideoFragment.class.getSimpleName();

    protected String mVideoOutput;
    protected MediaRecorder mMediaRecorder;

    protected VideoRecordingResultListener mVideoRecordListener;
    protected boolean mIsRecording = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof VideoRecordingResultListener) {
            mVideoRecordListener = (VideoRecordingResultListener) context;
        }
    }

    @Override
    public void onDetach() {
        mVideoRecordListener = null;
        super.onDetach();
    }

    protected void setUpMediaRecorder() {
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);

        mVideoOutput = getArguments().getString(MediaStore.EXTRA_OUTPUT);

        mMediaRecorder.setOutputFile(mVideoOutput);
        mMediaRecorder.setVideoEncodingBitRate(10000000);
        mMediaRecorder.setVideoFrameRate(30);


        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
    }

    protected void releaseMediaRouter() {
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
    }

    protected void deliverResult() {
        Log.d(TAG, "Video saved: " + mVideoOutput);
        if (mVideoRecordListener != null) {
            mVideoRecordListener.onVideoRecorded(mVideoOutput);
        }
        mVideoOutput = null;
    }

    protected void onRecordingStarted(View recordView) {
        mIsRecording = true;
        recordView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_recording));
    }

    protected void onRecordingFinished(View recordView) {
        mIsRecording = false;
        recordView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_start_recording));
    }

}
