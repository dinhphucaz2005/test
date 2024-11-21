package com.example.test.user2.ui.notify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.test.R;
import com.example.test.databinding.FragmentDashboardBinding;
import com.example.test.databinding.FragmentNotifyBinding;
import com.example.test.user2.OtherViewModel;

import java.util.ArrayList;

public class NotifyFragment extends Fragment {

    private FragmentNotifyBinding binding;
    private NotifyViewModel notifyViewModel;
    private OtherViewModel otherViewModel;
    private UserAdapter userAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notifyViewModel = new ViewModelProvider(requireActivity()).get(NotifyViewModel.class);
        otherViewModel = new ViewModelProvider(requireActivity()).get(OtherViewModel.class);

        binding = FragmentNotifyBinding.inflate(inflater, container, false);

        notifyViewModel.loadUsers(otherViewModel.getUid());

        userAdapter = new UserAdapter(new ArrayList<>(), user -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_other);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user.getUid());
            navController.navigate(R.id.action_notifyFragment_to_messageFragment, bundle);
        });

        setEvents();
        return binding.getRoot();
    }

    private void setEvents() {
        binding.rvUsers.setAdapter(userAdapter);
        notifyViewModel.getUsers().observe(getViewLifecycleOwner(), userAdapter::updateData);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        notifyViewModel = null;
    }
}