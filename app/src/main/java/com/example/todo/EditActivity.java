package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {
    private EditText title,description;
    private Button updateButton,deleteButton;
    private String titleSend,descriptionSend;
    private DatabaseReference mReference;
    private ListData mListData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        title=(EditText)findViewById(R.id.title);
        description=(EditText)findViewById(R.id.desc);
        updateButton=(Button)findViewById(R.id.updateButton);
        deleteButton=(Button)findViewById(R.id.deleteButton);
        mReference= FirebaseDatabase.getInstance().getReference();
        final Intent i=getIntent();
        String getTitle=i.getStringExtra("title");
        String getDescription=i.getStringExtra("desc");
        final String id=i.getStringExtra("id");
        title.setText(getTitle);
        description.setText(getDescription);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleSend=title.getText().toString().trim();
                descriptionSend=description.getText().toString().trim();
                mListData=new ListData(id,titleSend,descriptionSend);
                mReference.child("Notes").child(id).setValue(mListData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditActivity.this,"Notes Updated",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(EditActivity.this,HomeScreen.class));
                        finish();
                    }
                });
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReference.child("Notes").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(EditActivity.this,"Notes Deleted",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(EditActivity.this,HomeScreen.class));
                        finish();
                    }
                });
            }
        });
    }
}
