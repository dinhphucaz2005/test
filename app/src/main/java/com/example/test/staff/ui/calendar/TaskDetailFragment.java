package com.example.test.staff.ui.calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.admin.article.ImageAdapter;
import com.example.test.databinding.FragmentTaskDetailBinding;
import com.example.test.model.Article;

public class TaskDetailFragment extends Fragment {

    private FragmentTaskDetailBinding binding;
    private CalendarViewModel calendarViewModel;
    private ImageAdapter imageAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false);
        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);

        if (getArguments() != null) {
            String articleId = getArguments().getString(Article.ARTICLE_ID);
            calendarViewModel.loadArticle(articleId);
        }

        imageAdapter = new ImageAdapter();
        binding.imageRecyclerView.setAdapter(imageAdapter);
        observeData();
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void observeData() {
        calendarViewModel.getArticle().observe(getViewLifecycleOwner(), article -> {
            if (article != null) {
                binding.articleTitle.setText(article.getTitle());
                binding.dateCreated.setText(article.getFormattedDateCreated());
                binding.dateCollect.setText(article.getFormattedDateCollect());
                binding.locationCollect.setText("Nơi thu gom:" + article.getLocation());
                binding.idArticle.setText("ID bài viết: " + article.getId());
                binding.idUserCreated.setText("ID người đăng bài: " + article.getUserId());
                imageAdapter.setImages(article.getImageUrls());
            }
        });
    }
}