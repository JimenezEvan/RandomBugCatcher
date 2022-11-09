package com.example.randombugcatcher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.randombugcatcher.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button button = findViewById(R.id.btnCatch);

        setSupportActionBar(binding.toolbar);

        // https://stackoverflow.com/questions/60750078/android-studio-java-fab-onclick-alert-dialog-makes-app-crash
        // https://developer.android.com/develop/ui/views/components/dialogs
        binding.fab.setOnClickListener(new View.OnClickListener() {
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
                                //mValues.remove(holder.mItem);
                                //setEvents(mValues);
                            }
                        }).create().show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void catchBug(View view) {
        String bugName = BugDB.common[0];
        String bugLatin = BugDB.latin[0];
        int bugImage = BugDB.images[0];
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(bugName)
                .setMessage(bugLatin + "" + bugImage)
                .create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}