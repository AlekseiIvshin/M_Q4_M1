package com.eficksan.mq4m1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import nl.nl2312.rxcupboard.DatabaseChange;
import nl.nl2312.rxcupboard.RxCupboard;
import nl.nl2312.rxcupboard.RxDatabase;
import rx.Observable;

/**
 * Created by Aleksei Ivshin
 * on 13.10.2016.
 */

public class CommandRepository {

    private final DbHelper mDbHelper;

    public CommandRepository(Context context) {
        this.mDbHelper = new DbHelper(context);
    }

    public Observable<CommandEntity> add(CommandEntity entity) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        RxDatabase rxDatabase = RxCupboard.withDefault(database);
        return rxDatabase.putRx(entity);
    }

    public Observable<List<CommandEntity>> findAll() {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        RxDatabase rxDatabase = RxCupboard.withDefault(database);
        return rxDatabase.query(CommandEntity.class).toList();
    }

    public Observable<DatabaseChange<CommandEntity>> getChanges() {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        RxDatabase rxDatabase = RxCupboard.withDefault(database);
        return rxDatabase.changes(CommandEntity.class);
    }
}
