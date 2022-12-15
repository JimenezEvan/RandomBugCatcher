package com.example.randombugcatcher;

import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.randombugcatcher.databinding.FragmentBugBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Bug}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BugRecyclerViewAdapter extends RecyclerView.Adapter<BugRecyclerViewAdapter.ViewHolder> {

    private List<Bug> mValues;
    private static final String PREFS = "shared_prefs";
    private static final String SAVED_EVENTS = "saved_bugs";
    private final Context context;

    public BugRecyclerViewAdapter(Context context) {
        this.context = context;
        if(mValues == null) {
            mValues = new ArrayList<>();
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentBugBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).name);
        holder.mLatinView.setText(mValues.get(position).latin);
        final TextView common = holder.mNameView;
        final TextView latin = holder.mLatinView;
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View dia = inflater.inflate(R.layout.dialog_catch, null);
                TextView diaTxtName = dia.findViewById(R.id.diaTxtName);
                diaTxtName.setText(holder.mItem.name);
                TextView diaTxtLatin = dia.findViewById(R.id.diaTxtLatin);
                diaTxtLatin.setText(holder.mItem.latin);
                ImageView diaImage = dia.findViewById(R.id.diaImgBug);
                Glide.with(view.getContext())
                        .load(holder.mItem.url)
                        .override(700, 482)
                        .into(diaImage);
                builder.setView(dia);

                builder.create();
                builder.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNameView;
        public final TextView mLatinView;
        public final LinearLayout layout;
        public Bug mItem;

        public ViewHolder(FragmentBugBinding binding) {
            super(binding.getRoot());
            mNameView = binding.txtName;
            mLatinView = binding.txtLatin;
            layout = binding.bugLayout;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mLatinView.getText() + "'";
        }
    }

    public void setEvents(List<Bug> bugs) {
        this.mValues = bugs;
        notifyDataSetChanged();
        saveEvents();
    }

    private void saveEvents() {
        SharedPreferences pref = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String eventJSON = gson.toJson(mValues);
        pref.edit().putString(SAVED_EVENTS, eventJSON).commit();
    }
}