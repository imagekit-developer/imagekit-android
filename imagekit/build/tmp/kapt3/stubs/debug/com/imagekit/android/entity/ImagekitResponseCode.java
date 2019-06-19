package com.imagekit.android.entity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\t\b\u0086\u0001\u0018\u0000 \u000b2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u000bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\f"}, d2 = {"Lcom/imagekit/android/entity/ImagekitResponseCode;", "", "responseCode", "", "(Ljava/lang/String;II)V", "getResponseCode", "()I", "INVALID_FORM_PARAM", "INVALID_SIGNATURE", "INVALID_IMAGEKIT_ID", "SERVER_ERROR", "Companion", "imagekit_debug"})
public enum ImagekitResponseCode {
    /*public static final*/ INVALID_FORM_PARAM /* = new INVALID_FORM_PARAM(0) */,
    /*public static final*/ INVALID_SIGNATURE /* = new INVALID_SIGNATURE(0) */,
    /*public static final*/ INVALID_IMAGEKIT_ID /* = new INVALID_IMAGEKIT_ID(0) */,
    /*public static final*/ SERVER_ERROR /* = new SERVER_ERROR(0) */;
    private final int responseCode = 0;
    private static final java.util.Map<java.lang.Integer, com.imagekit.android.entity.ImagekitResponseCode> map = null;
    public static final com.imagekit.android.entity.ImagekitResponseCode.Companion Companion = null;
    
    public final int getResponseCode() {
        return 0;
    }
    
    ImagekitResponseCode(int responseCode) {
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u00062\u0006\u0010\b\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/imagekit/android/entity/ImagekitResponseCode$Companion;", "", "()V", "map", "", "", "Lcom/imagekit/android/entity/ImagekitResponseCode;", "fromInt", "type", "imagekit_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.Nullable()
        public final com.imagekit.android.entity.ImagekitResponseCode fromInt(int type) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}