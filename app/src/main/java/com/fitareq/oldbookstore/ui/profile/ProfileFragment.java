package com.fitareq.oldbookstore.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.fitareq.oldbookstore.R;
import com.fitareq.oldbookstore.data.model.profile.UserProfileData;
import com.fitareq.oldbookstore.databinding.FragmentProfileBinding;
import com.fitareq.oldbookstore.ui.login.LoginActivity;
import com.fitareq.oldbookstore.utils.CustomDialog;
import com.fitareq.oldbookstore.utils.PrefConstants;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private CustomDialog dialog;
    private NavController navController;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        dialog = new CustomDialog(requireActivity());

        viewModel.getUserProfileInfo().observe(requireActivity(), userInfo ->
        {
            if (userInfo != null) {
                switch (userInfo.getStatus()) {
                    case LOADING:
                        dialog.loading();
                        break;
                    case SUCCESS:
                        setUserInfo(userInfo.getData());
                        dialog.dismissDialog();
                        break;
                    case FAILED:
                        dialog.error("Couldn't load user information");
                        break;
                }
            }
        });


        return binding.getRoot();
    }

    private void setUserInfo(UserProfileData data) {
        String image = data.getImage();
        String name = data.getName();
        String phone = data.getPhone();
        String email = data.getEmail();

        if (image != null && !image.isEmpty()) {
            binding.profileImage.setClipToOutline(true);
            Picasso.with(requireActivity()).load(image).fit().into(binding.profileImage);
        }
        if (name != null && !name.isEmpty()) {
            binding.userName.setText(name);
            binding.userName.setVisibility(View.VISIBLE);
        } else binding.userName.setVisibility(View.GONE);

        if (phone != null && !phone.isEmpty()) {
            binding.userPhone.setText(phone);
            binding.userPhone.setVisibility(View.VISIBLE);
        } else binding.userPhone.setVisibility(View.GONE);

        if (email != null && !email.isEmpty()) {
            binding.userEmail.setText(email);
            binding.userEmail.setVisibility(View.VISIBLE);
        } else binding.userEmail.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        binding.userOrder.setOnClickListener(view1 -> {
            navController.navigate(R.id.orderFragment);
        });
        binding.userLogout.setOnClickListener(view1 -> {
            PrefConstants.removeTokenFromSharedPref(requireActivity());
            PrefConstants.setUserLoggedIn(requireActivity(), false);
            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        });
    }
}