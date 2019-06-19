package com.imagekit.android.util;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000b\u001a\n \r*\u0004\u0018\u00010\f0\fJ\u000e\u0010\u000e\u001a\n \r*\u0004\u0018\u00010\u00060\u0006J\u000e\u0010\u000f\u001a\n \r*\u0004\u0018\u00010\u00060\u0006J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0006J\u000e\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/imagekit/android/util/SharedPrefUtil;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "KEY_CLIENT_PUBLIC_KEY", "", "KEY_IMAGEKIT_ID_KEY", "SHARED_PREF_FILENAME", "mPref", "Landroid/content/SharedPreferences;", "clear", "Landroid/content/SharedPreferences$Editor;", "kotlin.jvm.PlatformType", "getClientPublicKey", "getImageKitId", "setClientPublicKey", "", "clientPublicKey", "setImageKitId", "imageKitId", "imagekit_release"})
public final class SharedPrefUtil {
    private final java.lang.String SHARED_PREF_FILENAME = "SharedPref File";
    private final java.lang.String KEY_CLIENT_PUBLIC_KEY = "Client Public Key";
    private final java.lang.String KEY_IMAGEKIT_ID_KEY = "ImageKit Id";
    private android.content.SharedPreferences mPref;
    
    public final void setClientPublicKey(@org.jetbrains.annotations.NotNull()
    java.lang.String clientPublicKey) {
    }
    
    public final java.lang.String getClientPublicKey() {
        return null;
    }
    
    public final void setImageKitId(@org.jetbrains.annotations.NotNull()
    java.lang.String imageKitId) {
    }
    
    public final java.lang.String getImageKitId() {
        return null;
    }
    
    public final android.content.SharedPreferences.Editor clear() {
        return null;
    }
    
    @javax.inject.Inject()
    public SharedPrefUtil(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
}