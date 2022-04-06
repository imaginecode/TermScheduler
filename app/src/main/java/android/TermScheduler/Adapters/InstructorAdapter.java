package android.TermScheduler.Adapters;

import android.TermScheduler.Entity.Instructor;
import android.TermScheduler.R;
import android.TermScheduler.UI.InstructorActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InstructorAdapter extends RecyclerView.Adapter<InstructorAdapter.InstructorViewHolder> {
    public List<Instructor> mInstructorList;
    private Context context;
    private LayoutInflater inflater;

    public InstructorAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class InstructorViewHolder extends RecyclerView.ViewHolder{
        public TextView recyclerViewItemLayout;

        public InstructorViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewItemLayout = itemView.findViewById(R.id.item_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Instructor selected = mInstructorList.get(position);

                    Intent intent = new Intent(context, InstructorActivity.class );
                    intent.putExtra("instructorID", selected.getInstructorID() );
                    intent.putExtra("position", position);
                    intent.putExtra("courseID", selected.getCourseID());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new InstructorViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull InstructorViewHolder holder, int position) {
        Instructor currentInstructor = mInstructorList.get(position);
        holder.recyclerViewItemLayout.setText(currentInstructor.getInstructorName());
    }

    @Override
    public int getItemCount() {
        return mInstructorList.size();
    }


    public void setInstructor(List<Instructor> instructor){
        mInstructorList = instructor;
        notifyDataSetChanged();
    }

}

