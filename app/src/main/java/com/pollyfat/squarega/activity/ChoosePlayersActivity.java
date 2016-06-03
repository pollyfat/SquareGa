package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pollyfat.squarega.R;
import com.pollyfat.squarega.adapter.PlayerTableAdapter;
import com.pollyfat.squarega.entity.Player;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_choose_players)
public class ChoosePlayersActivity extends Activity {

    public static final String spFileName = "PlayerList";

    List<Player> players;
    Player playerOne, playerTwo, playerCom = new Player("电脑君", "avatar_cp");

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
    @ViewById(R.id.add_player)
    ImageView operateBtn;

    PlayerTableAdapter selectAdapter;

    PopupWindow addPlay;
    View popupView;
    EditText name;
    GridView createGrid;
    PlayerTableAdapter createAdapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int selectPosition1 = -1, selectPosition2 = -1, createPosition = -1;
    boolean isFirstPlayer = true;
    int popupState = 0;

    private View popView;

    @ViewById(R.id.pop_viewstub)
    ViewStub popViewStub;

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
                    selectPosition1 = position;
                    selectAdapter.selectItem(PlayerTableAdapter.PlayerEnum.ONE, position);
                } else {
                    playerTwo = players.get(position);
                    selectPosition2 = position;
                    selectAdapter.selectItem(PlayerTableAdapter.PlayerEnum.TWO, position);
                }
                if (playerOne != null & playerTwo != null) {
                    popupState = 2;
                    operateBtn.setImageResource(R.drawable.play_btn_anim);
                    AnimationDrawable anim = (AnimationDrawable) operateBtn.getDrawable();
                    anim.start();
                }
            }
        });
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
        if (players == null) {
            players = new ArrayList<>();
        } else if (players.get(0).getName().equals(playerCom.getName())) {
            players.remove(0);
        }
    }

    @Click(R.id.add_player)
    public void addPlayer() {
        switch (popupState) {
            case 0:
                //未弹出添加框
                if (popView == null) {
                    //初始化添加玩家时的弹出框
                    initPopupView();
                    operateBtn.setImageResource(R.drawable.ok);
                } else {
                    popView.setVisibility(View.VISIBLE);
                    operateBtn.setImageResource(R.drawable.ok);
                }
                popupState = 1;
                break;
            case 1:
                //已弹出添加框
                if (createPosition == -1) {
                    Toast.makeText(ChoosePlayersActivity.this, "请选择一个头像~", Toast.LENGTH_SHORT).show();
                } else if (("").equals(name.getText().toString())) {
                    Toast.makeText(ChoosePlayersActivity.this, "你忘了输名字啦~", Toast.LENGTH_SHORT).show();
                } else {
                    popView.setVisibility(View.GONE);
                    operateBtn.setImageResource(R.drawable.add);
                    Player newP = new Player(name.getText().toString(), "avatar" + createPosition);
                    players.add(newP);
                    //将选择和输入重置
                    name.setText("");
                    createGrid.getChildAt(createPosition).findViewById(R.id.avatar_medal).setVisibility(View.INVISIBLE);

                    selectAdapter.notifyDataSetChanged();
                    createAdapter.notifyDataSetChanged();

                    //将创建的玩家加入列表中
                    editor = sharedPreferences.edit();
                    editor.putString(spFileName, new Gson().toJson(players));
                    editor.apply();
                }
                popupState = 0;
                break;
            case 2:
                //已选择完毕，准备开始游戏
                Intent intent = new Intent(this, ChooseLevelActivity_.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("playerOne", playerOne);
                bundle.putSerializable("playerTwo", playerTwo);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Click(R.id.btn_player_one)
    public void selectPlayerOne() {
        selectAdapter.setCurPlayer(PlayerTableAdapter.PlayerEnum.ONE);

        isFirstPlayer = true;
        findViewById(R.id.btn_player_one).setBackgroundResource(R.drawable.player_one);
        findViewById(R.id.btn_player_one).setClickable(false);
        findViewById(R.id.btn_player_two).setBackgroundResource(R.drawable.player_two_shadow);
        findViewById(R.id.btn_player_two).setClickable(true);
        if (playerTwo != null) {
            setMedalInvisible();
            players.remove(playerTwo);
            selectAdapter.notifyDataSetChanged();
        }
        if (players.size() != 0 && players.get(0).equals(playerCom)) {
            players.remove(playerCom);
            selectAdapter.notifyDataSetChanged();
        }
        if (playerOne != null) {
            selectPosition1 = selectPosition1 >= players.size() ? players.size() : selectPosition1;
            players.add(selectPosition1, playerOne);
            selectAdapter.selectItem(PlayerTableAdapter.PlayerEnum.ONE, selectPosition1);

        }
        listRoot.setBackgroundColor(ContextCompat.getColor(this, R.color.player_one));
    }

    @Click(R.id.btn_player_two)
    public void selectPlayerTwo() {
        selectAdapter.setCurPlayer(PlayerTableAdapter.PlayerEnum.TWO);

        findViewById(R.id.btn_player_one).setBackgroundResource(R.drawable.player_one_shadow);
        findViewById(R.id.btn_player_one).setClickable(true);
        findViewById(R.id.btn_player_two).setBackgroundResource(R.drawable.player_two);
        findViewById(R.id.btn_player_two).setClickable(false);
        if (playerOne != null) {
            setMedalInvisible();
            players.remove(playerOne);
            selectAdapter.notifyDataSetChanged();
        }
        players.add(0, playerCom);
        selectAdapter.notifyDataSetChanged();
        if (playerTwo != null) {
            if (selectPosition2 != 0) {
                selectPosition2 = selectPosition2 >= players.size() ? players.size() : selectPosition2;
                players.add(selectPosition2, playerTwo);
            }
            selectAdapter.selectItem(PlayerTableAdapter.PlayerEnum.TWO, selectPosition2);
        }
        isFirstPlayer = false;
        listRoot.setBackgroundColor(ContextCompat.getColor(this, R.color.player_two));
    }

    /**
     * 返回按钮触摸监听
     */
    @Override
    public void onBackPressed() {
        if (popView != null && popView.getVisibility() == View.VISIBLE) {
            popView.setVisibility(View.GONE);
            operateBtn.setImageResource(R.drawable.add);
        } else {
            super.onBackPressed();
        }
    }

    void initPopupView() {
        popView = popViewStub.inflate();
        name = (EditText) findViewById(R.id.edit_name);
        createGrid = (GridView) findViewById(R.id.show_avatar);
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

    void setMedalInvisible() {
        for (int i = 0; i < selectGrid.getCount(); i++) {
            selectGrid.getChildAt(i).findViewById(R.id.avatar_medal).setVisibility(View.INVISIBLE);
        }
    }
}
