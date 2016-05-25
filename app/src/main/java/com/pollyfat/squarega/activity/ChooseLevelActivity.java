package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.content.Intent;
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

    @Click({R.id.difficult_two, R.id.difficult_three, R.id.difficult_four, R.id.difficult_five})
    void chooseDiff(View clickedView) {
        findViewById(R.id.flag_two).setVisibility(View.INVISIBLE);
        findViewById(R.id.flag_three).setVisibility(View.INVISIBLE);
        findViewById(R.id.flag_four).setVisibility(View.INVISIBLE);
        findViewById(R.id.flag_five).setVisibility(View.INVISIBLE);
        switch (clickedView.getId()) {
            case R.id.difficult_two:
                level = 2;
                findViewById(R.id.flag_two).setVisibility(View.VISIBLE);
                break;
            case R.id.difficult_three:
                level = 3;
                findViewById(R.id.flag_three).setVisibility(View.VISIBLE);
                break;
            case R.id.difficult_four:
                level = 4;
                findViewById(R.id.flag_four).setVisibility(View.VISIBLE);
                break;
            case R.id.difficult_five:
                level = 5;
                findViewById(R.id.flag_five).setVisibility(View.VISIBLE);
                break;
        }
    }

    @Click(R.id.start)
    void startGame() {
         if (level==-1) {
            Toast.makeText(ChooseLevelActivity.this, this.getString(R.string.level_not_chosen), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(ChooseLevelActivity.this, StartActivity_.class);
            intent.putExtra("level", level);
            startActivity(intent);
        }
    }
}
