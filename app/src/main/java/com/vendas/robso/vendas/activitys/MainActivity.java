package com.vendas.robso.vendas.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vendas.robso.vendas.R;
import com.vendas.robso.vendas.config.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    private Button botao;
    private FirebaseAuth autenticacaoFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
