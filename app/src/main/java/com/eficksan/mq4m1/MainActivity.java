package com.eficksan.mq4m1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.eficksan.mq4m1.commands.Command;
import com.eficksan.mq4m1.commands.CommandFactory;
import com.eficksan.mq4m1.commands.CommandResultHandler;
import com.eficksan.mq4m1.data.CommandEntity;
import com.eficksan.mq4m1.data.CommandRepository;
import com.eficksan.mq4m1.view.CommandListAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.joda.time.DateTime;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nl.nl2312.rxcupboard.DatabaseChange;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    Unbinder mUnbinder;

    @BindView(R.id.list_command)
    RecyclerView mCommands;

    CommandRepository mCommandRepository;

    CommandListAdapter mCommandListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCommandRepository = new CommandRepository(getApplicationContext());
        mUnbinder = ButterKnife.bind(this);

        mCommandListAdapter = new CommandListAdapter();

        mCommands.setLayoutManager(new LinearLayoutManager(this));
        mCommands.setAdapter(mCommandListAdapter);
    }

    @Override
    protected void onDestroy() {
        mCommandRepository = null;
        super.onDestroy();
        mUnbinder.unbind();
    }

    @OnClick(R.id.next_command)
    public void handleNextCommandClick() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!handleCommandReceiveResult(requestCode, resultCode, data)) {
            CommandResultHandler resultHandler = CommandFactory.createResultHandler(requestCode);
            if (resultHandler != null) {
                resultHandler.handleResult(this, requestCode, resultCode, data);
            }
        }

    }

    private boolean handleCommandReceiveResult(int requestCode, int resultCode, Intent data) {
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            final String scanContent = scanResult.getContents();
            Log.v(TAG, String.format("Command code: '%s'", scanContent));
            saveCommand(scanContent);
            runCommand(scanContent);
            return true;
        }
        return false;
    }

    private void runCommand(String scanContent) {
        Command command = CommandFactory.create(scanContent);
        command.execute(this);
    }

    private void saveCommand(final String scanContent) {
        CommandEntity commandEntity = new CommandEntity();
        commandEntity.content = scanContent;
        commandEntity.time = DateTime.now().toString();

        mCommandRepository.add(commandEntity).subscribe(new Action1<CommandEntity>() {
            @Override
            public void call(CommandEntity entity) {
                Toast.makeText(MainActivity.this, getString(R.string.command_saved, scanContent), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void loadCommandsHistory() {
        mCommandRepository.findAll().subscribe(new Action1<List<CommandEntity>>() {
            @Override
            public void call(List<CommandEntity> commandEntities) {
                mCommandListAdapter.updateCommands(commandEntities);
            }
        });
    }

    private void subscribeOnCommandsChanges() {
        mCommandRepository.getChanges().subscribe(new Action1<DatabaseChange<CommandEntity>>() {
            @Override
            public void call(DatabaseChange<CommandEntity> commandEntityDatabaseChange) {
                loadCommandsHistory();
            }
        });
    }

}
