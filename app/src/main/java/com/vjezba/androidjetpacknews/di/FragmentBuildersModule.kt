package com.vjezba.androidjetpacknews.di


import com.vjezba.androidjetpacknews.ui.fragments.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {


    @ContributesAndroidInjector
    abstract fun contributeNewsDetailsFragment(): IntroViewPagerFragment


    @ContributesAndroidInjector
    abstract fun contributeRepositoriesSearchFragment(): RepositoriesRxJava2Fragment


    @ContributesAndroidInjector
    abstract fun contributeRxJava2FlowableToLiveDataFragment(): RxJava2FlowableToLiveDataFragment

}
