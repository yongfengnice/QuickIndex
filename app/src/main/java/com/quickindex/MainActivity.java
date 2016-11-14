package com.quickindex;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.quickindex.adapter.PersonAdapter;
import com.quickindex.bean.Person;
import com.quickindex.utils.BarUtils;
import com.quickindex.view.IndexBar;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private IndexBar mIndexBar;
    private TextView mTextView;
    private ArrayList<Person> mPersonList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_main);
        mListView.addHeaderView(View.inflate(this, R.layout.view_header, null));
        mIndexBar = (IndexBar) findViewById(R.id.bar);
        mTextView = (TextView) findViewById(R.id.tv_center);
    }


    private void initData() {
        for (int i = 0; i < Data.NAMES.length; i++) {
            mPersonList.add(new Person(Data.NAMES[i]));
        }
        Collections.sort(mPersonList);
        mListView.setAdapter(new PersonAdapter(mPersonList));
    }

    private void initEvent() {
        mIndexBar.setOnLetterUpdateListener(new IndexBar.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter) {
                mIndexBar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.index_bar_press));
                showToast(letter);
                //修改listView位置
                if (BarUtils.sFirstChar.equals(letter)) {
                    mListView.setSelection(0);
                } else if (BarUtils.sLastChar.equals(letter)) {
                    mListView.setSelection(mPersonList.size() - 1);
                } else {
                    for (int i = 0; i < mPersonList.size(); i++) {
                        String index = String.valueOf(mPersonList.get(i).getPinyin().charAt(0));
                        if (TextUtils.equals(letter, index)) {
                            mListView.setSelection(i + mListView.getHeaderViewsCount());
                            break;
                        }
                    }
                }
            }

            @Override
            public void onLetterNone() {
                mIndexBar.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.index_bar_normal));
                mTextView.setVisibility(View.GONE);
            }
        });
    }

    private void showToast(String letter) {
        mTextView.setText(letter);
        mTextView.setVisibility(View.VISIBLE);
    }
}
