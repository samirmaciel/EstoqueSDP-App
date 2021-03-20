package com.samirmaciel.estoquesdp.ui.estoque;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EstoqueViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public EstoqueViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}