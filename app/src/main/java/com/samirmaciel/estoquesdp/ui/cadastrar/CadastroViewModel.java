package com.samirmaciel.estoquesdp.ui.cadastrar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CadastroViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CadastroViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}