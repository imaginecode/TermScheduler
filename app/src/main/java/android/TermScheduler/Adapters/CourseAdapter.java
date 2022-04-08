package android.TermScheduler.Adapters;

import android.TermScheduler.Entity.Course;
import android.TermScheduler.R;
import android.TermScheduler.UI.DetailedCourseActivity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    private Context context;
    private LayoutInflater inflater;
    public List<Course> courseList;

    public CourseAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    //inner class
    public class CourseViewHolder extends RecyclerView.ViewHolder{
        public TextView itemLayoutRecycler;
        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLayoutRecycler = itemView.findViewById(R.id.item_layout);
            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view)
                {
                    int position = getAdapterPosition();
                    Course activeCourse = courseList.get(position);
                    Intent intent = new Intent(context, DetailedCourseActivity.class);
                    intent.putExtra("courseID", activeCourse.getCourseID());
                    intent.putExtra("position", position);
                    intent.putExtra("termId", activeCourse.getTermID());
                    context.startActivity(intent);
                }
            });
        }
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent,false);
        return new CourseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        Course activeCourse = courseList.get(position);
        holder.itemLayoutRecycler.setText(activeCourse.getCourseTitle());    }

    public void setCourses(List<Course> courses){
        courseList =courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(courseList !=null){
            return courseList.size();
        }
        else return 0;
    }

}