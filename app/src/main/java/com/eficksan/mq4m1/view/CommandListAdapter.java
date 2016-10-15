package com.eficksan.mq4m1.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eficksan.mq4m1.R;
import com.eficksan.mq4m1.data.CommandEntity;

import java.util.List;

/**
 * Created by Aleksei Ivshin
 * on 15.10.2016.
 */

public class CommandListAdapter extends RecyclerView.Adapter<CommandViewHolder> {

    private List<CommandEntity> commandsList;

    @Override
    public CommandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_command, parent, false);
        return new CommandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommandViewHolder holder, int position) {
        commandsList.get(position);
    }

    @Override
    public int getItemCount() {
        if (commandsList != null) {
            return commandsList.size();
        }
        return 0;
    }

    public void updateCommands(List<CommandEntity> commands) {
        this.commandsList = commands;
        notifyDataSetChanged();
    }
}
