package ru.m2d.yad.view.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.m2d.yad.R;
import ru.m2d.yad.databinding.FragmentUserDetailsBinding;
import ru.m2d.yad.viewmodel.UserViewModel;
import ru.m2d.yad.viewmodel.UserViewModelFactory;
import ru.m2d.yad_core.core.models.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetails extends Fragment {

    private static final String USER_ID = "user_id";
    private FragmentUserDetailsBinding binding;

    public UserDetails() {
        // Required empty public constructor
    }

    public static UserDetails newInstance(String param1, String param2) {
        UserDetails fragment = new UserDetails();
        Bundle args = new Bundle();
        args.putString(USER_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final UserViewModel viewModel = ViewModelProviders.of(this, new UserViewModelFactory())
                .get(UserViewModel.class);

        viewModel.setUserID(0);

        binding.setUserViewModel(viewModel);
        binding.setIsLoading(true);

        observeViewModel(viewModel);
    }

    private void observeViewModel(final UserViewModel viewModel) {
        // Observe project data
        viewModel.getObservableUser().observe(getViewLifecycleOwner(), new Observer<User>() {
            @Override
            public void onChanged(@Nullable User project) {
                if (project != null) {
                    binding.setIsLoading(false);
                    viewModel.setUser(project);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate this data binding layout
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_details, container, false);

        // Create and set the adapter for the RecyclerView.
        return (View) binding.getRoot();
    }
}