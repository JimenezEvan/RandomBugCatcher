package com.example.randombugcatcher;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Bug implements Parcelable {
    public String name;
    public String latin;
    public String link;
    public Bug(int bug) {
        name = BugDB.common[bug];
        latin = BugDB.latin[bug];
        //link = BugDB.links[bug];
    }

    protected Bug(Parcel in) {
        name = in.readString();
        latin = in.readString();
        link = in.readString();
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

    public static int getBugNum() {
        return (int) (Math.random() * BugDB.common.length);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(latin);
        parcel.writeString(link);
    }
}
