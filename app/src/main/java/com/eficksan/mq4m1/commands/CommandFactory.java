package com.eficksan.mq4m1.commands;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.eficksan.mq4m1.audio.AudoRecordingCommand;
import com.eficksan.mq4m1.browser.OpenBrowserCommand;
import com.eficksan.mq4m1.photo.TakePhotoCommand;
import com.eficksan.mq4m1.photo.ThirdLibTakePhotoCommand;
import com.eficksan.mq4m1.video.VideoCapturingCommand;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Aleksei Ivshin
 * on 13.10.2016.
 */

public class CommandFactory {

    public static final String TAKE_PHOTO_AND_THIRD_LIB_CROP = "cmd:take_photo_crop_third_lib/";
    public static final String TAKE_PHOTO_AND_CUSTOM_CROP = "cmd:take_photo_crop_custom/";
    public static final String TAKE_VIDEO = "cmd:take_video/";
    public static final String OPEN_BROWSER = "cmd:open_browser/";
    public static final String RECORD_AUDIO = "cmd:record_audio/";

    public static final int TAKE_PHOTO_AND_THIRD_LIB_CROP_REQUEST_CODE = 0;
    public static final int TAKE_PHOTO_AND_CUSTOM_CROP_REQUEST_CODE = 1;
    public static final int TAKE_VIDEO_REQUEST_CODE = 2;
    public static final int CROP_PHOTO_REQUEST_CODE = 3;
    public static final int OPEN_BROWSER_REQUEST_CODE = 4;
    public static final int RECORD_AUDIO_REQUEST_CODE = 5;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            TAKE_PHOTO_AND_THIRD_LIB_CROP,
            TAKE_PHOTO_AND_CUSTOM_CROP,
            TAKE_VIDEO,
            OPEN_BROWSER,
            RECORD_AUDIO})
    public @interface CommandType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            TAKE_PHOTO_AND_THIRD_LIB_CROP_REQUEST_CODE,
            TAKE_PHOTO_AND_CUSTOM_CROP_REQUEST_CODE,
            TAKE_VIDEO_REQUEST_CODE,
            OPEN_BROWSER_REQUEST_CODE,
            RECORD_AUDIO_REQUEST_CODE})
    public @interface CommandRequestCode {
    }

    public static Command create(String commandContent) {
        switch (commandContent) {
            case TAKE_PHOTO_AND_THIRD_LIB_CROP:
                return new ThirdLibTakePhotoCommand();
            case TAKE_PHOTO_AND_CUSTOM_CROP:
                return new TakePhotoCommand();
            case TAKE_VIDEO:
                return new VideoCapturingCommand();
            case RECORD_AUDIO:
                return new AudoRecordingCommand();
            default:
                if (isOpenBrowserCommand(commandContent)) {
                    return new OpenBrowserCommand(commandContent);
                }
                throw new IllegalArgumentException("No command for " + commandContent);
        }
    }

    public static Command createResultHandler(int requestCode) {
        switch (requestCode) {
            case TAKE_PHOTO_AND_THIRD_LIB_CROP_REQUEST_CODE:
                return new ThirdLibTakePhotoCommand();
            case TAKE_PHOTO_AND_CUSTOM_CROP_REQUEST_CODE:
                return new TakePhotoCommand();
            case TAKE_VIDEO_REQUEST_CODE:
                return new VideoCapturingCommand();
            case RECORD_AUDIO_REQUEST_CODE:
                return new AudoRecordingCommand();
            case OPEN_BROWSER_REQUEST_CODE:
            default:
                return null;
        }
    }

    private static boolean isOpenBrowserCommand(String command) {
        return command.indexOf(OPEN_BROWSER) == 0;
    }
}
