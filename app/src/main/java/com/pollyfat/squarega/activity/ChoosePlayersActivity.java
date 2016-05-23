package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pollyfat.squarega.R;
import com.pollyfat.squarega.adapter.PlayerTableAdapter;
import com.pollyfat.squarega.entity.Player;
import com.pollyfat.squarega.util.Util;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@EActivity(R.layout.activity_choose_players)
public class ChoosePlayersActivity extends Activity{

    public static final String spFileName = "PlayerList";

    List<Player> players;
    Player playerOne, playerTwo,playerCom = new Player("电脑君","avatar_cp");

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
    ImageButton doneBtn;
    PlayerTableAdapter createAdapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int selectPosition = -1, createPosition = -1;
    boolean isFirstPlayer = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(spFileName, Context.MODE_PRIVATE);
    }

    @AfterViews
    public void initViews() {
        initPlayersList();
        btnRoot.removeAllViews();
        btnRoot.addView(pTwo);
        btnRoot.addView(pOne);
        //初始化展示玩家头像的gridView
        selectAdapter = new PlayerTableAdapter(this, players, PlayerTableAdapter.SELECT_LIST);
        selectGrid.setAdapter(selectAdapter);
        selectGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (isFirstPlayer) {
                    playerOne = players.get(position);
                }else {
                    playerTwo = players.get(position);
                }
            }
        });
        //初始化添加玩家时的弹出框
        initPopupView();
        addPlay = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        addPlay.setOutsideTouchable(true);
        addPlay.setBackgroundDrawable(new ColorDrawable(0x55000000));
        addPlay.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                findViewById(R.id.add_player).setVisibility(View.VISIBLE);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            addPlay.setElevation(2);
        }
    }

    private void initPlayersList() {
        Gson gson = new Gson();
        String s = sharedPreferences.getString(spFileName, "");
        Type type = new TypeToken<List<Player>>() {
        }.getType();
        players = gson.fromJson(s, type);
    }

    @Click(R.id.add_player)
    public void addPlayer() {
        addPlay.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        findViewById(R.id.add_player).setVisibility(View.INVISIBLE);
    }

    @Click(R.id.btn_player_one)
    public void selectPlayerOne() {
        btnRoot.removeAllViews();
        btnRoot.addView(pTwo);
        btnRoot.addView(pOne);
        if (players.get(0).equals(playerCom)) {
            players.remove(playerCom);
        }
        isFirstPlayer = true;
        selectAdapter.notifyDataSetChanged();
        listRoot.setBackgroundColor(ContextCompat.getColor(this, R.color.player_one));
    }

    @Click(R.id.btn_player_two)
    public void selectPlayerTwo() {
        btnRoot.removeAllViews();
        btnRoot.addView(pOne);
        btnRoot.addView(pTwo);
        players.add(0,playerCom);
        isFirstPlayer = false;
        selectAdapter.notifyDataSetChanged();
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
            doneBtn = (ImageButton) popupView.findViewById(R.id.commit_add_player);
            doneBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (createPosition == -1) {
                        Toast.makeText(ChoosePlayersActivity.this, "请选择一个头像~", Toast.LENGTH_SHORT).show();
                    } else if (("").equals(name.getText().toString())) {
                        Toast.makeText(ChoosePlayersActivity.this, "你忘了输名字啦~", Toast.LENGTH_SHORT).show();
                    } else {
                        addPlay.dismiss();
                        findViewById(R.id.add_player).setVisibility(View.VISIBLE);
                        Player newP = new Player(name.getText().toString(), "avatar" + createPosition);
                        players.add(newP);
                        selectAdapter.notifyDataSetChanged();
                        editor = sharedPreferences.edit();
                        editor.putString(spFileName, new Gson().toJson(players));
                        editor.apply();
                    }
                }
            });
        }
    }
}
