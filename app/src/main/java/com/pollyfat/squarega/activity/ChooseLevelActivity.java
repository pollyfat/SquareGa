package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.pollyfat.squarega.R;
import com.pollyfat.squarega.entity.Player;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

/**
 * 选择对手和关卡
 * Created by bugre on 2016/3/20.
 */
@EActivity(R.layout.activity_choose_level)
public class ChooseLevelActivity extends Activity {

    int level = -1;

    @Extra
    Player playerOne;
    @Extra
    Player playerTwo;

    @Click({R.id.level_easy, R.id.level_normal, R.id.level_hard})
    void chooseDiff(View clickedView) {
        findViewById(R.id.medal_easy).setVisibility(View.INVISIBLE);
        findViewById(R.id.medal_normal).setVisibility(View.INVISIBLE);
        findViewById(R.id.medal_hard).setVisibility(View.INVISIBLE);
        switch (clickedView.getId()) {
            case R.id.level_easy:
                level = 3;
                findViewById(R.id.medal_easy).setVisibility(View.VISIBLE);
                break;
            case R.id.level_normal:
                level = 4;
                findViewById(R.id.medal_normal).setVisibility(View.VISIBLE);
                break;
            case R.id.level_hard:
                level = 5;
                findViewById(R.id.medal_hard).setVisibility(View.VISIBLE);
                break;
        }
    }

    @Click(R.id.level_start)
    void startGame() {
        if (level == -1) {
            Toast.makeText(ChooseLevelActivity.this, this.getString(R.string.level_not_chosen), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(ChooseLevelActivity.this, StartActivity_.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("playerOne", playerOne);
            bundle.putSerializable("playerTwo", playerTwo);
            bundle.putInt("level", level);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    @Click(R.id.level_back)
    void back() {
        this.finish();
    }
}
