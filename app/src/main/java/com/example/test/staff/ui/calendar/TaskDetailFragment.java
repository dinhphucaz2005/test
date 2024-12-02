package com.example.test.staff.ui.calendar;

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

import com.example.test.AppViewModel;
import com.example.test.admin.article.ImageAdapter;
import com.example.test.databinding.FragmentTaskDetailBinding;
import com.example.test.model.Article;
import com.example.test.model.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailFragment extends Fragment {

    private FragmentTaskDetailBinding binding;
    private CalendarViewModel calendarViewModel;
    private AppViewModel appViewModel;
    private ImageAdapter imageAdapter;
    private final List<Uri> imageUris = new ArrayList<>();
    String articleId;
    String taskId;
    String dateFormatted;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false);
        calendarViewModel = new ViewModelProvider(requireActivity()).get(CalendarViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        if (getArguments() != null) {
            articleId = getArguments().getString(Task.TASK_ID);
            taskId = getArguments().getString(Task.TASK_ID);
            dateFormatted = getArguments().getString(Task.DATE_FORMATTED);
            calendarViewModel.loadArticle(articleId);
        }

        imageAdapter = new ImageAdapter();
        binding.imageRecyclerView.setAdapter(imageAdapter);
        setEvents();
        observeData();
        return binding.getRoot();
    }

    private final int PICK_IMAGES_REQUEST = 0;

    private void openImageSelector() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK && data != null) {
            List<Uri> selectedImages = getUris(data);
            imageUris.addAll(selectedImages);
        }
    }

    @NonNull
    private static List<Uri> getUris(@NonNull Intent data) {
        List<Uri> selectedImages = new ArrayList<>();

        if (data.getClipData() != null) {
            ClipData clipData = data.getClipData();
            for (int i = 0; i < clipData.getItemCount(); i++) {
                Uri imageUri = clipData.getItemAt(i).getUri();
                selectedImages.add(imageUri);
            }
        } else if (data.getData() != null) {
            Uri imageUri = data.getData();
            selectedImages.add(imageUri);
        }
        return selectedImages;
    }

    private void setEvents() {

        binding.proofImg.setOnClickListener(v -> {
            openImageSelector();
        });

        binding.btnSending.setOnClickListener(v -> appViewModel.requestAcceptTask(dateFormatted, taskId, imageUris, message -> {
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