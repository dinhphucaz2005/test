package com.example.test.admin.article;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.AppViewModel;
import com.example.test.R;
import com.example.test.databinding.FragmentCreateTaskBinding;
import com.example.test.model.Article;

public class CreateTaskFragment extends Fragment {

    FragmentCreateTaskBinding binding;
    ArticleViewModel articleViewModel;
    AppViewModel appViewModel;
    String articleId;
    ImageAdapter imageAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCreateTaskBinding.inflate(inflater, container, false);
        articleViewModel = new ViewModelProvider(requireActivity()).get(ArticleViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);
        imageAdapter = new ImageAdapter();
        binding.imageRecyclerView.setAdapter(imageAdapter);
        if (getArguments() != null) {
            articleId = getArguments().getString(Article.ARTICLE_ID);
        }
        articleViewModel.loadArticle(articleId, article -> {
            if (article != null) {
                binding.articleTitle.setText(article.getTitle());
                binding.dateCreated.setText(article.getFormattedDateCreated());
                binding.dateCollect.setText(article.getFormattedDateCollect());
                binding.locationCollect.setText(article.getLocation());
                binding.idArticle.setText(article.getId());
                binding.idUserCreated.setText(article.getUserId());
                imageAdapter.setImages(article.getImageUrls());
            }
        });

        setEvents();
        return binding.getRoot();
    }

    private void setEvents() {
        binding.btnCreateTask.setOnClickListener(v -> {
            String title = binding.taskTitle.getText().toString();
            if (!title.isEmpty())
                articleViewModel.createArticle(
                        title, articleId, binding.checkbox.isChecked(),
                        message -> {
                            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                            requireActivity().onBackPressed();
                        }
                );
        });
    }
}