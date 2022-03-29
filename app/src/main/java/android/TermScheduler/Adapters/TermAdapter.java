package android.TermScheduler.Adapters;

import android.TermScheduler.Entity.Term;
import android.TermScheduler.R;
import android.TermScheduler.UI.DetailedTermActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItemView;
        private final TextView termStart;
        private final TextView termEnd;

        private TermViewHolder(View itemView){
            super(itemView);
            termItemView=itemView.findViewById(R.id.textView);
            termStart=itemView.findViewById(R.id.termStart);
            termEnd=itemView.findViewById(R.id.termEnd);

            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Term current=mTerms.get(position);
                    Intent intent = new Intent(context, DetailedTermActivity.class);
                    intent.putExtra("id", current.getTermID());
                    intent.putExtra("name", current.getTermTitle());
                    intent.putExtra("startDate", current.getTermStart());
                    intent.putExtra("endDate", current.getTermEnd());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Term> mTerms;
    private final Context context;
    private final LayoutInflater mInflater;


    public TermAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.term_list_item,parent,false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermAdapter.TermViewHolder holder, int position) {
        if(mTerms!=null){
            Term current=mTerms.get(position);
//            String name=current.getTermTitle();
            holder.termItemView.setText(current.getTermTitle());
            holder.termStart.setText(current.getTermStart());
            holder.termEnd.setText(current.getTermEnd());
        }
        else{
            holder.termItemView.setText("No term name");
            holder.termStart.setText("No Term Start");
            holder.termEnd.setText("No Term End");
        }
    }
    public void setTerms(List<Term> terms){
        mTerms=terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mTerms!=null){
            return mTerms.size();
        }
        else return 0;
    }

}
