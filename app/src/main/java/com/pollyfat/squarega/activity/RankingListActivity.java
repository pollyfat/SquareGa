package com.pollyfat.squarega.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pollyfat.squarega.R;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;

@EActivity(R.layout.activity_ranking_list)
public class RankingListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @ItemClick(R.id.ranking_list)
    public void rankingList(){

    }

}
