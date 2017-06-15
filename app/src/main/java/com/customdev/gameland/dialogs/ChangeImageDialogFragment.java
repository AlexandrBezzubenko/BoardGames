package com.customdev.gameland.dialogs;

import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.customdev.gameland.R;
import com.customdev.gameland.UserProfileEditActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class ChangeImageDialogFragment extends DialogFragment implements View.OnClickListener {

    private final int PICK_IMAGE_FROM_GALLERY = 100;
    private final int PICK_IMAGE_FROM_CAMERA = 200;

    @BindView(R.id.btn_pick_from_gallery) Button mPickFromGallery;
    @BindView(R.id.btn_pick_from_camera) Button mPickFromCamera;
    @BindView(R.id.btn_reset) Button mReset;

    private static UserProfileEditActivity.OnImageChosenListener mListener;

    public static ChangeImageDialogFragment newInstance(UserProfileEditActivity.OnImageChosenListener listener) {
        ChangeImageDialogFragment dialog = new ChangeImageDialogFragment();
        mListener = listener;
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Change image");
        View v = inflater.inflate(R.layout.dialog_fragment_change_image, null);
        ButterKnife.bind(this, v);
        setClickListeners();
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//        switch(requestCode) {
//            case PICK_IMAGE_FROM_GALLERY:
                if (resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    mListener.onImageChosen(selectedImage);
                } else {
                    mListener.onError();
                }

//                break;
//            case PICK_IMAGE_FROM_CAMERA:
//                if(resultCode == RESULT_OK){
//                    Uri selectedImage = imageReturnedIntent.getData();
//
//                }
//                break;
//        }
        this.dismiss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pick_from_gallery:
                startGalleryActivity();
                break;
            case R.id.btn_pick_from_camera:
                startCameraActivity();
                break;
            case R.id.btn_reset:
                setDefaultImage();
                break;
        }
    }

    private void startGalleryActivity() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_FROM_GALLERY);
    }

    private void startCameraActivity() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, PICK_IMAGE_FROM_CAMERA);
    }

    private void setDefaultImage() {

    }

    private void setClickListeners() {
        mPickFromGallery.setOnClickListener(this);
        mPickFromCamera.setOnClickListener(this);
        mReset.setOnClickListener(this);
    }
}
