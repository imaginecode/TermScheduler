package android.TermScheduler.UI;

import android.TermScheduler.Entity.Course;
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

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {
    class CourseViewHolder extends RecyclerView.ViewHolder{
        private final TextView courseItemView;
        private final TextView courseStartDate;
        private final TextView courseEndDate;


        private CourseViewHolder(View itemView){
            super(itemView);
            courseItemView=itemView.findViewById(R.id.courseTextView);
            courseStartDate = itemView.findViewById(R.id.courseStart);
            courseEndDate = itemView.findViewById(R.id.courseEnd);


            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    final Course current=mCourse.get(position);
                    Intent intent = new Intent(context, DetailedCourseActivity.class);
                    intent.putExtra("courseTitle", current.getCourseTitle());
                    intent.putExtra("courseStartDate", current.getStartDate());
                    intent.putExtra("courseEndDate", current.getEndDate());
                    intent.putExtra("courseStatus", current.getCourseStatus());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Course> mCourse;
    private final Context context;
    private final LayoutInflater mInflater;


    public CourseAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }
    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.course_list_item,parent,false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.CourseViewHolder holder, int position) {
        if(mCourse!=null){
            Course current=mCourse.get(position);

            holder.courseItemView.setText(current.getCourseTitle());
            holder.courseStartDate.setText(current.getStartDate());
            holder.courseEndDate.setText(current.getEndDate());
        }
        else{
            holder.courseItemView.setText("Nada");
            holder.courseStartDate.setText("Nada");
            holder.courseEndDate.setText("Nada");
        }
    }
    public void setCourses(List<Course> courses){
        mCourse=courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCourse!=null){
            return mCourse.size();
        }
        else return 0;
    }

}