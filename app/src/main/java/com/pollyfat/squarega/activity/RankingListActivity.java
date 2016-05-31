package com.pollyfat.squarega.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pollyfat.squarega.R;
import com.pollyfat.squarega.adapter.RankingListAdapter;
import com.pollyfat.squarega.entity.Player;
import com.pollyfat.squarega.entity.RecordItem;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_ranking_list)
public class RankingListActivity extends Activity {

    @ViewById(R.id.ranking_list)
    ListView rankingList;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    RankingListAdapter adapter;
    public static final String RANKING_LIST = "rankingList";
    List<RecordItem> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(RANKING_LIST, MODE_PRIVATE);
        initList();
    }

    @AfterViews
    void initListData(){
        adapter = new RankingListAdapter(records, this);
        rankingList.setAdapter(adapter);
    }

    void initList() {
        Gson gson = new Gson();
        String s = sharedPreferences.getString(RANKING_LIST, "");
        Type type = new TypeToken<List<RecordItem>>() {
        }.getType();
        records = gson.fromJson(s, type);
        if (records == null) {
            records = new ArrayList<>();
        }
    }
}
