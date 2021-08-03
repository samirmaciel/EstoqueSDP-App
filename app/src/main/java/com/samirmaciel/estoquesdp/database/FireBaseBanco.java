package com.samirmaciel.estoquesdp.database;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseBanco extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        //DatabaseReference databaseReference = firebaseDatabase.getReference();
    }

}
