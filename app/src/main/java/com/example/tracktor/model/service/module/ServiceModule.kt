package com.example.tracktor.model.service.module

import com.example.tracktor.model.service.AccountService
import com.example.tracktor.model.service.implementation.AccountServiceImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImplementation): AccountService

}