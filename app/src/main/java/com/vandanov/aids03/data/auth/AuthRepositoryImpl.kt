package com.vandanov.aids03.data.auth

import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.vandanov.aids03.data.retrofit.RetrofitInstance
import com.vandanov.aids03.domain.auth.entity.RegistrationMethod
import com.vandanov.aids03.domain.auth.entity.SignUpItem
import com.vandanov.aids03.domain.auth.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ConnectException
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor() : AuthRepository, ActivityRequired {

    private var isActivityStarted = false
    private var activityMain: FragmentActivity? = null

    private lateinit var verificationID: String

    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override suspend fun signIn(
        email: String,
        password: String,
        registrationMethod: RegistrationMethod,
        message: (String) -> Unit,
        navigate: (Boolean) -> Unit
    ) {
        if (registrationMethod == RegistrationMethod.EMAIL) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (it.result.user?.isEmailVerified == true) {
                            //навигация
                            navigate(true)
                        } else {
                            message("Подтвердите email")
                        }
                    } else {
                        if (it.exception is FirebaseAuthInvalidCredentialsException) {
                            val exception =
                                it.exception as FirebaseAuthInvalidCredentialsException
                            if (exception.errorCode == "ERROR_INVALID_EMAIL") {
                                message("Email недействителен")
                            } else if (exception.errorCode == "ERROR_WRONG_PASSWORD") {
                                message("Email или пароль недействительны")
                            }
                        } else if (it.exception is FirebaseAuthInvalidUserException) {
                            val exception =
                                it.exception as FirebaseAuthInvalidUserException
                            if (exception.errorCode == "ERROR_USER_NOT_FOUND") {
                                message("Пользователь не найден")
                            }
                        } else if (it.exception is FirebaseNetworkException) {
                            message("Проверьте подключение к интернету")
                        } else {
                            message(it.exception.toString())
                        }
                    }
                }

        } else if (registrationMethod == RegistrationMethod.PHONE) {

        }
    }

    override suspend fun signUp(
        signUpData: SignUpItem,
        registrationMethod: RegistrationMethod,
        message: (String) -> Unit,
        otpCodeSend: (Boolean) -> Unit,
        navigate: (Boolean) -> Unit
    ) {
        Log.d("MyLog", "signUp $activityMain")
        Log.d("MyLog", "AuthRepositoryImpl $AuthRepositoryImpl")

        when (userInfoVerification(signUpData.epidN, signUpData.dateBirth)) {

            CONNECT_NOT_FOUND -> {
                message("Отсутствует соединение с сервером!")
            }
            USER_NOT_FOUND -> {
                message("Пациент не найден, проверьте введенный Эпид№ и дату рождения!")
            }
            UID_FOUND -> {
                message("Учетная запись для пациента с введенным Эпид№ была создана ранее!")
            }
            UID_NOT_FOUND -> {
//                firebaseAuth.getFirebaseAuthSettings().setAppVerificationDisabledForTesting(true);

                if (registrationMethod == RegistrationMethod.EMAIL) {
                    firebaseAuth.createUserWithEmailAndPassword(
                        signUpData.email,
                        signUpData.password
                    )
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful) {
                                //добавляем токен в 1С
                                val user = task.result?.user!!
//                          val uid = firebaseAuth.currentUser?.uid.toString()
                                val uid = user.uid

                                CoroutineScope(Dispatchers.IO).launch {
                                    val uidInfo =
                                        uidSave(signUpData.epidN, signUpData.dateBirth, uid)
                                    if (uidInfo == UID_SAVE) {
                                        user.sendEmailVerification()
                                        withContext(Dispatchers.Main) {
                                            message("На электронную почту выслано письмо для подтверждения аккаунта!")
                                            navigate(true)
                                        }
                                    } else {
                                        user.delete()
                                        withContext(Dispatchers.Main) {
                                            message("Не удалось зарегистрироваться, попробуйте позже!")
                                        }
                                    }
                                }

                            } else {

                                if (task.exception is FirebaseAuthUserCollisionException) {
                                    val exception =
                                        task.exception as FirebaseAuthUserCollisionException
                                    if (exception.errorCode == "ERROR_EMAIL_ALREADY_IN_USE") {
                                        message("Электронная почта уже используется!")
                                    }
                                } else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                    val exception =
                                        task.exception as FirebaseAuthInvalidCredentialsException
                                    if (exception.errorCode == "ERROR_INVALID_EMAIL") {
                                        message("Недействительная электронная почта!")
                                    }
                                }
                                if (task.exception is FirebaseAuthWeakPasswordException) {
                                    val exception =
                                        task.exception as FirebaseAuthWeakPasswordException
                                    if (exception.errorCode == "ERROR_WEAK_PASSWORD") {
                                        message("Введен слабый пароль!")
                                    }
                                }
                            }
                        }

                } else if (registrationMethod == RegistrationMethod.PHONE) {
                    Log.d("MyLog", "Auth PHONE")

                    if (isActivityStarted) {

                        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                            .setPhoneNumber(signUpData.phoneNumber)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(activityMain!!)                 // Activity (for callback binding)
                            .setCallbacks(object :
                                PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//            signInWithPhoneAuthCredential(credential)
                                    Log.d("MyLog", "onVerificationCompleted")
                                }

                                override fun onVerificationFailed(e: FirebaseException) {
                                    Log.d("MyLog", "onVerificationFailed ${e.toString()}")
//                            if (e is FirebaseAuthInvalidCredentialsException) {
//                                // Invalid request
//                                Log.d("MyLog", "onVerificationFailed: ${e.toString()}")
//                            } else if (e is FirebaseTooManyRequestsException) {
//                                // The SMS quota for the project has been exceeded
//                                Log.d("MyLog", "onVerificationFailed: ${e.toString()}")
//                            }
//            mProgressBar.visibility = View.VISIBLE
                                    // Show a message and update the UI
                                }

                                override fun onCodeSent(
                                    verificationId: String,
                                    token: PhoneAuthProvider.ForceResendingToken
                                ) {
//            mProgressBar.visibility = View.INVISIBLE

                                    verificationID = verificationId

                                    otpCodeSend(true)

//                                val credential: PhoneAuthCredential =
//                                    PhoneAuthProvider.getCredential(
//                                        verificationId, "123456"
//                                    )
//                                signInWithPhoneAuthCredential(credential)

                                }
                            }) // OnVerificationStateChangedCallbacks
                            .build()

                        Log.d("MyLog", "PhoneAuthProvider.verifyPhoneNumber(options) $options")
                        PhoneAuthProvider.verifyPhoneNumber(options)
                    }

                } else if (registrationMethod == RegistrationMethod.GOOGLE) {

                }
            }
        }
    }

    override suspend fun otpSignUp(
        smsCode: String,
        epidN: String,
        dateBirth: String,
        message: (String) -> Unit,
        navigateTabs: (Boolean) -> Unit
    ) {

        Log.d("MyLog", "smsCode $smsCode $verificationID")

        val credential: PhoneAuthCredential =
            PhoneAuthProvider.getCredential(verificationID, smsCode)

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activityMain!!) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
//                    Toast.makeText(this , "Authenticate Successfully" , Toast.LENGTH_SHORT).show()
//                    sendToMain()
                    Log.d("MyLog", "signInWithPhoneAuthCredential   isSuccessful")

                    val user = task.result?.user!!
                    val uid = user.uid

                    CoroutineScope(Dispatchers.IO).launch {
                        val uidInfo = uidSave(epidN, dateBirth, uid)
                        if (uidInfo == UID_SAVE) {
                            withContext(Dispatchers.Main) {
                                message("Учетная запись успешно зарегистрована!")
                                navigateTabs(true)
                            }
                        } else {
                            user.delete()
                            withContext(Dispatchers.Main) {
                                message("Не удалось зарегистрироваться, попробуйте позже!")
                            }
                        }
                    }

                } else {
                    // Sign in failed, display a message and update the UI
                    Log.d("MyLog", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        val exception = task.exception as FirebaseAuthInvalidCredentialsException
                        if (exception.errorCode == "ERROR_INVALID_VERIFICATION_CODE") {
                            message("Недействительный проверочный код!")
                        } else if (exception.errorCode == "ERROR_INVALID_VERIFICATION_ID") {
                            message("Недействительный verificationID!")
                        }
                        Log.d("MyLog", "signInWithPhoneAuthCredential: ${exception.errorCode}")

                    }
                    // Update UI
                }
