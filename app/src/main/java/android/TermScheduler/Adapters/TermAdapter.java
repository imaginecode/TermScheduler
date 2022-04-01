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

    public List<Term> mTermsList;
    private Context context;
    private LayoutInflater inflater;

    public TermAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    // CLASS
    public class TermViewHolder extends RecyclerView.ViewHolder {
        public TextView itemStructureRecycler;

        private TermViewHolder(@NonNull View itemView) {
            super(itemView);
            itemStructureRecycler = itemView.findViewById(R.id.item_layout);
            //todo add course here

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Term currentTerm = mTermsList.get(position);

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new TermViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        Term currentTerm = mTermsList.get(position);
        holder.itemStructureRecycler.setText(currentTerm.getTermTitle());
    }

    @Override
    public int getItemCount() {
        return mTermsList.size();
    }

    public Term getTermAt(int position) {
        return mTermsList.get(position);
    }

    public void termsSetter(List<Term> terms) {
        mTermsList = terms;
        notifyDataSetChanged();
    }

}
