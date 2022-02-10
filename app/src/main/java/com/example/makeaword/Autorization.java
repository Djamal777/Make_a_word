package com.example.makeaword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Autorization extends AppCompatActivity {
    EditText email, pas;
    Button aut, reg;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.autorization);
        email=(EditText)findViewById(R.id.editTextTextEmailAdress);
        pas=(EditText)findViewById(R.id.editTextTextPassword);
        aut=(Button)findViewById(R.id.aut);
        reg=(Button)findViewById(R.id.reg);
        email.setText("");
        pas.setText("");

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference("Users");

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Autorization.this,Registration.class);
                startActivityForResult(i,1);
            }
        });
        aut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String m,p;
                p=pas.getText().toString();
                m=email.getText().toString();
                if(m.equals("admin@mail.ru")&&p.equals("admin")){
                    Intent i=new Intent(Autorization.this,MainActivityAdmin.class);
                    email.setText("");
                    pas.setText("");
                    startActivity(i);
                }
                else {
                    if (!(email.getText().length() < 3 || pas.getText().length() < 3)) {
                        auth.signInWithEmailAndPassword(email.getText().toString(), pas.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Intent i = new Intent(Autorization.this, MainActivity.class);
                                email.setText("");
                                pas.setText("");
                                startActivity(i);
                            }
                        });
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Неверный логин или пароль!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
