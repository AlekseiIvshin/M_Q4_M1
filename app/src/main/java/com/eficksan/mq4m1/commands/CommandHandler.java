package com.eficksan.mq4m1.commands;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Aleksei Ivshin
 * on 13.10.2016.
 */

public abstract class CommandHandler {
    public abstract void execute(Activity activityContext);

    public void handleResult(Context context, int requestCode, int resultCode, Intent data) {
        if (requestCode == getCommandRequestCode()) {
            handleCommandResult(context, requestCode, resultCode, data);
        }
    }

    protected abstract void handleCommandResult(Context context, int requestCode, int resultCode, Intent data);

    protected abstract int getCommandRequestCode();
}
