package br.com.caelum.leozd.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.caelum.leozd.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_email)
    EditText campoEmail;

    @BindView(R.id.login_senha)
    EditText campoSenha;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;
    private boolean flagUsuarioLogado = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser != null) {
            logaUsuario();
        }

    }

    @NonNull
    private void naoPodeClicarNoBotao(Button button) {
        button.setText("Carregando");
        button.setClickable(false);
        button.setBackgroundColor(Color.GRAY);
    }

    private void podeClicarNo(Button button, String texto) {
        button.setClickable(true);
        button.setText(texto);
        button.setBackgroundColor(getColor(R.color.colorPrimaryDark));
    }

    private void logaUsuario() {
        Intent vaiParaMain = new Intent(this, MainActivity.class);
        startActivity(vaiParaMain);
        finish();
    }

    @OnClick(R.id.login_logar)
    public void logar(Button button) {
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        String texto = button.getText().toString();

        naoPodeClicarNoBotao(button);

        if(!email.isEmpty() || !senha.isEmpty()){
            mAuth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(trataRespostaFirebase(button, texto));
        } else {
            Snackbar.make(campoSenha, "Todos os campos são obrigatórios", Snackbar.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private OnCompleteListener<AuthResult> trataRespostaFirebase(final Button button, final String texto) {
        return new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                podeClicarNo(button, texto);

                if (task.isSuccessful()) {
                    logaUsuario();
                } else {
                    trataErro(task.getException());
                }

            }
        };
    }

    private void trataErro(Exception erro) {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.casadocodigo)
                .setMessage(erro.getMessage())
                .setTitle("Tivemos problema")
                .show();
    }

    @OnClick(R.id.login_novo)
    public void novoUsuario(Button button) {
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        String texto = button.getText().toString();
        naoPodeClicarNoBotao(button);

        if(!email.isEmpty() || !senha.isEmpty()){
            mAuth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(trataRespostaFirebase(button, texto));
        } else {
            Snackbar.make(campoSenha, "Todos os campos são obrigatórios", Snackbar.LENGTH_INDEFINITE).show();
        }
    }
}
