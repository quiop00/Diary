package com.example.Diary;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
public class UserActivity extends AppCompatActivity implements CustomRecyclerAdapter.OnItemClickListener {
    Intent intent;
    FloatingActionButton btnAdd;
    RecyclerView recyclerView;
    CustomRecyclerAdapter recyclerAdapter;
    CollectionReference collectionReference;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        intent=getIntent();
        init();
        setUpRecyclerView();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UserActivity.this,AddActivity.class);
                startActivity(intent);
            }
        });


    }
    public void init(){
        recyclerView =findViewById(R.id.recycler_view);
        btnAdd=findViewById(R.id.add);
        db=FirebaseFirestore.getInstance();
        collectionReference=db.collection("Post");
    }
    public void setUpRecyclerView(){
        Query query=collectionReference.orderBy("date",Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Post> options=new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query,Post.class)
                .build();
        recyclerAdapter=new CustomRecyclerAdapter(options);
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        recyclerAdapter.stopListening();
    }

    @Override
    public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
        History history=documentSnapshot.toObject(History.class);
        Intent intent=new Intent(UserActivity.this, EditActivity.class);
        intent.putExtra("history",history);
        startActivity(intent);

    }
}
