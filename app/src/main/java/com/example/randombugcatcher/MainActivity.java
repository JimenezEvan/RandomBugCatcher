package com.example.randombugcatcher;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import java.util.Calendar;

import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.bumptech.glide.Glide;
import com.example.randombugcatcher.databinding.ActivityMainBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    public static int bugCount = 0;
    public static void setBugCount(int itemCount) {
        bugCount = itemCount;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BugDB.makeDB();
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.burnall);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button button = findViewById(R.id.btnCatch);

        setSupportActionBar(binding.toolbar);


        binding.fabBurn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle(R.string.diaTitle)
                        .setMessage(R.string.diaMessage)
                        .setNegativeButton(R.string.diaCancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                            }
                        })
                        .setPositiveButton(R.string.diaConfirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mediaPlayer.start();
                                BugListViewModel m = new ViewModelProvider(MainActivity.this).get(BugListViewModel.class);
                                m.removeAllBugs();
                            }
                        }).create().show();
            }

        });
        binding.fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BugRecyclerViewAdapter adapter = new BugRecyclerViewAdapter(view.getContext());
                Toast.makeText(MainActivity.this, "Bug Count: " + bugCount, Toast.LENGTH_SHORT).show();
            }
        });
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 14);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void catchBug(View view) {
        Integer num = Bug.getBugNum();

        Bug bug = new Bug(num);
        //add to list
        BugListViewModel viewModel = new ViewModelProvider(this).get(BugListViewModel.class);
        viewModel.addBug(bug);
        //dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        View dia = inflater.inflate(R.layout.dialog_catch, null);
        TextView diaTxtName = dia.findViewById(R.id.diaTxtName);
        diaTxtName.setText(bug.name);
        TextView diaTxtLatin = dia.findViewById(R.id.diaTxtLatin);
        diaTxtLatin.setText(bug.latin);
        ImageView diaImage = dia.findViewById(R.id.diaImgBug);
        Glide.with(view.getContext())
                .load(bug.url)
                .override(700, 482)
                .into(diaImage);
        builder.setView(dia);

        builder.create().show();
        //
    }
}