package com.example.makeaword;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button city, country, no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city=(Button)findViewById(R.id.cities);
        country=(Button)findViewById(R.id.countries);
        no=(Button)findViewById(R.id.no_categories);
        View.OnClickListener cityListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Game.class);
                i.putExtra("id",1);
                startActivity(i);
            }
        };

        View.OnClickListener countryListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Game.class);
                i.putExtra("id",2);
                startActivity(i);
            }
        };

        View.OnClickListener noListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Game.class);
                i.putExtra("id", 3);
                startActivity(i);
            }
        };
        city.setOnClickListener(cityListener);
        country.setOnClickListener(countryListener);
        no.setOnClickListener(noListener);
    }
}