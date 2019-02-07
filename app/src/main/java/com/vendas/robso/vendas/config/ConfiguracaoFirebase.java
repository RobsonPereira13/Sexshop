package com.vendas.robso.vendas.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public final class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth autenticacaoFirebase;

    public static DatabaseReference getFirebase() {
        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;

    }

    public static FirebaseAuth getFirebaseAutenticacao() {
        if (autenticacaoFirebase == null) {
            autenticacaoFirebase = FirebaseAuth.getInstance();
        }
        return autenticacaoFirebase;
    }


}
