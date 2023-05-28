package com.vandanov.aids03.data.retrofit

import retrofit2.http.*

interface ApiService {

//    @GET("bd/hs/analyzes/get/")
//    suspend fun getRezAnalyzes(): Response<RezAnalyzes>

//    @GET("kopia/hs/mobile/user_verification/{epid_n}")
//    suspend fun userVerification(@Path("epid_n") epidN : String): String

    @GET("mis/hs/mobile/user_verification")
    suspend fun userInfoVerificationAPI(
        @Query("epid_n") epidN : String,
        @Query("date_birth") dateBirth : String
    ): Int

    @GET("mis/hs/mobile/uid_save")
    suspend fun uidSaveAPI(
        @Query("epid_n") epidN : String,
        @Query("date_birth") dateBirth : String,
        @Query("uid") uid : String
    ): Int

}