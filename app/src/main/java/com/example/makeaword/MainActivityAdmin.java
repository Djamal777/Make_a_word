package com.example.makeaword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivityAdmin extends AppCompatActivity {
    Button city, country, no;
    EditText add;
    ImageButton plus;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        city=(Button)findViewById(R.id.cities);
        country=(Button)findViewById(R.id.countries);
        no=(Button)findViewById(R.id.no_categories);
        add=(EditText)findViewById(R.id.pole) ;
        plus=(ImageButton)findViewById(R.id.imageButton);
        db= FirebaseFirestore.getInstance();
        View.OnClickListener cityListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivityAdmin.this,Game.class);
                i.putExtra("id",1);
                startActivity(i);
            }
        };

        View.OnClickListener countryListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivityAdmin.this,Game.class);
                i.putExtra("id",2);
                startActivity(i);
            }
        };

        View.OnClickListener noListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivityAdmin.this,Game.class);
                i.putExtra("id", 3);
                startActivity(i);
            }
        };
        city.setOnClickListener(cityListener);
        country.setOnClickListener(countryListener);
        no.setOnClickListener(noListener);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add.getText().toString().length()>2){
                    Word word=new Word();
                    word.setWord(add.getText().toString());
                    db.collection("words").add(word);
                    add.setText("");
                }
            }
        });
    }
}
