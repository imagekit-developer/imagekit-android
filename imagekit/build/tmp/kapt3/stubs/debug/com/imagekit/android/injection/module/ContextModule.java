package com.imagekit.android.injection.module;

import java.lang.System;

@kotlin.Suppress(names = {"unused"})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0007J\b\u0010\t\u001a\u00020\u0003H\u0007R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\n"}, d2 = {"Lcom/imagekit/android/injection/module/ContextModule;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "getContext", "()Landroid/content/Context;", "provideApplication", "Landroid/app/Application;", "provideContext", "imagekit_debug"})
@dagger.Module()
public final class ContextModule {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    
    /**
     * Provides the Application Context
     * @param context Context in which the application is running
     * @return the Application Context to be provided
     */
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final android.app.Application provideApplication() {
        return null;
    }
    
    /**
     * Provides the Context
     * @return the Context to be provided
     */
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final android.content.Context provideContext() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getContext() {
        return null;
    }
    
    public ContextModule(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
}