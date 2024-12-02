package com.example.test.admin.article;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.test.AppViewModel;
import com.example.test.R;
import com.example.test.databinding.FragmentCreateTaskBinding;
import com.example.test.model.Article;
import com.example.test.model.ArticleStatus;

import java.util.Optional;

public class CreateTaskFragment extends Fragment {

    FragmentCreateTaskBinding binding;
    ArticleViewModel articleViewModel;
    AppViewModel appViewModel;
    String articleId;
    ImageAdapter imageAdapter;


    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
                binding.locationCollect.setText("Nơi thu gom: " + article.getLocation().getAddress());
                appViewModel.getUserById(article.getUserId(), user -> binding.tvUsernamePost.setText("Người đăng: " + user.getDisplayName()));
                binding.tvPhoneNumberPost.setText("Số điện thoại người đăng: " + article.getPhoneNumber());
                imageAdapter.setImages(article.getImageUrls());
                if (article.getArticleStatus().equals(ArticleStatus.DONE)) {
                    binding.btnCreateTask.setEnabled(false);
                    binding.createTaskLayout.setVisibility(View.GONE);
                }
            }
        });

        setEvents();
        observeData();
        return binding.getRoot();
    }

    private void observeData() {
        articleViewModel.observeCollectingStaff().observe(getViewLifecycleOwner(), user -> {
            if (user != null) binding.collectingStaff.setText(user.getDisplayName());
        });
    }

    private void setEvents() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_admin);

        binding.btnSelectStaff.setOnClickListener(v -> navController.navigate(R.id.admin_action_createTaskFragment_to_collectStaffFragment));

        binding.btnCreateTask.setOnClickListener(v -> {
            String title = binding.taskTitle.getText().toString();
            if (!title.isEmpty())
                articleViewModel.createTask(title, articleId, binding.checkbox.isChecked(), message -> {
                    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                    articleViewModel.updateArticleStatus(articleId, ArticleStatus.DONE);
                    requireActivity().onBackPressed();
                }, message -> Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show());
        });
    }
}