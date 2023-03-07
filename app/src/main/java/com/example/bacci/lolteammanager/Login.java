package com.example.bacci.lolteammanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    UserFactory uf = UserFactory.getInstance();
    TextView reg, error;
    EditText username, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        username = findViewById(R.id.user);
        password = findViewById(R.id.psw);
        login = findViewById(R.id.accedi);

        reg = findViewById(R.id.reg);
        error = findViewById(R.id.errore);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkUser()){

                    if(uf.loginUsername(username.getText().toString(), password.getText().toString())){
                        error.setVisibility(View.GONE);
                        User utente = uf.getUtente(username.getText().toString(), password.getText().toString());

                        Intent profilePlayer = new Intent(Login.this, Home.class);

                        profilePlayer.putExtra("currentUserID", utente.getIdPlayer());

                        startActivity(profilePlayer);
                    } else {
                        username.getText().clear();
                        password.getText().clear();
                        error.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrazione = new Intent(Login.this, Registrazione.class);

                startActivity(registrazione);
            }
        });
    }

    private boolean checkUser(){
        if((username.getText() == null || username.getText().length() == 0) &&
                (password.getText() == null || password.getText().length() == 0)){
            username.setError("ERRORE! Inserire lo username.");
            password.setError("ERRORE! Inserire la password.");
        } else if(username.getText() == null || username.getText().length() == 0) {
            username.setError("ERRORE! Inserire lo username.");
        } else if(password.getText() == null || password.getText().length() == 0){
            password.setError("ERRORE! Inserire la password.");
        } else return true;

        return false;
    }
}