//                mProgressBar.visibility = View.INVISIBLE
            }

    }

//    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) : String {
//
//        Log.d("MyLog", "fun signInWithPhoneAuthCredential")
//
//        var errorMessage = ""
//
//        firebaseAuth.signInWithCredential(credential)
//            .addOnCompleteListener(activityMain!!) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
////                    Toast.makeText(this , "Authenticate Successfully" , Toast.LENGTH_SHORT).show()
////                    sendToMain()
//                    Log.d("MyLog", "signInWithPhoneAuthCredential   isSuccessful")
//                    errorMessage = "Учетная запись успешно зарегистрована!"
//                } else {
//                    // Sign in failed, display a message and update the UI
//                    Log.d("MyLog", "signInWithPhoneAuthCredential: ${task.exception.toString()}")
//                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        // The verification code entered was invalid
//                       val exception = task.exception as FirebaseAuthInvalidCredentialsException
//                       if (exception.errorCode == "ERROR_INVALID_VERIFICATION_CODE") {
//                           errorMessage = "Недействительный проверочный код!"
//                           Log.d("MyLog", "errorCode: $errorMessage")
//                       } else if (exception.errorCode == "ERROR_INVALID_VERIFICATION_ID") {
//                           errorMessage = "Недействительный verificationID!"
//                       }
//                        Log.d("MyLog", "signInWithPhoneAuthCredential: ${exception.errorCode}")
//
//                    }
//                    // Update UI
//                }
////                mProgressBar.visibility = View.INVISIBLE
//            }
//
//        Log.d("MyLog", "errorCode2: $errorMessage")
//        return errorMessage
//    }


    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
