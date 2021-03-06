package com.pollyfat.squarega.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.entity.Player;
import com.pollyfat.squarega.util.Util;

import java.util.List;

/**
 * Created by polly on 2016/5/19.
 * 玩家列表和添加玩家的GridView适配器
 */
public class PlayerTableAdapter extends BaseAdapter {
    public static final int SELECT_LIST = 0, CREATE_LIST = 1;
    Context context;
    List<Player> players;
    int tag;

    public PlayerTableAdapter(Context context, List<Player> players, int tag) {
        this.context = context;
        this.players = players;
        this.tag = tag;
    }


    public enum PlayerEnum {
        ONE,
        TWO
    }


    private PlayerEnum curPlayer = PlayerEnum.ONE;

    private int selectedOne = -1, selectedTwo = -1;

    public void setCurPlayer(PlayerEnum player) {
        curPlayer = player;
    }

    public void selectItem(PlayerEnum player, int pos) {
        if (player == PlayerEnum.ONE) {
            this.selectedOne = pos;
        } else {
            this.selectedTwo = pos;
        }
        this.curPlayer = player;
        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.player_item, null);
            viewHolder = new ViewHolder();
            viewHolder.avatar = (ImageView) convertView.findViewById(R.id.player_avatar);
            viewHolder.name = (TextView) convertView.findViewById(R.id.player_name);
            viewHolder.nameSpace = (RelativeLayout) convertView.findViewById(R.id.name_space);
            viewHolder.metal = (ImageView) convertView.findViewById(R.id.avatar_medal);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (tag == SELECT_LIST) {
            //选择已存在的玩家
            viewHolder.name.setText(players.get(position).getName());
            viewHolder.avatar.setImageResource(Util.getDrawableResourceByName(players.get(position).getAvatar(), context));

            if (curPlayer == PlayerEnum.ONE) {
                if (position == selectedOne) {
                    viewHolder.metal.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.metal.setVisibility(View.GONE);
                }
            } else {
                if (position == selectedTwo) {
                    viewHolder.metal.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.metal.setVisibility(View.GONE);
                }
            }
        } else {
            //创建玩家
            viewHolder.nameSpace.setVisibility(View.GONE);
            viewHolder.avatar.setImageResource(Util.getDrawableResourceByName(players.get(position).getAvatar(), context));
        }
        return convertView;
    }

    class ViewHolder {
        ImageView avatar;
        TextView name;
        RelativeLayout nameSpace;
        ImageView metal;
    }
}
