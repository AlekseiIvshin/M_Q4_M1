package com.eficksan.mq4m1.commands;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import com.eficksan.mq4m1.audio.AudoRecordingCommand;
import com.eficksan.mq4m1.browser.OpenBrowserCommand;
import com.eficksan.mq4m1.video.VideoCapturingCommand;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Aleksei Ivshin
 * on 13.10.2016.
 */

public class CommandFactory {

    public static final String TAKE_PHOTO = "cmd:take_photo/";
    public static final String TAKE_PHOTO_CUSTOM = "cmd:take_photo_custom/";
    public static final String TAKE_VIDEO = "cmd:take_video/";
    public static final String OPEN_BROWSER = "cmd:open_browser/";
    public static final String RECORD_AUDIO = "cmd:record_audio/";

    public static final int TAKE_PHOTO_REQUEST_CODE = 0;
    public static final int TAKE_PHOTO_CUSTOM_REQUEST_CODE = 1;
    public static final int TAKE_VIDEO_REQUEST_CODE = 2;
    public static final int OPEN_BROWSER_REQUEST_CODE = 4;
    public static final int RECORD_AUDIO_REQUEST_CODE = 5;

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            TAKE_PHOTO,
            TAKE_PHOTO_CUSTOM,
            TAKE_VIDEO,
            OPEN_BROWSER,
            RECORD_AUDIO})
    public @interface CommandType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            TAKE_PHOTO_REQUEST_CODE,
            TAKE_PHOTO_CUSTOM_REQUEST_CODE,
            TAKE_VIDEO_REQUEST_CODE,
            OPEN_BROWSER_REQUEST_CODE,
            RECORD_AUDIO_REQUEST_CODE})
    public @interface CommandRequestCode {
    }

    public static Command create(String commandContent) {
        switch (commandContent) {
            case TAKE_PHOTO:
            case TAKE_PHOTO_CUSTOM:
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
            case TAKE_PHOTO_REQUEST_CODE:
            case TAKE_PHOTO_CUSTOM_REQUEST_CODE:
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
