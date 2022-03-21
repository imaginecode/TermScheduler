package android.TermScheduler.UI;

import android.TermScheduler.Entity.Instructor;
import android.TermScheduler.R;
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
    class InstructorViewHolder extends RecyclerView.ViewHolder{
        private final TextView instructorItemView;
        private final TextView instructorName;
        private final TextView instructorEmail;


        private InstructorViewHolder(View itemView){
            super(itemView);
            instructorItemView=itemView.findViewById(R.id.instructorTextView);
            instructorName = itemView.findViewById(R.id.instructorName);
            instructorEmail = itemView.findViewById(R.id.instructorEmail);



            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Instructor current=mInstructor.get(position);
                    Intent intent = new Intent(context, DetailedAssessmentActivity.class);
                    intent.putExtra("instructorID", current.getInstructorID());
                    intent.putExtra("instructorName", current.getInstructorName());
                    intent.putExtra("email", current.getInstructorEmail());
                    intent.putExtra("phone", current.getInstructorPhone());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Instructor> mInstructor;
    private final Context context;
    private final LayoutInflater mInflater;


    public InstructorAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public InstructorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.instructor_list_item,parent,false);
        return new InstructorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InstructorAdapter.InstructorViewHolder holder, int position) {
        if(mInstructor!=null){
            Instructor current=mInstructor.get(position);

            holder.instructorItemView.setText(current.getInstructorName());
            holder.instructorEmail.setText(current.getInstructorEmail());
        }
        else{
            holder.instructorItemView.setText("Nada");
            holder.instructorEmail.setText("Nada");
        }
    }
    public void setInstructor(List<Instructor> instructors){
        mInstructor=instructors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mInstructor!=null){
            return mInstructor.size();
        }
        else return 0;
    }

}

