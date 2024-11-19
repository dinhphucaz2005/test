package com.example.test.ui.dashboard;

import static android.app.Activity.RESULT_OK;

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

import com.example.test.databinding.FragmentImageBinding;

import java.util.ArrayList;
import java.util.List;


public class ImageFragment extends Fragment {

    private static final int PICK_IMAGES_REQUEST = 1;
    private FragmentImageBinding binding;
    private DashboardViewModel dashboardViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentImageBinding.inflate(inflater, container, false);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        setEvents();
        observerData();
        return binding.getRoot();
    }

    private void observerData() {
        dashboardViewModel.getSelectedImages().observe(requireActivity(), uris -> {
            if (!uris.isEmpty()) {
                binding.btnSelectImage.setImageURI(uris.get(0));
            }
        });
    }

    private void setEvents() {
        binding.btnFinish.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });

        binding.btnSelectImage.setOnClickListener(view -> {
            openImageSelector();
        });
    }

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
            dashboardViewModel.addSelectedImages(selectedImages);
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
}