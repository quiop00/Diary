package com.example.Diary;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
public class CustomHistoryAdapter extends FirestoreRecyclerAdapter<History,CustomHistoryAdapter.ViewHolder> {
    public CustomHistoryAdapter(@NonNull FirestoreRecyclerOptions<History> options) {
        super(options);
    }
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull History model) {
        holder.content.setText("Tin created this"+model.getTitle());
        holder.time.setText(model.getTime());
        holder.date.setText(model.getDate());
        holder.date.setVisibility(View.VISIBLE);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView content;
        public  TextView date;
        public  TextView time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.content=itemView.findViewById(R.id.post_content);
            this.time=itemView.findViewById(R.id.post_time_label);
            this.date=itemView.findViewById(R.id.post_date_label);

        }
    }



}
