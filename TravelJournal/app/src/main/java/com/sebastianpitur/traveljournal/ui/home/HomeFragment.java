package com.sebastianpitur.traveljournal.ui.home;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.AccountPicker;
import com.sebastianpitur.traveljournal.R;
import com.sebastianpitur.traveljournal.Trips;
import com.sebastianpitur.traveljournal.databinding.FragmentHomeBinding;

import static androidx.databinding.DataBindingUtil.setContentView;

import java.util.Arrays;
import java.util.Objects;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Account selectedAccount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        AccountManager am = AccountManager.get(getContext());



        Button button = root.findViewById(R.id.login_button);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                View navView = requireActivity().findViewById(R.id.nav_view);
                TextView nameAndSurname = navView.findViewById(R.id.nameAndSurname);
                TextView emailAddressOfUser = navView.findViewById(R.id.emailAddressOfUser);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Intent intent = AccountManager.newChooseAccountIntent(null, null, new String[]{"com.google"}, null, null, null,
                            null);


                    startActivityForResult(intent, 1);
                } else {
                    Account[] accounts = AccountManager.get(requireContext()).getAccountsByType("com.google");
                    ListView accountListView = root.findViewById(R.id.account_list);
                    AccountAdapter accountAdapter = new AccountAdapter(requireContext(), accounts);
                    accountListView.setAdapter(accountAdapter);
                    accountListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectedAccount = accountAdapter.getItem(position);
                            // Perform any additional actions with the selected account if needed
                        }
                    });
                }
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {

                String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                String accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
                selectedAccount = new Account(accountName, accountType);
                Intent i = new Intent(getActivity(), Trips.class);
                i.putExtra("account", selectedAccount.name);
                startActivity(i);
            } else if (resultCode == RESULT_CANCELED) {
                Log.e("", "error selecting the account");
            }
        }
    }


    public static class AccountAdapter extends ArrayAdapter<Account> {

        private final LayoutInflater inflater;

        public AccountAdapter(Context context, Account[] accounts) {
            super(context, R.layout.fragment_home, accounts);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.fragment_home, parent, false);
            }

            TextView accountNameTextView = view.findViewById(R.id.account_list);
            Account account = getItem(position);
            accountNameTextView.setText(account.name);

            return view;
        }
    }

}