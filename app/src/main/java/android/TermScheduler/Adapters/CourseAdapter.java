package android.TermScheduler.Adapters;

import android.TermScheduler.Entity.Course;
import android.TermScheduler.R;
import android.TermScheduler.UI.CourseActivity;
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
    public List<Course> mCourseList;

    public CourseAdapter(Context context){
        inflater = LayoutInflater.from(context);
        this.context = context;
    }



    //inner class
    public class CourseViewHolder extends RecyclerView.ViewHolder{
        public TextView recyclerViewItemLayout;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewItemLayout = itemView.findViewById(R.id.item_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Course currentCourse = mCourseList.get(position);

                    Intent intent = new Intent(context, CourseActivity.class);
                    intent.putExtra("courseId", currentCourse.getCourseID());
                    intent.putExtra("position", position);
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
        Course currentCourse = mCourseList.get(position);
        holder.recyclerViewItemLayout.setText(currentCourse.getCourseTitle());
    }

    public Course getCourseAt(int position) {
        return mCourseList.get(position);
    }

    public void setCourses(List<Course> courses){
        mCourseList=courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mCourseList!=null){
            return mCourseList.size();
        }
        else return 0;
    }

}