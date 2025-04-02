package com.bravedroid.momentor.data.di

import com.bravedroid.momentor.data.repository.FakeHomeRepository
import com.bravedroid.momentor.data.repository.FakePhotoRepository
import com.bravedroid.momentor.data.repository.FakeUserRepository
import com.bravedroid.momentor.data.repository.HomeRepository
import com.bravedroid.momentor.data.repository.PhotoRepository
import com.bravedroid.momentor.data.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DiModule {

    @Binds
    @Singleton
    fun bindUserRepository(
        fakeUserRepository: FakeUserRepository
    ): UserRepository

    @Binds
    @Singleton
    fun bindPhotoRepository(
        fakePhotoRepository: FakePhotoRepository
    ): PhotoRepository

    @Binds
    @Singleton
    fun bindHomeRepository(
        fakeHomeRepository: FakeHomeRepository
    ): HomeRepository
}
