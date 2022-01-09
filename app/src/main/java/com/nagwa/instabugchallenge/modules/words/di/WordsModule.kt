package com.nagwa.instabugchallenge.modules.words.di

import androidx.lifecycle.ViewModel
import com.nagwa.instabugchallenge.core.data.InstaBugDatabase
import com.nagwa.instabugchallenge.core.di.module.ViewModelKey
import com.nagwa.instabugchallenge.modules.words.data.repository.WordsRepositoryImpl
import com.nagwa.instabugchallenge.modules.words.data.source.local.WordsDao
import com.nagwa.instabugchallenge.modules.words.data.source.remote.WordsApi
import com.nagwa.instabugchallenge.modules.words.data.source.remote.WordsDs
import com.nagwa.instabugchallenge.modules.words.domain.repository.WordsRepository
import com.nagwa.instabugchallenge.modules.words.presentation.view.fragment.HomeFragment
import com.nagwa.instabugchallenge.modules.words.presentation.view.fragment.SearchFragment
import com.nagwa.instabugchallenge.modules.words.presentation.viewmodel.WordsViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import retrofit2.Retrofit


@Module
class WordsDataModule {
    @Provides
    fun provideWordsApi(retrofit: Retrofit)
            : WordsApi = retrofit.create(WordsApi::class.java)


    @Provides
    fun provideWordsDataSource(WordsApi: WordsApi): WordsDs = WordsDs(WordsApi)

    @Provides
    fun provideWordsDao(db: InstaBugDatabase): WordsDao = WordsDao(db)

    @Provides
    fun provideWordsRepositoryImpl(wordsDs: WordsDs, wordsDao: WordsDao): WordsRepository =
        WordsRepositoryImpl(wordsDs,wordsDao )


}
@Module
abstract class WordsPresentationModule {
    @Binds
    @IntoMap
    @ViewModelKey(WordsViewModel::class)
    internal abstract fun wordsViewModel(viewModel: WordsViewModel): ViewModel

}
@Module
abstract class WordsFragmentModule{
    @ContributesAndroidInjector
    internal abstract fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun searchFragment(): SearchFragment

}
