package com.eficksan.mq4m1.audio;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.eficksan.mq4m1.R;
import com.eficksan.mq4m1.commands.Command;
import com.eficksan.mq4m1.commands.CommandFactory;

/**
 * Created by Aleksei Ivshin
 * on 15.10.2016.
 */

public class AudoRecordingCommand extends Command {

    @Override
    public void execute(Activity activityContext) {
        activityContext.startActivityForResult(
                AudioRecordingActivity.buildLauncherIntent(activityContext), getDefaultCommandRequestCode());
    }

    @Override
    protected void handleCommandResult(Activity activityContext, int requestCode, int resultCode, Intent data) {
        if (data != null) {
            String resultFilePath = AudioRecordingActivity.takeResult(data);
            Toast.makeText(activityContext,
                    activityContext.getString(R.string.audio_recording_result, resultFilePath),
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    protected int getDefaultCommandRequestCode() {
        return CommandFactory.RECORD_AUDIO_REQUEST_CODE;
    }
}
