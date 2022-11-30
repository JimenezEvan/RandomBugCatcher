package com.example.randombugcatcher;

import java.util.ArrayList;
import java.util.List;

public class Bug {
    public String name;
    public String latin;
    public int image;
    public Bug(int bug) {
        name = BugDB.common[bug];
        latin = BugDB.latin[bug];
        //image = BugDB.images[bug];
    }

    public static int getBugNum() {
        return (int) (Math.random() * BugDB.common.length);
    }
}
