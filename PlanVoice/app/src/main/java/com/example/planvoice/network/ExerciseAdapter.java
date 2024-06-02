package com.example.planvoice.network;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.planvoice.R;

import java.util.List;
import java.util.ArrayList;

public class ExerciseAdapter extends BaseAdapter {
    private Context mContext;
    private List<ExerciseResponse> mExerciseList;
    private List<ExerciseResponse> filteredList;

    public ExerciseAdapter(Context context, List<ExerciseResponse> exerciseList) {
        mContext = context;
        mExerciseList = exerciseList;
    }

    @Override
    public int getCount() {
        return mExerciseList.size();
    }

    @Override
    public Object getItem(int position) {
        return mExerciseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exercise_item, null);
        }

        TextView exerciseNameTextView = view.findViewById(R.id.exerciseName);
        TextView exerciseDescriptionTextView = view.findViewById(R.id.exerciseDescription);
        ImageView exerciseImageView = view.findViewById(R.id.exerciseImage);

        ExerciseResponse exercise = mExerciseList.get(position);
        exerciseNameTextView.setText(exercise.getExerciseName());
        exerciseDescriptionTextView.setText(exercise.getExerciseDescription());

        // 이미지 리소스의 이름을 가져옵니다.
        String imageName = exercise.getImageURL();
        // 이미지 리소스의 ID를 가져옵니다.
        int imageResource = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());
        // 이미지를 설정합니다.
        exerciseImageView.setImageResource(imageResource);

        return view;
    }
    public void updateList(List<ExerciseResponse> filteredList) {
        this.filteredList = new ArrayList<>(filteredList);
        notifyDataSetChanged();
    }

}
