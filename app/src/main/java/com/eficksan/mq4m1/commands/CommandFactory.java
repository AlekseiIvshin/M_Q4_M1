package com.eficksan.mq4m1.commands;

/**
 * Created by Aleksei Ivshin
 * on 13.10.2016.
 */

public class CommandFactory {

    public Command create(String commandCode) {
        switch (commandCode) {
            default:
                throw new IllegalArgumentException("No command for " + commandCode);
        }
    }
}
