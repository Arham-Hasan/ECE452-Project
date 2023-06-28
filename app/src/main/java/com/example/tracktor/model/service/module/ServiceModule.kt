package com.example.tracktor.model.service.module

import com.example.tracktor.model.service.AccountService
import com.example.tracktor.model.service.implementation.AccountServiceImplementation
import com.example.tracktor.model.service.FarmStorageService
import com.example.tracktor.model.service.implementation.FarmStorageServiceImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ServiceModule {
    @Binds
    abstract fun provideAccountService(impl: AccountServiceImplementation): AccountService
    @Binds
    abstract fun provideFarmStorageService(impl: FarmStorageServiceImplementation): FarmStorageService
}