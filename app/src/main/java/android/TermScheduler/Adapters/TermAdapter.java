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

    public List<Term> termList;
    private Context context;
    private LayoutInflater inflater;

    public TermAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }



    // inner class
    public class TermViewHolder extends RecyclerView.ViewHolder {
        public TextView itemRecycler;
        private TermViewHolder(@NonNull View itemView) {
            super(itemView);
            itemRecycler = itemView.findViewById(R.id.item_layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    int position = getAdapterPosition();
                    Term currentTerm = termList.get(position);

                    Intent intent = new Intent(context, DetailedTermActivity.class);
                    intent.putExtra("termId", currentTerm.getTermID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TermViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        Term currentTerm = termList.get(position);
        holder.itemRecycler.setText(currentTerm.getTermTitle());
    }

    public void termsSetter(List<Term> terms) {
        termList = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

}
