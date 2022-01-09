package com.nagwa.instabugchallenge.core.di.module

import com.nagwa.instabugchallenge.modules.words.presentation.view.activity.MainActivity
import com.nagwa.instabugchallenge.modules.words.di.WordsDataModule
import com.nagwa.instabugchallenge.modules.words.di.WordsFragmentModule
import com.nagwa.instabugchallenge.modules.words.di.WordsPresentationModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [WordsDataModule::class, WordsPresentationModule::class,
        WordsFragmentModule::class])
    internal abstract fun mainActivity(): MainActivity

}


