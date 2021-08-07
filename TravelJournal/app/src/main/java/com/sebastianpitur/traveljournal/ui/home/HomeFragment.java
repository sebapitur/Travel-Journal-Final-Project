package com.sebastianpitur.traveljournal.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.sebastianpitur.traveljournal.R;
import com.sebastianpitur.traveljournal.Trips;
import com.sebastianpitur.traveljournal.databinding.FragmentHomeBinding;

import static androidx.databinding.DataBindingUtil.setContentView;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private EditText nameSurnameEditText;
    private EditText emailAddressEditText;
    private TextView nameAndSurname;
    private TextView emailAddressOfUser;
    private Button button;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        button = root.findViewById(R.id.login_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                nameSurnameEditText = root.findViewById(R.id.nameSurnameEditText);
                emailAddressEditText = root.findViewById(R.id.emailAddressEditText);
                nameAndSurname = getActivity().findViewById(R.id.nav_view).findViewById(R.id.nameAndSurname);
                emailAddressOfUser = getActivity().findViewById(R.id.nav_view).findViewById(R.id.emailAddressOfUser);
                nameAndSurname.setText(nameSurnameEditText.getText());
                emailAddressOfUser.setText(emailAddressEditText.getText());

                Intent i = new Intent(getActivity(), Trips.class);
                startActivity(i);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}