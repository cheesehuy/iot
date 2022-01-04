package com.example.afinal;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;
public class MainActivity extends AppCompatActivity {
    DatabaseReference reff;
    TextView trangthaituoi, trangthainuoc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        trangthaituoi = (TextView)findViewById(R.id.trangthaituoi);
        trangthainuoc = (TextView)findViewById(R.id.trangthainuoc);
        reff = FirebaseDatabase.getInstance().getReference();
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nuoc = snapshot.child("State-sensor").getValue().toString();
                trangthainuoc.setText(nuoc);
                if(nuoc.equals("hết nước")){
                    trangthainuoc.setTextColor(Color.RED);
                }
                else if (nuoc.equals("còn nước")){
                    trangthainuoc.setTextColor(Color.BLUE);
                }
                String bom = snapshot.child("State-water").getValue().toString();
                trangthaituoi.setText(bom);
                if(bom.equals("đang bơm nước")){
                    trangthaituoi.setTextColor(Color.BLUE);
                }
                else if (bom.equals("dừng bơm nước")){
                    trangthaituoi.setTextColor(Color.RED);
                }
              }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "fail",Toast.LENGTH_LONG);
            }
        });
    }
}