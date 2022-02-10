package com.example.makeaword;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class Game extends AppCompatActivity {

    TextView word;
    EditText pole;
    ImageButton add, hint;
    WordResponse callback;
    FirebaseFirestore db;
    String otv;
    public List<Word> words;
    int id, kol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        word=(TextView)findViewById(R.id.textView);
        pole=(EditText)findViewById(R.id.editTextTextPersonName2);
        add=(ImageButton)findViewById(R.id.imageButton2);
        hint=(ImageButton)findViewById(R.id.hint);
        Intent i=getIntent();
        words=new ArrayList<>();
        db=FirebaseFirestore.getInstance();
        id=i.getIntExtra("id",1);
        if(id==1){
           WordRequest qr = new WordRequest();
            //qr.rus=true;
            qr.cities = true;
            qr.apikey="5cd4c4633844bda0101b473a6";
            new RequestAsync().execute(qr);
        }
        else if(id==2){
            WordRequest qr = new WordRequest();
            //qr.rus=true;
            qr.countries = true;
            qr.apikey="5cd4c4633844bda0101b473a6";
            new RequestAsync().execute(qr);
        }
        else if(id==3){
            db.collection("words").get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if(!queryDocumentSnapshots.isEmpty()) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                int i=0;
                                for (DocumentSnapshot d : list) {
                                    Word w = d.toObject(Word.class);
                                    words.add(w);
                                    i++;
                                }
                                kol=words.size();
                                int rand=(int)(Math.random()*words.size());
                                otv=words.get(rand).getWord();
                                String reverse=shuffleString(otv);
                                word.setText(reverse);
                                words.remove(rand);
                            }
                        }
                    });

        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pole.getText().toString().equals(otv)){
                    if(id==1){
                        WordRequest qr = new WordRequest();
                        //qr.rus=true;
                        qr.cities = true;
                        qr.apikey="5cd4c4633844bda0101b473a6";
                        new RequestAsync().execute(qr);
                    }
                    else if(id==2){
                        WordRequest qr = new WordRequest();
                        //qr.rus=true;
                        qr.countries = true;
                        qr.apikey="5cd4c4633844bda0101b473a6";
                        new RequestAsync().execute(qr);
                    }
                    else if(id==3){
                        kol=words.size();
                        if(kol!=0) {
                            int rand = (int) Math.random() * words.size();
                            otv=words.get(rand).getWord();
                            String reverse=shuffleString(otv);
                            word.setText(reverse);
                            words.remove(rand);
                        }
                        else{
                            db.collection("words").get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @RequiresApi(api = Build.VERSION_CODES.N)
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            if(!queryDocumentSnapshots.isEmpty()) {
                                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                                int i=0;
                                                for (DocumentSnapshot d : list) {
                                                    Word w = d.toObject(Word.class);
                                                    words.add(w);
                                                    i++;
                                                }
                                                kol=words.size();
                                                int rand=(int)(Math.random()*words.size());
                                                otv=words.get(rand).getWord();
                                                String reverse=shuffleString(otv);
                                                word.setText(reverse);
                                                words.remove(rand);
                                            }
                                        }
                                    });
                        }
                    }
                    pole.setText("");
                } else {
                    Toast.makeText(getApplicationContext(), "Неверный ответ!", Toast.LENGTH_LONG).show();
                }
            }
        });
        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id==1||id==2) {
                    word.setText(callback.data.word);
                }
                else{
                    word.setText(otv);
                }
            }
        });
    }
    private void OnResponsee(){
        String reverse=shuffleString(callback.data.word);
        word.setText(reverse);
    }

    class RequestAsync extends AsyncTask<WordRequest, Void, Void> {

        @Override
        protected Void doInBackground(WordRequest... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(JsonPlaceHolderAPI.HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonPlaceHolderAPI JsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);
            Call<WordResponse> call =
                    JsonPlaceHolderAPI.getWords(params[0].rus,params[0].eng,params[0].countries,params[0].cities,params[0].length,params[0].apikey);

            call.enqueue(new Callback<WordResponse>() {
                @Override
                public void onResponse(Call<WordResponse> call, Response<WordResponse> response) {
                    Game.this.callback = response.body();
                    Game.this.otv=callback.data.word;
                    Game.this.OnResponsee();
                }

                @Override
                public void onFailure(Call<WordResponse> call, Throwable t) {
                    Game.this.OnFailure();
                }
            });
            return null;
        }
    }
    private void OnFailure() {
    }

    public static String shuffleString(String string) {
        List<String> letters = Arrays.asList(string.split(""));
        Collections.shuffle(letters);
        String shuffled = "";
        for (String letter : letters) {
            shuffled += letter;
        }
        return shuffled;
    }
    public void firestorecall(){
        db.collection("words").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if(!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            int i=0;
                            for (DocumentSnapshot d : list) {
                                Word w = d.toObject(Word.class);
                                //words.add(w);
                                words.get(i).setWord(w.getWord());
                                i++;
                            }
                        }
                    }
                });
    }

}
