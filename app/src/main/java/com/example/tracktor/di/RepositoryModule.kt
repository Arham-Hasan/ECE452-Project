package com.example.tracktor.di

import com.example.tracktor.data.repository.AuthRepository
import com.example.tracktor.data.repository.AuthRepositoryImpl
import com.example.tracktor.data.repository.FarmManagerRepository
import com.example.tracktor.data.repository.FarmManagerRepositoryImpl
import com.example.tracktor.data.repository.FarmRepository
import com.example.tracktor.data.repository.FarmRepositoryImpl
import com.example.tracktor.data.repository.FarmUserRepository
import com.example.tracktor.data.repository.FarmUserRepositoryImpl
import com.example.tracktor.data.repository.InstagramPostRepository
import com.example.tracktor.data.repository.InstagramPostRepositoryImpl
import com.example.tracktor.data.repository.ImageStorageRepository
import com.example.tracktor.data.repository.ImageStorageRepositoryImpl
import com.example.tracktor.data.repository.InventoryRepository
import com.example.tracktor.data.repository.InventoryRepositoryImpl
import com.example.tracktor.data.repository.UserManagerRepository
import com.example.tracktor.data.repository.UserManagerRepositoryImpl
import com.example.tracktor.data.repository.UserRepository
import com.example.tracktor.data.repository.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth):AuthRepository = AuthRepositoryImpl(auth)

    @Provides
    @Singleton
    fun provideFarmRepository(firestore: FirebaseFirestore):FarmRepository = FarmRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideFarmUserRepository(firestore: FirebaseFirestore): FarmUserRepository = FarmUserRepositoryImpl(firestore)

    @Provides
    @Singleton
    fun provideInventoryRepository(firestore: FirebaseFirestore,): InventoryRepository = InventoryRepositoryImpl(firestore)


    @Provides
    @Singleton
    fun provideFarmManagerRepository(
        authRepository: AuthRepository,
        farmRepository: FarmRepository,
        farmUserRepository: FarmUserRepository,
        inventoryRepository: InventoryRepository,
        imageStorageRepository: ImageStorageRepository,
    ):FarmManagerRepository = FarmManagerRepositoryImpl(authRepository,farmRepository, farmUserRepository, inventoryRepository, imageStorageRepository)

    @Provides
    @Singleton
    fun provideUserRepository(
        firestore: FirebaseFirestore
    ):UserRepository = UserRepositoryImpl(firestore)
    @Provides
    @Singleton
    fun provideUserManagerRepository(
        authRepository: AuthRepository,
        userRepository: UserRepository
    ):UserManagerRepository = UserManagerRepositoryImpl(userRepository, authRepository)

    @Provides
    @Singleton
    fun provideInstagramRepository(
        firestore: FirebaseFirestore
    ):InstagramPostRepository = InstagramPostRepositoryImpl(firestore)
    @Provides
    @Singleton
    fun provideImageStoreRepository(storage: FirebaseStorage): ImageStorageRepository = ImageStorageRepositoryImpl(storage)
}