package com.example.gym4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private List<Exercise> exerciseList;
    private int expandedPosition = RecyclerView.NO_POSITION;

    public ExerciseAdapter(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);

        holder.exerciseTitle.setText(exercise.getTitle());
        holder.exerciseDescription.setText(exercise.getDescription());
        holder.exerciseImage.setImageResource(exercise.getImageResource());

        final boolean isExpanded = position == expandedPosition;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousExpandedPosition = expandedPosition;
                expandedPosition = isExpanded ? RecyclerView.NO_POSITION : position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });

        holder.itemView.setActivated(isExpanded);
        holder.exerciseDescription.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        // Agrega aquí cualquier otra lógica de expansión que desees
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        ImageView exerciseImage;
        TextView exerciseTitle;
        TextView exerciseDescription;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseImage = itemView.findViewById(R.id.exerciseImage);
            exerciseTitle = itemView.findViewById(R.id.exerciseTitle);
            exerciseDescription = itemView.findViewById(R.id.exerciseDescription);
        }
    }
}
