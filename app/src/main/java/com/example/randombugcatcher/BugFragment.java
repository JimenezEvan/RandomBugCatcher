package com.example.randombugcatcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class BugFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private static final String PREFS = "shared_prefs";
    private static final String SAVED_BUGS = "saved_bugs";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BugFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static BugFragment newInstance(int columnCount) {
        BugFragment fragment = new BugFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bug_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            BugRecyclerViewAdapter adapter = new BugRecyclerViewAdapter(context);
            recyclerView.setAdapter(adapter);
            BugListViewModel bugListViewModel = new ViewModelProvider((ViewModelStoreOwner) context).get(BugListViewModel.class);
            bugListViewModel.getBugs().observe(getViewLifecycleOwner(), new Observer<List<Bug>>() {
                @Override
                public void onChanged(List<Bug> bugs) {
                    adapter.setEvents(bugs);
                    MainActivity.setBugCount(adapter.getItemCount());
                }
            });
        }
        return view;
    }


    private ArrayList<Bug> getBugs() {
        SharedPreferences pref = requireActivity().getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String bugsJSON = pref.getString(SAVED_BUGS, "");
        Type type = new TypeToken<ArrayList<Bug>>(){}.getType();
        ArrayList<Bug> bugs = gson.fromJson(bugsJSON, type);
        if(bugs == null) return new ArrayList<>();
        else return bugs;
    }
}