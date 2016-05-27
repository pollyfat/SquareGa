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
    PlayerTableAdapter selectAdapter;
    @ViewById(R.id.add_player)
    ImageView operateBtn;

    PopupWindow addPlay;
    View popupView;
    EditText name;
    GridView createGrid;
    PlayerTableAdapter createAdapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int selectPosition1 = -1, selectPosition2 = -1, createPosition = -1;
    boolean isFirstPlayer = true;

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
                    //点击其他玩家时将刚才选择的玩家的标识设为隐藏
                    if (selectPosition1 != -1)
                        createGrid.getChildAt(selectPosition1).findViewById(R.id.avatar_medal).setVisibility(View.INVISIBLE);
                    View playerItem = selectGrid.getChildAt(position);
                    playerItem.findViewById(R.id.avatar_medal).setVisibility(View.VISIBLE);
                    selectAdapter.notifyDataSetChanged();
                    selectPosition1 = position;
                } else {
                    //TODO 徽章显示问题
                    playerTwo = players.get(position);
                    //点击其他玩家时将刚才选择的玩家的标识设为隐藏
                    if (selectPosition2 != -1)
                        createGrid.getChildAt(selectPosition2).findViewById(R.id.avatar_medal).setVisibility(View.INVISIBLE);
                    View playerItem = selectGrid.getChildAt(position);
                    playerItem.findViewById(R.id.avatar_medal).setVisibility(View.VISIBLE);
                    selectAdapter.notifyDataSetChanged();
                    selectPosition2 = position;
                }
                if (playerOne != null & playerTwo != null) {
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
        }
    }


    @Click(R.id.add_player)
    public void addPlayer() {
        //显示或者隐藏pop布局
        if (popView == null) {
            //初始化添加玩家时的弹出框
            initPopupView();
            operateBtn.setImageResource(R.drawable.ok);
        } else {
            if (popView.getVisibility() == View.VISIBLE) {
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
            } else {
                if (playerOne != null && playerTwo != null) {
                    Intent intent = new Intent(this, ChooseLevelActivity_.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("playerOne", playerOne);
                    bundle.putSerializable("playerTwo", playerTwo);
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    popView.setVisibility(View.VISIBLE);
                    operateBtn.setImageResource(R.drawable.ok);
                }
            }
        }
    }

    @Click(R.id.btn_player_one)
    public void selectPlayerOne() {
        findViewById(R.id.btn_player_one).setBackgroundResource(R.drawable.player_one);
        findViewById(R.id.btn_player_two).setBackgroundResource(R.drawable.player_two_shadow);
        if (players.get(0).equals(playerCom)) {
            players.remove(playerCom);
        }
        isFirstPlayer = true;
        if (playerTwo != null) {
            players.remove(playerTwo);
        }
        if (playerOne != null) {
            players.add(selectPosition1,playerOne);
        }
        selectAdapter.notifyDataSetChanged();
        listRoot.setBackgroundColor(ContextCompat.getColor(this, R.color.player_one));
    }

    @Click(R.id.btn_player_two)
    public void selectPlayerTwo() {
        findViewById(R.id.btn_player_one).setBackgroundResource(R.drawable.player_one_shadow);
        findViewById(R.id.btn_player_two).setBackgroundResource(R.drawable.player_two);
        if (players.size() == 0 || !players.get(0).equals(playerCom)) {
            players.add(0, playerCom);
        }
        if (playerOne != null) {
            players.remove(playerOne);
        }
        if (playerTwo != null) {
            players.add(selectPosition2,playerTwo);
        }
        isFirstPlayer = false;
        selectAdapter.notifyDataSetChanged();
        listRoot.setBackgroundColor(ContextCompat.getColor(this, R.color.player_two));
    }

    /**
     * 返回按钮触摸监听
     */
    @Override
    public void onBackPressed() {
        if (popView.getVisibility() == View.VISIBLE) {
            popView.setVisibility(View.GONE);
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
}
