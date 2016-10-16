package com.eficksan.mq4m1.video;

/**
 * Created by Aleksei Ivshin
 * on 16.10.2016.
 */

public interface VideoRecordingResultListener {

    void onVideoRecorded(String videoUrl);

    void onRecordingFailed(Throwable exception);

    void onRecordingFailed(String message);
}
