package android.TermScheduler.Adapters;

import android.TermScheduler.Entity.Assessment;
import android.TermScheduler.R;
import android.TermScheduler.UI.DetailedAssessmentActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssessmentAdapter extends RecyclerView.Adapter<AssessmentAdapter.AssessmentViewHolder> {
    class AssessmentViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentItemView;
        private final TextView assessmentStartDate;
        private final TextView assessmentEndDate;


        private AssessmentViewHolder(View itemView){
            super(itemView);
            assessmentItemView=itemView.findViewById(R.id.assessmentTextView);
            assessmentStartDate = itemView.findViewById(R.id.assessmentStart);
            assessmentEndDate = itemView.findViewById(R.id.assessmentEnd);


            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Assessment current=mAssessment.get(position);
                    Intent intent = new Intent(context, DetailedAssessmentActivity.class);
                    intent.putExtra("assessmentName", current.getAssessmentTitle());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    intent.putExtra("assessmentDate", current.getAssessmentStart());
                    intent.putExtra("assessmentID", current.getAssessmentID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Assessment> mAssessment;
    private final Context context;
    private final LayoutInflater mInflater;


    public AssessmentAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.assessment_list_item,parent,false);
        return new AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentAdapter.AssessmentViewHolder holder, int position) {
        if(mAssessment!=null){
            Assessment current=mAssessment.get(position);

            holder.assessmentItemView.setText(current.getAssessmentTitle());
            holder.assessmentStartDate.setText(current.getAssessmentStart());
            holder.assessmentEndDate.setText(current.getAssessmentEnd());
        }
        else{
            holder.assessmentItemView.setText("Nada");
            holder.assessmentStartDate.setText("Nada");
            holder.assessmentEndDate.setText("Nada");
        }
    }
    public void setAssessments(List<Assessment> assessments){
        mAssessment=assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mAssessment!=null){
            return mAssessment.size();
        }
        else return 0;
    }

}

