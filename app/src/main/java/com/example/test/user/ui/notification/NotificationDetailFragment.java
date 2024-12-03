package com.example.test.user.ui.notification;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.test.AppViewModel;
import com.example.test.R;
import com.example.test.admin.article.ImageAdapter;
import com.example.test.databinding.FragmentNotificationDetailBinding;
import com.example.test.databinding.FragmentTaskDetailBinding;
import com.example.test.model.Article;
import com.example.test.model.Task;
import com.example.test.model.TaskStatus;
import com.example.test.staff.ui.calendar.CalendarViewModel;
import com.example.test.staff.ui.calendar.TaskDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class NotificationDetailFragment extends Fragment {

    FragmentNotificationDetailBinding binding;
    private CalendarViewModel calendarViewModel;
    private AppViewModel appViewModel;
    private ImageAdapter imageAdapter;
    String articleId;
    String taskId;
    String dateFormatted;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationDetailBinding.inflate(inflater, container, false);
        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        if (getArguments() != null) {
            articleId = getArguments().getString(Article.ARTICLE_ID);
            taskId = getArguments().getString(Task.TASK_ID);
            dateFormatted = getArguments().getString(Task.DATE_FORMATTED);
            calendarViewModel.loadArticle(articleId);
            calendarViewModel.loadTask(dateFormatted, taskId, task -> {
                if (task != null) {
                    if (task.getStatus().equals(TaskStatus.IN_PROGRESS)) {
                        binding.proofBtnLayout.setVisibility(View.GONE);
                    } else {
                        if (task.getStatus().equals(TaskStatus.PENDING)) {
                            binding.proofBtnLayout.setVisibility(View.VISIBLE);
                        } else {
                            binding.proofBtnLayout.setVisibility(View.GONE);
                        }
                        if (task.getProofImages() != null && !task.getProofImages().isEmpty()) {
                            Glide.with(binding.getRoot()).load(task.getProofImages().get(0)).centerCrop().into(binding.proofImgDone);
                        }
                        String statusText = "Chờ xác nhận";
                        if (task.getStatus().equals(TaskStatus.CANCEL)) {
                            statusText = "Đã hủy";
                        } else if (task.getStatus().equals(TaskStatus.DONE)) {
                            statusText = "Đã hoàn thành";
                        }
                        binding.tvProofStatus.setText("Trạng thái: " + statusText);
                    }
                }
            });
        }

        imageAdapter = new ImageAdapter();
        binding.imageRecyclerView.setAdapter(imageAdapter);
        setEvents();
        observeData();
        return binding.getRoot();
    }

    private final int PICK_IMAGES_REQUEST = 0;


    private void setEvents() {

        binding.btnCancel.setOnClickListener(v -> {
            appViewModel.cancelTask(dateFormatted, taskId, message -> {
                Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
                requireActivity().onBackPressed();
            });
        });

        binding.btnAccept.setOnClickListener(v -> appViewModel.acceptTask(dateFormatted, taskId, message -> {
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
            requireActivity().onBackPressed();
        }));

    }

    @SuppressLint("SetTextI18n")
    private void observeData() {

        calendarViewModel.getArticle().observe(getViewLifecycleOwner(), article -> {
            if (article != null) {
                binding.articleTitle.setText(article.getTitle());
                binding.dateCreated.setText(article.getFormattedDateCreated());
                binding.dateCollect.setText(article.getFormattedDateCollect());
                binding.locationCollect.setText("Nơi thu gom: " + article.getLocation().getAddress());
                binding.tvPhoneNumberPost.setText("Số điện thoại người đăng: " + article.getPhoneNumber());
                imageAdapter.setImages(article.getImageUrls());
                appViewModel.getUserById(article.getUserId(), user -> binding.tvUsernamePost.setText("Người đăng: " + user.getDisplayName()));
            }
        });
    }
}