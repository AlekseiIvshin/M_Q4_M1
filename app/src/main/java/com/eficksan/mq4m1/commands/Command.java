package com.eficksan.mq4m1.commands;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by Aleksei Ivshin
 * on 13.10.2016.
 */

public abstract class Command {

    public abstract void execute(Activity activityContext);

    public void handleResult(Activity activityContext, int requestCode, int resultCode, Intent data) {
        if (requestCode == getCommandRequestCode()) {
            handleCommandResult(activityContext, requestCode, resultCode, data);
        }
    }

    protected abstract void handleCommandResult(Activity activityContext, int requestCode, int resultCode, Intent data);

    protected abstract int getCommandRequestCode();
}
