package com.eficksan.mq4m1.video.v16;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.eficksan.mq4m1.R;
import com.eficksan.mq4m1.video.BaseVideoFragment;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class VideoFragmentV16 extends BaseVideoFragment implements SurfaceHolder.Callback {

    @BindView(R.id.record_video)
    View mRecordVideo;

    @BindView(R.id.svVideoPreview)
    SurfaceView mVideoPreview;

    private Unbinder mUnbinder;
    private SurfaceHolder holder;

    public VideoFragmentV16() {
        // Required empty public constructor
    }

    public static VideoFragmentV16 newInstance(String outputDirectory) {
        Bundle args = new Bundle();
        args.putString(EXTRA_RESULT_DIRECTORY, outputDirectory);
        VideoFragmentV16 fragment = new VideoFragmentV16();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUnbinder = ButterKnife.bind(this, view);

        holder = mVideoPreview.getHolder();
        holder.addCallback(this);

        setUpMediaRecorder();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @OnClick(R.id.record_video)
    public void handleVideoRecording() {
        if (mIsRecording) {
            stopRecording();
        } else {
            startRecording();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopRecording();
    }

    private void prepareRecorder() {
        mMediaRecorder.setPreviewDisplay(holder.getSurface());
        try {
            mMediaRecorder.prepare();
        } catch (IOException e) {
            mVideoRecordListener.onRecordingFailed(e);
        }
    }

    private void startRecording() {
        onRecordingStarted(mRecordVideo);
        mMediaRecorder.start();
    }

    private void stopRecording() {
        onRecordingFinished(mRecordVideo);
        releaseMediaRouter();
        deliverResult();
    }
}
