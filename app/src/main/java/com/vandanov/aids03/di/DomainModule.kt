package com.vandanov.aids03.di

import com.vandanov.aids03.data.auth.AuthRepositoryImpl
import com.vandanov.aids03.domain.auth.repository.AuthRepository
import com.vandanov.aids03.domain.auth.usecase.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

//    @Binds
//    abstract fun bindSignUpUseCase(
//        authRepository: AuthRepository
//    ): SignUpUseCase
//
//    @Binds
//    abstract fun bindSignInEmailUseCase(
//        authRepository: AuthRepository
//    ): SignInEmailUseCase
//
//    @Binds
//    abstract fun bindOtpSignUpUseCase(
//        authRepository: AuthRepository
//    ): OtpSignUpUseCase

    @Provides
    fun provideSignUpUseCase(authRepository: AuthRepository): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

    @Provides
    fun provideSignInUseCase(authRepository: AuthRepository): SignInUseCase {
        return SignInUseCase(authRepository)
    }

    @Provides
    fun provideOtpSignUpUseCase(authRepository: AuthRepository): OtpSignUpUseCase {
        return OtpSignUpUseCase(authRepository)
    }

    @Provides
    fun provideInitFirebaseUseCase(authRepository: AuthRepository): InitFirebaseUseCase {
        return InitFirebaseUseCase(authRepository)
    }

    @Provides
    fun provideLogoutUseCase(authRepository: AuthRepository): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }

}