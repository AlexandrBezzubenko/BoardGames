package com.customdev.gameland.dialogs;

import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.customdev.gameland.R;
import com.customdev.gameland.UserProfileEditActivity.OnImageChosenListener;
import com.customdev.gameland.utils.ScalingUtilities;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


public class ChangeImageDialogFragment extends DialogFragment implements View.OnClickListener {

    private final int PICK_IMAGE_FROM_GALLERY = 100;
    private final int PICK_IMAGE_FROM_CAMERA = 200;

    private final String TMP_IMAGE_NAME = "tmp_image";

    private static OnImageChosenListener mListener;
    @BindView(R.id.btn_pick_from_gallery) Button mPickFromGallery;
    @BindView(R.id.btn_pick_from_camera) Button mPickFromCamera;

    @BindView(R.id.btn_reset) Button mReset;

    public static ChangeImageDialogFragment newInstance(OnImageChosenListener listener) {
        ChangeImageDialogFragment dialog = new ChangeImageDialogFragment();
        mListener = listener;
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle(getString(R.string.change_image_dialog_title));
        View v = inflater.inflate(R.layout.dialog_fragment_change_image, container, false);
        ButterKnife.bind(this, v);
        setClickListeners();
        return v;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (resultCode == RESULT_OK) {
            File newImage = null;
            if (requestCode == PICK_IMAGE_FROM_GALLERY) {
                newImage = onSelectFromGalleryResult(imageReturnedIntent);
            } else if (requestCode == PICK_IMAGE_FROM_CAMERA) {
                newImage = onCaptureImageResult(imageReturnedIntent);
            }
            mListener.onImageChosenSuccess(newImage);
        } else {
            mListener.onImageChosenFailure();
        }
        this.dismiss();
    }

    private void setClickListeners() {
        mPickFromGallery.setOnClickListener(this);
        mPickFromCamera.setOnClickListener(this);
        mReset.setOnClickListener(this);
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

    private File onSelectFromGalleryResult(Intent data) {
        Bitmap bitmap = null;
        if (data != null) {
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmapToImageFile(bitmap);
    }

    private File onCaptureImageResult(Intent data) {
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        bitmapToImageFile(bitmap);

        return bitmapToImageFile(bitmap);
    }

    private File bitmapToImageFile(Bitmap bitmap) {
        int dimension = 300;
        Bitmap scaledBitmap = ScalingUtilities.createScaledBitmap(bitmap, dimension, dimension, ScalingUtilities.ScalingLogic.CROP);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String destinationFilename = getActivity().getCacheDir().getPath() + File.separatorChar + TMP_IMAGE_NAME;
        File destination = new File(destinationFilename);
        FileOutputStream fo;
        try {
            if (destination.createNewFile()) {
                fo = new FileOutputStream(destination);
                fo.write(bytes.toByteArray());
                fo.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return destination;
    }
}
