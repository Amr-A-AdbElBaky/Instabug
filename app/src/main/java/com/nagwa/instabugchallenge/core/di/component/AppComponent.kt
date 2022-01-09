package com.nagwa.instabugchallenge.core.di.component

import android.app.Application
import com.nagwa.instabugchallenge.core.application.InstabugApplication
import com.nagwa.instabugchallenge.core.di.module.ActivityBuilder
import com.nagwa.instabugchallenge.core.di.module.AppModule
import com.nagwa.instabugchallenge.core.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class, ViewModelModule::class, ActivityBuilder::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: InstabugApplication)
}

