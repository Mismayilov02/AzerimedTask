package com.mismayilov.data.modules

import com.mismayilov.data.repository.NetworkRepositoryImpl
import com.mismayilov.domain.repositories.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkRepositoryModule {
    @Binds
    fun bindNetworkRepository(networkRepositoryImpl: NetworkRepositoryImpl): NetworkRepository
}