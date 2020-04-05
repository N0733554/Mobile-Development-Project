package com.example.virtual_pet_app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private ProgressBar hungerBar, exerciseBar;
    int Hunger, Exercise;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        hungerBar = (ProgressBar) view.findViewById(R.id.hungerBar);
        hungerBar.setMax(100);
        hungerBar.setProgress(Hunger);
        exerciseBar = (ProgressBar) view.findViewById(R.id.exerciseBar);
        exerciseBar.setMax(100);
        exerciseBar.setProgress(Exercise);

        return view;
    }

    public HomeFragment(int h, int e)
    {
        this.Hunger = h;
        this.Exercise = e;
    }
}
