package com.todolist.ui.TemporalTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TemporalTaskViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TemporalTaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}