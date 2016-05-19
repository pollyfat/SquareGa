package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.adapter.PlayerTableAdapter;
import com.pollyfat.squarega.entity.Player;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_choose_players)
public class ChoosePlayersActivity extends Activity {

    @ViewById(R.id.player_table)
    GridView playerTable;

    List<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPlayersList();
        PlayerTableAdapter adapter = new PlayerTableAdapter(this, players);
        playerTable.setAdapter(adapter);
        playerTable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void initPlayersList() {
        //TODO
    }

    @Click(R.id.add_player)
    public void addPlayer(){

    }

    @Click(R.id.btn_player_one)
    public void selectPlayerOne(){

    }

    @Click(R.id.btn_player_two)
    public void selectPlayerTwo(){

    }
}
