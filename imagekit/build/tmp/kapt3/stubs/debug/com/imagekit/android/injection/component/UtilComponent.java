package com.imagekit.android.injection.component;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\u0006"}, d2 = {"Lcom/imagekit/android/injection/component/UtilComponent;", "", "inject", "", "app", "Lcom/imagekit/android/ImageKit;", "imagekit_debug"})
@javax.inject.Singleton()
@dagger.Component(modules = {com.imagekit.android.injection.module.ContextModule.class})
public abstract interface UtilComponent {
    
    public abstract void inject(@org.jetbrains.annotations.NotNull()
    com.imagekit.android.ImageKit app);
}