//                Toast.makeText(
//                    requireContext(),
//                    com.google.firebase.auth.R.string.send_verification_done,
//                    Toast.LENGTH_LONG
//                ).show()
            } else {
//                Toast.makeText(
//                    requireContext(),
//                    com.google.firebase.auth.R.string.send_verification_error,
//                    Toast.LENGTH_LONG
//                ).show()
            }
        }
    }

    private suspend fun userInfoVerification(epidN: String, dateBirth: String): Int {

        var userInfo = USER_NOT_FOUND
//        CoroutineScope(Dispatchers.IO).launch {
        try {
//                val token = RetrofitInstance.api.userVerification(epidN, dateBirth)
            userInfo = RetrofitInstance.api.userInfoVerificationAPI(epidN, dateBirth)
//                Log.d("MyLog", "ID: ${user.id} Token: ${user.token}")
//                Log.d("MyLog", "Token: $token")

        } catch (e: Exception) {
            // Here it's better to at least log the exception
            Log.d("MyLog", "Coroutine error", e)

            userInfo = CONNECT_NOT_FOUND
        }
//        }
        return userInfo
    }


    override fun logout() {
        TODO("Not yet implemented")
    }


    private suspend fun uidSave(epidN: String, dateBirth: String, uid: String): Int {

        var uidInfo = UID_NOT_SAVE

        try {
//          val token = RetrofitInstance.api.userVerification(epidN, dateBirth)

            uidInfo = RetrofitInstance.api.uidSaveAPI(epidN, dateBirth, uid)
//                Log.d("MyLog", "ID: ${user.id} Token: ${user.token}")
            Log.d("MyLog", "Token: $uid")

        } catch (e: Exception) {
            // Here it's better to at least log the exception
            Log.d("MyLog", "Coroutine error ${e.message}", e)

            return UID_NOT_SAVE
        }

        return uidInfo
    }

//    private fun toastMessage (messageToast: String) {
//        message = messageToast
//    }

    companion object {
        private const val USER_NOT_FOUND = 0
        private const val UID_NOT_FOUND = 1
        private const val UID_FOUND = 2
        private const val CONNECT_NOT_FOUND = 3

        private const val UID_NOT_SAVE = 0
        private const val UID_SAVE = 1
    }

    override fun onActivityCreated(activity: FragmentActivity) {
        Log.d("MyLog", "AuthRepository $activity")
        activityMain = activity
    }

    override fun onActivityStarted() {
        isActivityStarted = true
    }

    override fun onActivityStopped() {
        isActivityStarted = false
    }

    override fun onActivityDestroyed() {
        this.activityMain = null
    }

}