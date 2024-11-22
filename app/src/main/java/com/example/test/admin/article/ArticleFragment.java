package com.example.test.admin.article;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.R;
import com.example.test.databinding.FragmentArticleBinding;
import com.example.test.model.Article;
import com.example.test.user.ui.dashboard.ArticleAdapter;

import java.util.ArrayList;

public class ArticleFragment extends Fragment {

    private FragmentArticleBinding binding;
    private ArticleViewModel articleViewModel;
    private ArticleAdapter articleAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentArticleBinding.inflate(inflater, container, false);

        articleViewModel = new ViewModelProvider(requireActivity()).get(ArticleViewModel.class);

        articleAdapter = new ArticleAdapter(new ArrayList<>(), article -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Article.ARTICLE_ID, article.getId());
            NavController navController = Navigation.findNavController(binding.getRoot());
            navController.navigate(R.id.admin_action_articleFragment_to_createTaskFragment, bundle);
        });
        binding.adminRvArticles.setAdapter(articleAdapter);
        articleViewModel.getArticles(articleAdapter::setArticles);

        setEvents();
        return binding.getRoot();
    }

    private void setEvents() {

    }


}