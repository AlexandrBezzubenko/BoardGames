package com.customdev.gameland.interfaces;

/**
 * The callbacks used to indicate the user profile image download is complete.
 */
public interface OnUserProfileImageDownloadListener {
    /**
     * Calls is case of succeed of image download.
     */
    void onDownloadSuccess();

    /**
     * Calls is case of failure of image download.
     * @param e An exception returned from server.
     */
    void onDownloadFail(Exception e);
}
