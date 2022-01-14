package com.freekickr.roombahighscore.hilt

import com.freekickr.roombahighscore.domain.SharedPreferencesHandler
import com.freekickr.roombahighscore.domain.SharedPreferencesHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class FragmentModule {

    @Binds
    abstract fun bindSharedPreferences(sharedPreferencesHandlerImpl: SharedPreferencesHandlerImpl): SharedPreferencesHandler

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule{

    @Binds
    abstract fun bindSharedPreferences(sharedPreferencesHandlerImpl: SharedPreferencesHandlerImpl): SharedPreferencesHandler

}