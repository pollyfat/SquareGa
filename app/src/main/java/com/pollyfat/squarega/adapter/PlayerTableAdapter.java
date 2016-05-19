package com.pollyfat.squarega.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.entity.Player;

import java.util.List;

/**
 * Created by android on 2016/5/19.
 */
public class PlayerTableAdapter extends BaseAdapter {

    Context context;
    List<Player> players ;

    public PlayerTableAdapter(Context context, List<Player> players) {
        this.context = context;
        this.players = players;
    }

    @Override
    public int getCount() {
        return players.size();
    }

    @Override
    public Object getItem(int position) {
        return players.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_choose_players, null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.player_avatar);
            viewHolder.name = (TextView) convertView.findViewById(R.id.player_name);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder{
        ImageView avatar;
        TextView name;
    }
}
