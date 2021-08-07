package com.sebastianpitur.traveljournal.ui.about_us;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutUsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("We are a small company from Hawaii, do not hesitate to contact us if you want to help our cause");
    }

    public LiveData<String> getText() {
        return mText;
    }
}