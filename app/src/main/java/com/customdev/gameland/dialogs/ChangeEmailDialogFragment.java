package com.customdev.gameland.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.customdev.gameland.R;
import com.customdev.gameland.utils.AuthenticateManager;
import com.customdev.gameland.utils.DatabaseManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChangeEmailDialogFragment extends DialogFragment implements View.OnClickListener{

    public interface OnEmailChangeListener {
        void onSuccess();
        void onFailure(Exception e);
    }

    @BindView(R.id.et_change_email) EditText mNewEmailEditText;
    @BindView(R.id.btn_confirm) EditText mConfirmButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_change_email, null);
        ButterKnife.bind(this, v);
        mConfirmButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                changeEmail();
                break;
        }
    }

    private void changeEmail() {
        String newEmail = mNewEmailEditText.getText().toString();
        AuthenticateManager.changeUserEmail(newEmail, new OnEmailChangeListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "email changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getActivity(), "email doesn't changed" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
