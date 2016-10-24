package com.eficksan.mq4m1.browser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.eficksan.mq4m1.commands.Command;
import com.eficksan.mq4m1.commands.CommandFactory;

/**
 * Created by Aleksei Ivshin
 * on 15.10.2016.
 */

public class OpenBrowserCommand extends Command {
    private final String mUrl;

    public OpenBrowserCommand(String commandContent) {
        this.mUrl = commandContent.replace(CommandFactory.OPEN_BROWSER, "");
    }

    @Override
    public void execute(Activity activityContext) {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse(mUrl));
        activityContext.startActivity(i);
    }

    @Override
    protected void handleCommandResult(Activity activityContext, int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected int getDefaultCommandRequestCode() {
        return CommandFactory.OPEN_BROWSER_REQUEST_CODE;
    }

}
