package com.vendas.robso.vendas.activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.vendas.robso.vendas.R;
import com.vendas.robso.vendas.config.ConfiguracaoFirebase;
import com.vendas.robso.vendas.model.Usuarios;

public class LoginActivity extends AppCompatActivity {
    private Button botaoLogar, botaoCadastrar;
    private EditText emailLogin, senhaLogin;
    private TextView alterarSenha;
    private ProgressBar progressBar;


    private Usuarios usuario;
    private FirebaseAuth autenticacaoFirebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//Ao criar a activity o metodo verifica se o usuario estar logado no app
        verificarUsuarioLogado();

        botaoLogar = (Button) findViewById(R.id.btn_logar);
        botaoCadastrar = (Button) findViewById(R.id.btn_cadastro);
        emailLogin = (EditText) findViewById(R.id.emailLogin);
        senhaLogin = (EditText) findViewById(R.id.senhaLogin);
        alterarSenha = (TextView) findViewById(R.id.esqueceuSenha);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        alterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RecuperarSenha.class);
                startActivity(i);
            }
        });

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailLogin.getText().toString().trim().equals("") && !senhaLogin.getText().toString().trim().equals("")) {
                    usuario = new Usuarios();
                    usuario.setEmail(emailLogin.getText().toString());
                    usuario.setSenha(senhaLogin.getText().toString());
                    validarLogin();
                    botaoLogar.setVisibility(View.GONE);
                    botaoCadastrar.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                } else if (emailLogin.getText().toString().trim().equals("")) {
                    emailLogin.setError("informe seu e-mail");
                    emailLogin.requestFocus();
                } else if (senhaLogin.getText().toString().trim().equals("")) {
                    senhaLogin.setError("informe sua senha");
                    senhaLogin.requestFocus();
                }
            }
        });

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, CadastroUsuario.class);
                startActivity(i);
            }
        });

    }

    private void verificarUsuarioLogado() {
        autenticacaoFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if (autenticacaoFirebase.getCurrentUser() != null) {
            abrirTelaPrincipal();
        }
    }

    private void validarLogin() {


        autenticacaoFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacaoFirebase.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Logado com sucesso", Toast.LENGTH_SHORT).show();
                    abrirTelaPrincipal();

                } else {
                    progressBar.setVisibility(View.GONE);
                    botaoLogar.setVisibility(View.VISIBLE);
                    botaoCadastrar.setVisibility(View.VISIBLE);

                    //Tratando execeções no cadastro dos Usuários
                    String erro = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Senha Inválida";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "E-mail inválido";

                    } catch (Exception e) {
                        erro = "Falha ao tentar logar";
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, "Erro: " + erro, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void abrirTelaPrincipal() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
