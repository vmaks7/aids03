package com.vandanov.aids03.di

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vandanov.aids03.data.auth.ActivityRequired
import com.vandanov.aids03.data.auth.AuthRepositoryImpl
import com.vandanov.aids03.domain.auth.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SourcesModule {

    @Provides
//    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository {
        return authRepositoryImpl
    }

    @Provides
    @IntoSet
    fun bindActivityRequired(authRepositoryImpl: AuthRepositoryImpl): ActivityRequired {
        return authRepositoryImpl
    }

}


//@Module
//@InstallIn(SingletonComponent::class)
//abstract class DataModule {
//
//    @Binds
//    @Singleton
//    abstract fun bindAuthRepositoryImpl(
//        authRepositoryImpl: AuthRepositoryImpl
//    ): AuthRepository
//
//}

//@Module
//@InstallIn(ActivityComponent::class)
//object ActivityModule {
//
//    @Provides
//    fun provideAuthRepositoryImpl(activity: FragmentActivity): AuthRepositoryImpl {
//        return AuthRepositoryImpl(activity)
//    }
//
//}



//@Module
//@InstallIn(ActivityComponent::class)
//class DataModule2 {
//
//    @Provides
////    @Singleton
//    fun provideAuthRepositoryImpl2(@ActivityContext activity: FragmentActivity): AuthRepository {
//        return AuthRepositoryImpl(activity)
//    }
//
//}

//@Module
//@InstallIn(ActivityComponent::class)
//abstract class DataModule {
//
//    @Binds
//    abstract fun bindAuthRepositoryImpl(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
//}