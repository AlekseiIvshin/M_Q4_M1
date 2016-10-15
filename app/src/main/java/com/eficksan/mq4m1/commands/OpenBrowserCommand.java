package com.eficksan.mq4m1.commands;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Aleksei Ivshin
 * on 15.10.2016.
 */

public class OpenBrowserCommand implements Command {
    private final String mUrl;

    public OpenBrowserCommand(String commandContent) {
        this.mUrl = commandContent.replace(CommandFactory.OPEN_BROWSER, "");
    }

    @Override
    public void execute(Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW,
                Uri.parse(mUrl));
        context.startActivity(i);
    }

}
