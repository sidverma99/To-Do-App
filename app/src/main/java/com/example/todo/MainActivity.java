package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText mTitle,mDescription;
    private String titleSend,descriptionSend;
    private Button save;
    private DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitle=(EditText)findViewById(R.id.title);
        mDescription=(EditText)findViewById(R.id.desc);
        mReference= FirebaseDatabase.getInstance().getReference();
        save=(Button)findViewById(R.id.addNotes);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleSend=mTitle.getText().toString().trim();
                descriptionSend=mDescription.getText().toString().trim();
                if (TextUtils.isEmpty(titleSend) || TextUtils.isEmpty(descriptionSend)){
                    Toast.makeText(getApplicationContext(),"All fields are mandatory",Toast.LENGTH_LONG).show();
                    return;
                }
                String id=mReference.push().getKey();
                ListData listData=new ListData(id,titleSend,descriptionSend);
                mReference.child("Notes").child(id).setValue(listData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "Notes Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,HomeScreen.class));
                        finish();
                    }
                });
            }
        });
    }
}
