package com.quickindex.bean;

import android.text.TextUtils;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.quickindex.utils.BarUtils;

public class Person implements Comparable<Person> {

    private String mName;
    private String mPinyin;

    public String getPinyin() {
        return mPinyin;
    }

    public void setPinyin(String pinyin) {
        this.mPinyin = pinyin;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Person(String name) {
        this.mName = name;
//        this.mPinyin = PinyinUtils.getPinyin(mName);
        if (!TextUtils.isEmpty(name)) {
            try {
                this.mPinyin = PinyinHelper.convertToPinyinString(name.trim(), "", PinyinFormat.WITHOUT_TONE);
            } catch (PinyinException e) {
                e.printStackTrace();
            }
        }
        if (!TextUtils.isEmpty(mPinyin)) {
            this.mPinyin = this.mPinyin.toUpperCase();
            char firstChar = mPinyin.charAt(0);
            if (firstChar < 'A' || firstChar > 'Z') {
                this.mPinyin = TextUtils.concat(BarUtils.sChar_z, this.mPinyin).toString();
            }
        } else {
            this.mPinyin = BarUtils.sChar_z;
        }
    }

    @Override
    public int compareTo(Person o) {
        return this.mPinyin.compareTo(o.getPinyin());
    }
}
