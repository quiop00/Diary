package com.example.Diary;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;

public class CustomRecyclerAdapter extends FirestoreRecyclerAdapter<Post,CustomRecyclerAdapter.ViewHolder> {
    String previousDate="";
    private OnItemClickListener listener;
    public CustomRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Post> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Post model) {
        holder.title.setText(model.getTitle());
        holder.content.setText(model.getContent());
        holder.time.setText(model.getTime());
        holder.date.setText(model.getDate());
        if(!model.getColor().equals("default")){
            holder.materialCardView.setBackgroundColor(Integer.parseInt(model.getColor()));
        }
        if(!previousDate.equals(model.getDate()))
            holder.date.setVisibility(View.VISIBLE);
        previousDate=model.getDate();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TextView content;
        public  TextView date;
        public  TextView time;
        public MaterialCardView materialCardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title=itemView.findViewById(R.id.post_title);
            this.content=itemView.findViewById(R.id.post_content);
            this.time=itemView.findViewById(R.id.post_time_label);
            this.date=itemView.findViewById(R.id.post_date_label);
            this.materialCardView=itemView.findViewById(R.id.post_card_container);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION&&listener!=null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }
    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot,int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }


}
