package com.customdev.gameland.interfaces;

public interface OnLoginResultListener {
    void OnLoginSuccess();
    void OnLoginFail(Exception e);
}
