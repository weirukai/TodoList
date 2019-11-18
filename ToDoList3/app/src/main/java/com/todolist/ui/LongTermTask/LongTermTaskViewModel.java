package com.todolist.ui.LongTermTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LongTermTaskViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LongTermTaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}