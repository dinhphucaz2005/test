package com.example.test.staff.ui.notify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.test.R;
import com.example.test.databinding.FragmentNotifyBinding;
import com.example.test.model.User;
import com.example.test.AppViewModel;

import java.util.ArrayList;

public class NotifyFragment extends Fragment {

    private FragmentNotifyBinding binding;
    private UsersViewModel usersViewModel;
    private AppViewModel appViewModel;
    private UserAdapter userAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        usersViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);
        appViewModel = new ViewModelProvider(requireActivity()).get(AppViewModel.class);

        binding = FragmentNotifyBinding.inflate(inflater, container, false);

        usersViewModel.loadUsers(appViewModel.getUid());

        userAdapter = new UserAdapter(new ArrayList<>(), user -> {
            NavController navController = Navigation.findNavController(binding.getRoot());
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", user.getUid());
            appViewModel.getRole(role -> {
                if (role == User.STAFF)
                    navController.navigate(R.id.staff_action_notifyFragment_to_messageFragment, bundle);
                else
                    navController.navigate(R.id.action_notifyFragment2_to_messageFragment3, bundle);
            });
        });

        setEvents();
        return binding.getRoot();
    }

    private void setEvents() {
        binding.rvUsers.setAdapter(userAdapter);
        usersViewModel.getUsers().observe(getViewLifecycleOwner(), userAdapter::updateData);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        usersViewModel = null;
    }
}