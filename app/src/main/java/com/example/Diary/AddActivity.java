package com.example.Diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

import io.opencensus.internal.Utils;
import petrov.kristiyan.colorpicker.ColorPicker;

public class AddActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    BottomNavigationView bottomNavigationView;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Button btnAdd,btnCancel;
    EditText edtTitle,edtContent;
    DatabaseReference reference;
    FirebaseUser user;
    FirebaseAuth auth;
    FirebaseFirestore db;
    HashMap<String,Object> hashMap;
    String date;
    String time;
    String myColor="default";
    MaterialCardView postView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setCheckable(true);
                switch (menuItem.getItemId()){
                    case R.id.date_picker:{
                        //int date picker
                        initDatePicker();
                        datePickerDialog.show();
                        break;
                    }
                    case R.id.time_picker:{
                        initTimePicker();
                        timePickerDialog.show();
                        break;
                    }
                    case R.id.post_bg_color:{
                        colorPicker();
                        break;
                    }
                    case R.id.delete_post:{
                        break;
                    }
                }
                return true;
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!TextUtils.isEmpty(edtTitle.getText())&&!TextUtils.isEmpty(edtContent.getText())){
                   hashMap=new HashMap<>();
                   hashMap.put("title",edtTitle.getText().toString());
                   hashMap.put("content",edtContent.getText().toString());
                   hashMap.put("date",date);
                   hashMap.put("time",time);
                   hashMap.put("color",myColor);
                   db=FirebaseFirestore.getInstance();
                   db.collection("Post")
                           .document()
                           .set(hashMap)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   Toast.makeText(AddActivity.this, "Successful",
                                           Toast.LENGTH_SHORT).show();
                               }
                           })
                           .addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(AddActivity.this, "Fail",
                                           Toast.LENGTH_SHORT).show();
                               }
                           });
                   hashMap.clear();
                   hashMap.put("title",edtTitle.getText().toString());
                   hashMap.put("date",date);
                   hashMap.put("time",time);
                   hashMap.put("auth",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                   db.collection("Author")
                           .document()
                           .set(hashMap)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   Toast.makeText(AddActivity.this, "Successful",
                                           Toast.LENGTH_SHORT).show();
                               }
                           })
                           .addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Toast.makeText(AddActivity.this, "Fail",
                                           Toast.LENGTH_SHORT).show();
                               }
                           });
                   Intent intent=new Intent(AddActivity.this,UserActivity.class);
                   startActivity(intent);

               }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });
    }
    public void init(){
        bottomNavigationView=findViewById(R.id.bottom_bar_view);
        btnAdd=findViewById(R.id.btn_done);
        btnCancel=findViewById(R.id.btn_cancel);
        edtTitle=findViewById(R.id.post_edit_title);
        edtContent=findViewById(R.id.post_edit_content);
        postView=findViewById(R.id.post_edit_card_container);
        Calendar calendar=Calendar.getInstance();

        date= DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        time=DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

    }
    public void colorPicker(){
        final ColorPicker colorPicker=new ColorPicker(this);
        ArrayList<String> colors=new ArrayList<>();
        colors.add("#258174");
        colors.add("#3C8D2F");
        colors.add("#20724F");
        colors.add("#6a3ab2");
        colors.add("#323299");
        colors.add("#800080");
        colors.add("#b79716");
        colors.add("#966d37");
        colors.add("#b77231");
        colorPicker.setColors(colors)
                .setColumns(4)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        myColor=String.valueOf(color);
                        postView.setBackgroundColor(Integer.parseInt(myColor));
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
    private void initDatePicker(){
        Calendar calendar=Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(AddActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        date=day+" Th"+month+","+year;
                    }
                }, calendar.get(calendar.YEAR), calendar.get(calendar.MONTH), calendar.get(calendar.DAY_OF_MONTH));
    }
    private void initTimePicker(){
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(AddActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(minute<10)
                time=hourOfDay+":0"+minute;
                else
                    time=hourOfDay+":"+minute;
            }
        }, hour, minute, true);

    }
}
