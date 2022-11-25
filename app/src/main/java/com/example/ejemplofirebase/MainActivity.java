package com.example.ejemplofirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.function.BiPredicate;


public class MainActivity extends AppCompatActivity {

    Button btnFetch,btnInsert;
    ListView txtData;
    EditText dataToInsert;

    private ArrayList<String> paisesNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtData = (ListView) findViewById(R.id.txtDataFB);
        btnFetch = (Button) findViewById(R.id.btnFetchFB);
        btnInsert = (Button) findViewById(R.id.btnInsertFB);
        dataToInsert = (EditText) findViewById(R.id.et_dataFB);

        btnFetch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Paises")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                for (DataSnapshot dataSnap : dataSnapshot.getChildren() ) {
                                    System.out.println(dataSnap.getKey());
                                    paisesNames.add(dataSnap.getKey());
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, paisesNames);
                                txtData.setAdapter(adapter);
                                paisesNames = new ArrayList<String>();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String data = dataToInsert.getText().toString();

                    if(!data.equals("")){
                        Country country = new Country(data);
                        FirebaseDatabase.getInstance().getReference().child("Paises").child(data).setValue(country);
                        Toast.makeText(MainActivity.this, "Dato Insertado", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
                catch (Exception e){

                }
            }
        });
    }


    class Country {

        public String nombre;

        public Country() {
            // Default constructor required for calls to DataSnapshot.getValue(Proveedor.class)
        }

        public Country(String nombre) {
            this.nombre = nombre;
        }
    }
}