package com.pollyfat.squarega.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.entity.RecordItem;
import com.pollyfat.squarega.util.Util;

import java.util.List;

/**
 * 排行榜列表适配器
 * Created by polly on 2016/5/30.
 */

public class RankingListAdapter extends BaseAdapter {

    private List<RecordItem> records;
    private Context context;

    public RankingListAdapter(List<RecordItem> records, Context context) {
        this.records = records;
        this.context = context;
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ranking_item, null);
            View playerOne = convertView.findViewById(R.id.player_one);
            View playerTwo = convertView.findViewById(R.id.player_two);
            viewHolder = new ViewHolder();
            viewHolder.avatarPlayerOne = (ImageView) playerOne.findViewById(R.id.player_avatar);
            viewHolder.playerOneName = (TextView) playerOne.findViewById(R.id.player_name);
            viewHolder.ribbonOne = (ImageView) playerOne.findViewById(R.id.ribbon);

            viewHolder.avatarPlayerTwo = (ImageView) playerTwo.findViewById(R.id.player_avatar);
            viewHolder.playerTwoName = (TextView) playerTwo.findViewById(R.id.player_name);
            viewHolder.ribbonTwo = (ImageView) playerTwo.findViewById(R.id.ribbon);

            viewHolder.textNameOne = (TextView) convertView.findViewById(R.id.player_one_name);
            viewHolder.textNameTwo = (TextView) convertView.findViewById(R.id.player_two_name);
            viewHolder.level = (TextView) convertView.findViewById(R.id.level);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        RecordItem recordItem = records.get(position);
        viewHolder.avatarPlayerOne.setImageResource((Util.getDrawableResourceByName(recordItem.getPlayerOne().getAvatar(), context)));
        viewHolder.playerOneName.setText(recordItem.getPlayerOne().getName());

        viewHolder.avatarPlayerTwo.setImageResource((Util.getDrawableResourceByName(recordItem.getPlayerTwo().getAvatar(), context)));
        viewHolder.playerTwoName.setText(recordItem.getPlayerTwo().getName());

        viewHolder.textNameOne.setText(recordItem.getPlayerOne().getName());
        viewHolder.textNameTwo.setText(recordItem.getPlayerTwo().getName());
        switch (recordItem.getLevel()) {
            case 4:
                viewHolder.level.setText(context.getString(R.string.easy));
                break;
            case 5:
                viewHolder.level.setText(context.getString(R.string.normal));
                break;
            case 6:
                viewHolder.level.setText(context.getString(R.string.hard));
                break;
        }
        viewHolder.date.setText(recordItem.getDate());
        viewHolder.time.setText(recordItem.getTime());

        if (recordItem.isFirstWin()) {
            viewHolder.ribbonTwo.setImageResource(R.drawable.ribbon_lose);
        } else {
            viewHolder.ribbonOne.setImageResource(R.drawable.ribbon_lose);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView avatarPlayerOne;
        ImageView avatarPlayerTwo;
        ImageView ribbonOne;
        ImageView ribbonTwo;
        TextView playerOneName;
        TextView playerTwoName;
        TextView textNameOne;
        TextView textNameTwo;
        TextView level;
        TextView date;
        TextView time;
    }
}
