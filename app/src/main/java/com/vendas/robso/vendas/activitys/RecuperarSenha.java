package com.vendas.robso.vendas.activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.vendas.robso.vendas.R;
import com.vendas.robso.vendas.config.ConfiguracaoFirebase;

public class RecuperarSenha extends AppCompatActivity {

    private Button botaoAlterar;
    private EditText alterarEmail;
    private ProgressBar progressBar;
    private FirebaseAuth autenticacaoFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        botaoAlterar = (Button) findViewById(R.id.btn_Alterar);
        alterarEmail = (EditText) findViewById(R.id.emailAlterar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);


        autenticacaoFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();

        botaoAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = alterarEmail.getText().toString();
                botaoAlterar.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
//                if (TextUtils.isEmpty(userEmail)) {
                if (alterarEmail.getText().toString().trim().equals("")) {
                    alterarEmail.setError("informe o e-mail");
                    alterarEmail.requestFocus();
                    progressBar.setVisibility(View.GONE);
                    botaoAlterar.setVisibility(View.VISIBLE);


                } else {

                    autenticacaoFirebase.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(RecuperarSenha.this, "Consulte sua caixa de mensagens para alterar sua senha", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RecuperarSenha.this, LoginActivity.class));

                            } else {
                                progressBar.setVisibility(View.GONE);
                                botaoAlterar.setVisibility(View.VISIBLE);
                                Toast.makeText(RecuperarSenha.this, "E-mail não cadastrado", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });
                }
            }
        });

    }
}
