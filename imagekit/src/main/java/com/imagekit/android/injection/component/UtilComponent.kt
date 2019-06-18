package com.imagekit.android.injection.component

import com.imagekit.android.ImageKit
import com.imagekit.android.injection.module.ContextModule
import dagger.Component
import javax.inject.Singleton


@Component(modules = [(ContextModule::class)])
@Singleton
interface UtilComponent {
    fun inject(app: ImageKit)
}