package com.customdev.gameland.interfaces;

/**
 * The callbacks used to indicate the user profile image upload is complete.
 */
public interface OnUserProfileImageUploadListener {
    /**
     * Calls is case of succeed of image upload.
     */
    void onUploadSuccess();

    /**
     * Calls is case of failure of image upload.
     * @param e An exception returned from server.
     */
    void onUploadFail(Exception e);
}
