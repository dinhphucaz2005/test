package com.example.test.ui.dashboard;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.test.R;
import com.example.test.databinding.FragmentDashboardBinding;

import java.util.Objects;

public class DashboardFragment extends Fragment {


    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        dashboardViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);

        setEvents();
        observerData();
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void observerData() {

        dashboardViewModel.getSelectedImages().observe(getViewLifecycleOwner(), uris -> {
            binding.tvImageSelected.setText("Ảnh / Video: " + uris.size() + " đã chọn");
        });

        dashboardViewModel.getArticle().observe(getViewLifecycleOwner(), article -> {
            binding.tvAccessibility.setText(article.getAccessibility());
            binding.tvWeight.setText("Khối lương: " + article.getWeightKgs());
            binding.tvTime.setText("Thời gian thu gom: " + article.getDate());
            binding.tvLocation.setText("Địa chỉ: " + article.getLocation());
            binding.tvPhoneNumber.setText("Liên hệ: " + article.getPhoneNumber());
            StringBuilder categories = new StringBuilder();
            for (int i = 0; i < article.getCategories().size() && i <= 0; i++) {
                categories.append(article.getCategories().get(i));
            }
            binding.tvCategories.setText(categories);
        });
    }

    boolean isKeyboardShowing = false;

    void onKeyboardVisibilityChanged(boolean opened) {
        if (opened) binding.bottomModalSheet.setVisibility(View.GONE);
        else binding.bottomModalSheet.setVisibility(View.VISIBLE);
    }

    private void setEvents() {


        // Tracking keyboard opening
        binding.getRoot().getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect r = new Rect();
            binding.getRoot().getWindowVisibleDisplayFrame(r);
            int screenHeight = binding.getRoot().getRootView().getHeight();

            int keypadHeight = screenHeight - r.bottom;

            if (keypadHeight > screenHeight * 0.15) {
                if (!isKeyboardShowing) {
                    isKeyboardShowing = true;
                    onKeyboardVisibilityChanged(true);
                }
            } else {
                if (isKeyboardShowing) {
                    isKeyboardShowing = false;
                    onKeyboardVisibilityChanged(false);
                }
            }
        });

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        binding.tvAccessibility.setOnClickListener(view -> navController.navigate(R.id.action_navigation_dashboard_to_navigation_accessibility));

        binding.tvCategories.setOnClickListener(view -> navController.navigate(R.id.action_navigation_dashboard_to_navigation_category));

        binding.btnImageAndVideo.setOnClickListener(view -> navController.navigate(R.id.action_navigation_dashboard_to_navigation_image));

        binding.btnWeight.setOnClickListener(view -> navController.navigate(R.id.action_navigation_dashboard_to_navigation_weight));

        binding.btnTime.setOnClickListener(view -> navController.navigate(R.id.action_navigation_dashboard_to_navigation_time));

        binding.btnLocation.setOnClickListener(view -> navController.navigate(R.id.action_navigation_dashboard_to_navigation_location));

        binding.btnContact.setOnClickListener(view -> navController.navigate(R.id.action_navigation_dashboard_to_navigation_contact));

        binding.btnUpload.setOnClickListener(view -> {
            dashboardViewModel.setTitle(Objects.requireNonNull(binding.noteEditText.getText()).toString());
            String message = dashboardViewModel.upload();
            Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
        });
    }
}
