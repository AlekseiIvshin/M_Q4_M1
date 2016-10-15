package com.eficksan.mq4m1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import nl.qbusict.cupboard.CupboardFactory;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by Aleksei Ivshin
 * on 13.10.2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "commands.db";
    private static final int DATABASE_VERSION = 1;

    static {
        cupboard().register(CommandEntity.class);
    }

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard().withDatabase(db).upgradeTables();
    }
}
