package com.imagekit.android.data;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J \u0010\t\u001a\u00020\n2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\rH\u0002JY\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\b2\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001bH\u0007\u00a2\u0006\u0002\u0010\u001cJY\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\b2\u0006\u0010\u001e\u001a\u00020\r2\u0006\u0010\u001a\u001a\u00020\u001bH\u0007\u00a2\u0006\u0002\u0010\u001fJY\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\u0014\u001a\u00020\u00152\u000e\u0010\u0016\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\b2\u0006\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001bH\u0007\u00a2\u0006\u0002\u0010\u001cR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/imagekit/android/data/Repository;", "", "context", "Landroid/content/Context;", "sharedPrefUtil", "Lcom/imagekit/android/util/SharedPrefUtil;", "(Landroid/content/Context;Lcom/imagekit/android/util/SharedPrefUtil;)V", "uploadUrl", "", "bitmapToFile", "Ljava/io/File;", "filename", "bitmap", "Landroid/graphics/Bitmap;", "uploadFile", "", "fileName", "signature", "timestamp", "", "useUniqueFilename", "", "tags", "", "folder", "file", "imageKitCallback", "Lcom/imagekit/android/ImageKitCallback;", "(Ljava/lang/String;Ljava/lang/String;JZ[Ljava/lang/String;Ljava/lang/String;Ljava/io/File;Lcom/imagekit/android/ImageKitCallback;)V", "uploadImage", "image", "(Ljava/lang/String;Ljava/lang/String;JZ[Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;Lcom/imagekit/android/ImageKitCallback;)V", "imagekit_release"})
public final class Repository {
    private final java.lang.String uploadUrl = null;
    private final android.content.Context context = null;
    private final com.imagekit.android.util.SharedPrefUtil sharedPrefUtil = null;
    
    @android.annotation.SuppressLint(value = {"CheckResult"})
    public final void uploadImage(@org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, long timestamp, boolean useUniqueFilename, @org.jetbrains.annotations.Nullable()
    java.lang.String[] tags, @org.jetbrains.annotations.Nullable()
    java.lang.String folder, @org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap image, @org.jetbrains.annotations.NotNull()
    com.imagekit.android.ImageKitCallback imageKitCallback) {
    }
    
    @android.annotation.SuppressLint(value = {"CheckResult"})
    public final void uploadImage(@org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, long timestamp, boolean useUniqueFilename, @org.jetbrains.annotations.Nullable()
    java.lang.String[] tags, @org.jetbrains.annotations.Nullable()
    java.lang.String folder, @org.jetbrains.annotations.NotNull()
    java.io.File image, @org.jetbrains.annotations.NotNull()
    com.imagekit.android.ImageKitCallback imageKitCallback) {
    }
    
    @android.annotation.SuppressLint(value = {"CheckResult"})
    public final void uploadFile(@org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, long timestamp, boolean useUniqueFilename, @org.jetbrains.annotations.Nullable()
    java.lang.String[] tags, @org.jetbrains.annotations.Nullable()
    java.lang.String folder, @org.jetbrains.annotations.NotNull()
    java.io.File file, @org.jetbrains.annotations.NotNull()
    com.imagekit.android.ImageKitCallback imageKitCallback) {
    }
    
    private final java.io.File bitmapToFile(android.content.Context context, java.lang.String filename, android.graphics.Bitmap bitmap) throws java.io.IOException {
        return null;
    }
    
    @javax.inject.Inject()
    public Repository(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.imagekit.android.util.SharedPrefUtil sharedPrefUtil) {
        super();
    }
}