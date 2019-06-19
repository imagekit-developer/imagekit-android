package com.imagekit.android;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 (2\u00020\u0001:\u0001(B\u001f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0007JW\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\u000e\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"\u00a2\u0006\u0002\u0010#JW\u0010$\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\u000e\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u00052\u0006\u0010%\u001a\u00020&2\u0006\u0010!\u001a\u00020\"\u00a2\u0006\u0002\u0010\'JW\u0010$\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u0006\u0010\u0018\u001a\u00020\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u001b2\u000e\u0010\u001c\u001a\n\u0012\u0004\u0012\u00020\u0005\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u00052\u0006\u0010%\u001a\u00020 2\u0006\u0010!\u001a\u00020\"\u00a2\u0006\u0002\u0010#R\u001e\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001e\u0010\u000e\u001a\u00020\u000f8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013\u00a8\u0006)"}, d2 = {"Lcom/imagekit/android/ImageKit;", "", "context", "Landroid/content/Context;", "clientPublicKey", "", "imageKitId", "(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)V", "mRepository", "Lcom/imagekit/android/data/Repository;", "getMRepository", "()Lcom/imagekit/android/data/Repository;", "setMRepository", "(Lcom/imagekit/android/data/Repository;)V", "mSharedPrefUtil", "Lcom/imagekit/android/util/SharedPrefUtil;", "getMSharedPrefUtil", "()Lcom/imagekit/android/util/SharedPrefUtil;", "setMSharedPrefUtil", "(Lcom/imagekit/android/util/SharedPrefUtil;)V", "uploadFile", "", "fileName", "signature", "timestamp", "", "useUniqueFilename", "", "tags", "", "folder", "file", "Ljava/io/File;", "imageKitCallback", "Lcom/imagekit/android/ImageKitCallback;", "(Ljava/lang/String;Ljava/lang/String;JZ[Ljava/lang/String;Ljava/lang/String;Ljava/io/File;Lcom/imagekit/android/ImageKitCallback;)V", "uploadImage", "image", "Landroid/graphics/Bitmap;", "(Ljava/lang/String;Ljava/lang/String;JZ[Ljava/lang/String;Ljava/lang/String;Landroid/graphics/Bitmap;Lcom/imagekit/android/ImageKitCallback;)V", "Companion", "imagekit_release"})
public final class ImageKit {
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public com.imagekit.android.util.SharedPrefUtil mSharedPrefUtil;
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Inject()
    public com.imagekit.android.data.Repository mRepository;
    private static com.imagekit.android.ImageKit imageKit;
    private static com.imagekit.android.injection.component.UtilComponent appComponent;
    public static final com.imagekit.android.ImageKit.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.util.SharedPrefUtil getMSharedPrefUtil() {
        return null;
    }
    
    public final void setMSharedPrefUtil(@org.jetbrains.annotations.NotNull()
    com.imagekit.android.util.SharedPrefUtil p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.imagekit.android.data.Repository getMRepository() {
        return null;
    }
    
    public final void setMRepository(@org.jetbrains.annotations.NotNull()
    com.imagekit.android.data.Repository p0) {
    }
    
    public final void uploadImage(@org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, long timestamp, boolean useUniqueFilename, @org.jetbrains.annotations.Nullable()
    java.lang.String[] tags, @org.jetbrains.annotations.Nullable()
    java.lang.String folder, @org.jetbrains.annotations.NotNull()
    android.graphics.Bitmap image, @org.jetbrains.annotations.NotNull()
    com.imagekit.android.ImageKitCallback imageKitCallback) {
    }
    
    public final void uploadImage(@org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, long timestamp, boolean useUniqueFilename, @org.jetbrains.annotations.Nullable()
    java.lang.String[] tags, @org.jetbrains.annotations.Nullable()
    java.lang.String folder, @org.jetbrains.annotations.NotNull()
    java.io.File image, @org.jetbrains.annotations.NotNull()
    com.imagekit.android.ImageKitCallback imageKitCallback) {
    }
    
    public final void uploadFile(@org.jetbrains.annotations.NotNull()
    java.lang.String fileName, @org.jetbrains.annotations.NotNull()
    java.lang.String signature, long timestamp, boolean useUniqueFilename, @org.jetbrains.annotations.Nullable()
    java.lang.String[] tags, @org.jetbrains.annotations.Nullable()
    java.lang.String folder, @org.jetbrains.annotations.NotNull()
    java.io.File file, @org.jetbrains.annotations.NotNull()
    com.imagekit.android.ImageKitCallback imageKitCallback) {
    }
    
    private ImageKit(android.content.Context context, java.lang.String clientPublicKey, java.lang.String imageKitId) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\u0006J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/imagekit/android/ImageKit$Companion;", "", "()V", "appComponent", "Lcom/imagekit/android/injection/component/UtilComponent;", "imageKit", "Lcom/imagekit/android/ImageKit;", "getInstance", "init", "", "context", "Landroid/content/Context;", "clientPublicKey", "", "imageKitId", "imagekit_release"})
    public static final class Companion {
        
        public final void init(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        java.lang.String clientPublicKey, @org.jetbrains.annotations.NotNull()
        java.lang.String imageKitId) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.imagekit.android.ImageKit getInstance() {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}