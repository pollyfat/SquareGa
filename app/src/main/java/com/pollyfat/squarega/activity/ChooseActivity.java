package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.pollyfat.squarega.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by bugre on 2016/3/20.
 */
@EActivity(R.layout.activity_choose)
public class ChooseActivity extends Activity {

    String rival = "";
    String level = "-1";

    @Click({R.id.fight_com, R.id.fight_pep})
    void chooseCom(View clickedView) {
        switch (clickedView.getId()) {
            case R.id.fight_com:
                rival = "computer";
                findViewById(R.id.flag_com).setVisibility(View.VISIBLE);
                findViewById(R.id.flag_pep).setVisibility(View.INVISIBLE);
                break;
            case R.id.fight_pep:
                rival = "people";
                findViewById(R.id.flag_com).setVisibility(View.INVISIBLE);
                findViewById(R.id.flag_pep).setVisibility(View.VISIBLE);
                break;
        }
    }

    @Click({R.id.difficult_two, R.id.difficult_three, R.id.difficult_four, R.id.difficult_five})
    void chooseDiff(View clickedView) {
        findViewById(R.id.flag_two).setVisibility(View.INVISIBLE);
        findViewById(R.id.flag_three).setVisibility(View.INVISIBLE);
        findViewById(R.id.flag_four).setVisibility(View.INVISIBLE);
        findViewById(R.id.flag_five).setVisibility(View.INVISIBLE);
        switch (clickedView.getId()) {
            case R.id.difficult_two:
                level = "2";
                findViewById(R.id.flag_two).setVisibility(View.VISIBLE);
                break;
            case R.id.difficult_three:
                level = "3";
                findViewById(R.id.flag_three).setVisibility(View.VISIBLE);
                break;
            case R.id.difficult_four:
                level = "4";
                findViewById(R.id.flag_four).setVisibility(View.VISIBLE);
                break;
            case R.id.difficult_five:
                level = "5";
                findViewById(R.id.flag_five).setVisibility(View.VISIBLE);
                break;
        }

    }

    @Click(R.id.start)
    void startGame() {
        if (rival.equals("")) {
            Toast.makeText(ChooseActivity.this, this.getString(R.string.rival_not_chosen), Toast.LENGTH_SHORT).show();
        } else if (level.equals("-1")) {
            Toast.makeText(ChooseActivity.this, this.getString(R.string.level_not_chosen), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(ChooseActivity.this, StartActivity_.class);
            intent.putExtra("rival", rival);
            intent.putExtra("level", level);
            startActivity(intent);
        }
    }
}