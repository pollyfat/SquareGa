package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pollyfat.squarega.R;
import com.pollyfat.squarega.adapter.PlayerTableAdapter;
import com.pollyfat.squarega.entity.Player;
import com.pollyfat.squarega.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_choose_players)
public class ChoosePlayersActivity extends Activity{

    public static final String spFileName = "PlayerList";

    List<Player> players;
    Player playerOne, playerTwo;

    @ViewById(R.id.player_table)
    GridView selectGrid;
    @ViewById(R.id.btn_root)
    RelativeLayout btnRoot;
    @ViewById(R.id.btn_player_one)
    Button pOne;
    @ViewById(R.id.btn_player_two)
    Button pTwo;
    @ViewById(R.id.choose_player_list)
    RelativeLayout listRoot;
    PlayerTableAdapter selectAdapter;

    PopupWindow addPlay;
    View popupView;
    EditText name;
    GridView createGrid;
    PlayerTableAdapter createAdapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int selectPosition,createPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
        initPlayersList();
    }

    @AfterViews
    public void initViews() {
        btnRoot.removeAllViews();
        btnRoot.addView(pTwo);
        btnRoot.addView(pOne);
        //初始化展示玩家头像的gridView
        selectAdapter = new PlayerTableAdapter(this, players, PlayerTableAdapter.SELECT_LIST);
        selectGrid.setAdapter(selectAdapter);
        //初始化添加玩家时的弹出框
        initPopupView();
        addPlay = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        addPlay.setFocusable(false);
        addPlay.setTouchable(true);
        addPlay.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addPlay.setElevation(2);
        }
    }

    private void initPlayersList() {
        Gson gson = new Gson();
        players = (List<Player>) Util.getListObject(sharedPreferences, spFileName, Player.class);
    }

    @Click(R.id.add_player)
    public void addPlayer() {
        Toast.makeText(this,"click",Toast.LENGTH_SHORT).show();
        if (addPlay.isShowing()) {
            addPlay.dismiss();
            ((ImageView)findViewById(R.id.add_player)).setImageResource(R.drawable.add);
            Player newP = new Player("avatar" + createPosition);
            newP.setName(name.getText().toString());
            players.add(newP);
            selectAdapter.notifyDataSetChanged();
            editor = sharedPreferences.edit();
            editor.putString(spFileName, new Gson().toJson(players));
            editor.apply();
        }else {
            addPlay.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            ((ImageView)findViewById(R.id.add_player)).setImageResource(R.drawable.gold);
        }
    }

    @Click(R.id.btn_player_one)
    public void selectPlayerOne() {
        btnRoot.removeAllViews();
        btnRoot.addView(pTwo);
        btnRoot.addView(pOne);
        listRoot.setBackgroundColor(ContextCompat.getColor(this, R.color.player_one));
    }

    @Click(R.id.btn_player_two)
    public void selectPlayerTwo() {
        btnRoot.removeAllViews();
        btnRoot.addView(pOne);
        btnRoot.addView(pTwo);
        listRoot.setBackgroundColor(ContextCompat.getColor(this, R.color.player_two));
    }

    void initPopupView() {
        if (popupView == null) {
            popupView = LayoutInflater.from(this).inflate(R.layout.popup_select_player, null);
            name = (EditText) popupView.findViewById(R.id.edit_name);
            createGrid = (GridView) popupView.findViewById(R.id.show_avatar);
            List<Player> playerList = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                playerList.add(new Player("avatar" + i));
            }
            createAdapter = new PlayerTableAdapter(this, playerList, PlayerTableAdapter.CREATE_LIST);
            createGrid.setAdapter(createAdapter);
            createGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for (int i = 0; i < createAdapter.getCount(); i++) {
                        View playerItem = createGrid.getChildAt(i);
                        playerItem.findViewById(R.id.avatar_medal).setVisibility(View.INVISIBLE);
                    }
                    View playerItem = createGrid.getChildAt(position);
                    playerItem.findViewById(R.id.avatar_medal).setVisibility(View.VISIBLE);
                    createPosition = position;
                }
            });
        }
    }

}
