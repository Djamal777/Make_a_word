package com.example.makeaword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registration extends AppCompatActivity {
    EditText log, pas, email;
    Button reg;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        log = (EditText) findViewById(R.id.editTextTextEmailAdress);
        pas = (EditText) findViewById(R.id.editTextTextPassword);
        reg = (Button) findViewById(R.id.reg);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        users = db.getReference("Users");

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(log.getText().toString().length() < 7 || pas.getText().toString().length() < 7 || email.getText().toString().length() < 10)) {
                    auth.createUserWithEmailAndPassword(email.getText().toString(), pas.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            User user = new User();
                            user.setEmail(email.getText().toString());
                            user.setLog(log.getText().toString());
                            user.setPas(pas.getText().toString());
                            users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
                        }
                    });
                    Intent i = new Intent();
                    i.putExtra("email", email.getText().toString());
                    setResult(1, i);
                    finish();
                }
                else if(log.getText().toString().length() < 7){
                    Toast.makeText(getApplicationContext(), "Введите логин от 7 символов!", Toast.LENGTH_LONG).show();
                }
                else if(pas.getText().toString().length() < 7){
                    Toast.makeText(getApplicationContext(), "Введите пароль от 7 символов!", Toast.LENGTH_LONG).show();

                }
                else if(email.getText().toString().length() < 10){
                    Toast.makeText(getApplicationContext(), "Введите корректную почту!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
