package com.example.bacci.lolteammanager;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;


class MyEventDay extends EventDay implements Parcelable {
    private String mNote;
    @DrawableRes private int imageResource;

    MyEventDay(Calendar day, int imageResource, String note) {
        super(day, imageResource);
        this.imageResource = imageResource;
        mNote = note;
    }

    String getNote() {
        return mNote;
    }

    private MyEventDay(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        mNote = in.readString();
    }

    public static final Creator<MyEventDay> CREATOR = new Creator<MyEventDay>() {
        @Override
        public MyEventDay createFromParcel(Parcel in) {
            return new MyEventDay(in);
        }

        @Override
        public MyEventDay[] newArray(int size) {
            return new MyEventDay[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        parcel.writeInt(this.imageResource);
        parcel.writeString(mNote);
    }

    public int getImageResource(){
        return imageResource;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}