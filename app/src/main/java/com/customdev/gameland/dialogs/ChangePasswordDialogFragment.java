package com.customdev.gameland.dialogs;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.customdev.gameland.R;
import com.customdev.gameland.utils.AuthenticateManager;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChangePasswordDialogFragment extends DialogFragment implements View.OnClickListener {

    public interface OnPasswordChangeListener {
        void onSuccess();
        void onFailure(Exception e);
    }

    @BindView(R.id.et_new_password) EditText mNewPasswordEditText;
    @BindView(R.id.et_new_password_confirm) EditText mNewPasswordConfirmEditText;
    @BindView(R.id.btn_confirm) Button mConfirmButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_fragment_change_password, null);
        ButterKnife.bind(this, v);
        mConfirmButton.setOnClickListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                changePassword();
                break;
        }
    }

    private void changePassword() {
        String newPassword = mNewPasswordEditText.getText().toString();
        AuthenticateManager.changeUserPassword(newPassword, new OnPasswordChangeListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(getActivity(), "password changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getActivity(), "password doesn't changed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
