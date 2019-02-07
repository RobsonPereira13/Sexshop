package com.vendas.robso.vendas.activitys;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.vendas.robso.vendas.R;
import com.vendas.robso.vendas.config.ConfiguracaoFirebase;
import com.vendas.robso.vendas.model.Usuarios;

public class CadastroUsuario extends AppCompatActivity {
    private Button cadastrar;
    private EditText nome, email, senha;
    private Usuarios usuario;
    private FirebaseAuth autenticacaoFirebase;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = (EditText) findViewById(R.id.nomeCadastro);
        email = (EditText) findViewById(R.id.emailCadastro);
        senha = (EditText) findViewById(R.id.senhaCadastro);
        cadastrar = (Button) findViewById(R.id.btn_cadastrar);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.GONE);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!nome.getText().toString().trim().equals("") && !email.getText().toString().trim().equals("") && !senha.getText().toString().trim().equals("")) {
                    usuario = new Usuarios();
                    usuario.setNome(nome.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    cadastarUsuarios();
                    cadastrar.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                } else if (nome.getText().toString().trim().equals("")) {
                    nome.setError("informe seu nome");
                    nome.requestFocus();
                } else if (email.getText().toString().trim().equals("")) {
                    email.setError("informe seu e-mail");
                    email.requestFocus();
                } else if (senha.getText().toString().trim().equals("")) {
                    senha.setError("informe sua senha");
                    senha.requestFocus();
                }
            }
        });
    }

    private void cadastarUsuarios() {
        autenticacaoFirebase = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacaoFirebase.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(CadastroUsuario.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(CadastroUsuario.this, "Cadastrado com Sucesso", Toast.LENGTH_SHORT).show();

                            FirebaseUser usuarioFirebase = task.getResult().getUser();
                            usuario.setId(usuarioFirebase.getUid());
                            usuario.salvar();
                            //deslogando
                            autenticacaoFirebase.signOut();
                            //fechando a activity de cadastro para voltar a de login no sistema de pilha
                            finish();

                        } else {
                            progressBar.setVisibility(View.GONE);
                            cadastrar.setVisibility(View.VISIBLE);
                            //Tratando execeções no cadastro dos Usuários
                            String erro = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e) {
                                erro = "Sua senha deve conter no minimo 8 caracteres entre letras e numeros";

                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                erro = "E-mail inválido";

                            } catch (FirebaseAuthUserCollisionException e) {
                                erro = "Usuário já Cadastrado";

                            } catch (Exception e) {
                                erro = "Falha ao tentar cadastrar";
                                e.printStackTrace();
                            }
                            Toast.makeText(CadastroUsuario.this, "Erro: " + erro, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
