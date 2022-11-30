package com.example.randombugcatcher;

import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.randombugcatcher.databinding.FragmentBugBinding;
import com.google.gson.Gson;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Bug}.
 * TODO: Replace the implementation with code for your data type.
 */
public class BugRecyclerViewAdapter extends RecyclerView.Adapter<BugRecyclerViewAdapter.ViewHolder> {

    private List<Bug> mValues;

    private static final String PREFS = "shared_prefs";
    private static final String SAVED_EVENTS = "saved_events";
    private final Context context;

    public BugRecyclerViewAdapter(Context context) {
        this.context = context;
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

                builder.setTitle(holder.mItem.name);
                builder.setMessage(holder.mItem.latin + "\nMore Info: " + "link");

                builder.create().show();
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