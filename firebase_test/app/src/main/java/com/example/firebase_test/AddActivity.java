package com.example.firebase_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    EditText name,mssv,lop,quequan;
    Button btn_add, btn_cacel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = (EditText) findViewById(R.id.add_name);
        mssv = (EditText) findViewById(R.id.add_mssv);
        lop = (EditText) findViewById(R.id.add_lop);
        quequan = (EditText) findViewById(R.id.add_quequan);

        btn_add = (Button) findViewById(R.id.btn_AddConfirm);
        btn_cacel = (Button) findViewById(R.id.btn_CancelAdd);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_user();
                clear();
            }
        });

        btn_cacel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void add_user(){
        Map<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("mssv", Long.parseLong(mssv.getText().toString()));
        map.put("lop",lop.getText().toString());
        map.put("quequan",quequan.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("users").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddActivity.this, "Thêm người dùng thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddActivity.this, "Thêm người dùng thất bại", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void clear(){
        name.setText("");
        mssv.setText("");
        lop.setText("");
        quequan.setText("");
    }
}