package com.example.randombugcatcher;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Bug implements Parcelable {
    public String name;
    public String latin;
    public String url;

    public Bug(Integer bug) {
        name = BugDB.bugList(bug).name;
        latin = BugDB.bugList(bug).latin;
        url = BugDB.bugList(bug).url;
    }
    public Bug() {

    }

    protected Bug(Parcel in) {
        name = in.readString();
        latin = in.readString();
        url = in.readString();
    }

    public static final Creator<Bug> CREATOR = new Creator<Bug>() {
        @Override
        public Bug createFromParcel(Parcel in) {
            return new Bug(in);
        }

        @Override
        public Bug[] newArray(int size) {
            return new Bug[size];
        }
    };

    public static Integer getBugNum() {
        return (Integer) (int) (Math.random() * BugDB.size());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(latin);
        parcel.writeString(url);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String link) {
        this.url = link;
    }

}
