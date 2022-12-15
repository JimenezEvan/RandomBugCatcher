package com.example.randombugcatcher;

import androidx.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BugDB {
    private static List<Bug> bugs = new ArrayList<>();
    //names
    public static String[] common = {
        "Annam walking stick",
            "1common",
            "2common",
            "3common",
            "4common",
            "5common",
            "6common",
            "7common",
            "8common",
            "9rare :)"
    };
    public static String[] latin = {
        "Medauroidea extradentata",
            "1latin",
            "2latin",
            "3latin",
            "4latin",
            "5latin",
            "6latin",
            "7latin",
            "8latin",
            "9el raro :>"
    };
    //images
    public static String[] links = {

    };

    public static void makeDB() {
        FirebaseDatabase.getInstance().getReference().child("bugs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot bug : snapshot.getChildren()) {
                        bugs.add(bug.getValue(Bug.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static Bug bugList(Integer bug) {
        return bugs.get(bug);
    }
    public static int size() {
        return bugs.size();
    }
}
