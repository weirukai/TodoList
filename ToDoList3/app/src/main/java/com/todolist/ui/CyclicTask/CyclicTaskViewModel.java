package com.todolist.ui.CyclicTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CyclicTaskViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CyclicTaskViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}