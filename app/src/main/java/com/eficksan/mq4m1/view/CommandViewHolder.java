package com.eficksan.mq4m1.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.eficksan.mq4m1.R;
import com.eficksan.mq4m1.data.CommandEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aleksei Ivshin
 * on 13.10.2016.
 */

public class CommandViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.command_name)
    TextView mCommandName;

    @BindView(R.id.command_time)
    TextView mCommandTime;

    public CommandViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setContent(CommandEntity commandEntity) {
        mCommandName.setText(commandEntity.content);
        mCommandTime.setText(commandEntity.time);
    }
}
