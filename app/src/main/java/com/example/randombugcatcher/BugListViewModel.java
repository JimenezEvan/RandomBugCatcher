package com.example.randombugcatcher;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BugListViewModel extends AndroidViewModel {
    private static final String PREFS = "shared_prefs";
    private static final String SAVED_EVENTS = "saved_bugs";
    private MutableLiveData<List<Bug>> bugs;
    private SavedStateHandle savedStateHandle;
    private Application app;

    public BugListViewModel(Application app, SavedStateHandle stateHandle) {
        super(app);
        this.app = app;
        savedStateHandle = stateHandle;
        this.bugs = new MutableLiveData<>();
        bugs.setValue(loadBugs());
        savedStateHandle.set("bugs", bugs.getValue());
    }

    private List<Bug> loadBugs() {
        SharedPreferences pref = app.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String bugsJSON = pref.getString(SAVED_EVENTS, "");
        Type type = new TypeToken<ArrayList<Bug>>(){}.getType();
        ArrayList<Bug> bugs = gson.fromJson(bugsJSON, type);
        if(bugs == null) return new ArrayList<>();
        else return bugs;
    }

    /*public void setBugs(List<Bug> bugs) {
        this.bugs.setValue(bugs);
    }*/

    public MutableLiveData<List<Bug>> getBugs() {
        return savedStateHandle.getLiveData("bugs", new ArrayList<>());
    }

    public void addBug(Bug bug) {
        List<Bug> bugList = getBugs().getValue();
        bugList.add(bug);
        bugs.setValue(bugList);
        savedStateHandle.set("bugs", bugList);

    }
    public void removeAllBugs() {
        List<Bug> bugList = new ArrayList<>();
        bugs.setValue(bugList);
        savedStateHandle.set("bugs", bugList);
    }
}
