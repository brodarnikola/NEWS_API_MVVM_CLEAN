package com.vjezba.androidjetpacknews.di

import androidx.lifecycle.ViewModel
import com.vjezba.androidjetpacknews.viewmodels.*

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {


    // programing languages viewmodels
    @Binds
    @IntoMap
    @ViewModelKey(LanguagesActivityViewModel::class)
    abstract fun bindLanguagesActivityViewModel(viewModel: LanguagesActivityViewModel): ViewModel

    // rxjava2 example with viewmodels
    @Binds
    @IntoMap
    @ViewModelKey(RepositoriesRxJava2ViewModel::class)
    abstract fun bindRepositoriesRxJava2ViewModel(viewModel: RepositoriesRxJava2ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RxJava2FlowableToLiveDataViewModel::class)
    abstract fun bindRxJava2FlowableToLiveDataViewModel(viewModel: RxJava2FlowableToLiveDataViewModel): ViewModel

}
