package com.example.Diary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

import io.opencensus.internal.Utils;
import petrov.kristiyan.colorpicker.ColorPicker;

public class EditActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    CustomHistoryAdapter recyclerAdapter;
    CollectionReference collectionReference;
    FirebaseFirestore db;
    History history;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
//        history= (History) getIntent().getSerializableExtra("history");
//        id=history.getId();
        setUpRecyclerView();

    }
    public void init(){
        recyclerView =findViewById(R.id.recycler_view);
        db=FirebaseFirestore.getInstance();
        collectionReference=db.collection("Author");
    }
    public void setUpRecyclerView(){
        Query query=collectionReference.orderBy("date",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<History> options=new FirestoreRecyclerOptions.Builder<History>()
                .setQuery(query,History.class)
                .build();
        recyclerAdapter=new CustomHistoryAdapter(options);
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.notifyDataSetChanged();
    }

}
