package com.vandanov.aids03.data.auth

import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.SAVED_STATE_REGISTRY_OWNER_KEY
import androidx.navigation.Navigation.findNavController
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.vandanov.aids03.R
import com.vandanov.aids03.data.retrofit.RetrofitInstance
import com.vandanov.aids03.domain.auth.entity.AccountItem
import com.vandanov.aids03.domain.auth.entity.SignUpItem
import com.vandanov.aids03.domain.auth.repository.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AuthRepositoryImpl : AuthRepository {

    private lateinit var firebaseAuth: FirebaseAuth

    override suspend fun signInEmail(email: String, password: String) {

        firebaseAuth = FirebaseAuth.getInstance()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result.user?.isEmailVerified == true) {
//                        findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)

                        Log.d("MyLog", "Success")

                    } else {
//                        Toast.makeText(
//                            requireContext(),
//                            R.string.ERROR_EMAIL_verification_done,
//                            Toast.LENGTH_LONG
//                        ).show()
                    }
                } else {
                    if (it.exception is FirebaseAuthInvalidCredentialsException) {
                        val exception =
                            it.exception as FirebaseAuthInvalidCredentialsException
                        if (exception.errorCode == "ERROR_INVALID_EMAIL") {
//                            Toast.makeText(
//                                requireContext(),
//                                R.string.ERROR_INVALID_EMAIL,
//                                Toast.LENGTH_LONG
//                            ).show()
                        } else if (exception.errorCode == "ERROR_WRONG_PASSWORD") {
//                            Toast.makeText(
//                                requireContext(),
//                                R.string.ERROR_WRONG_PASSWORD,
//                                Toast.LENGTH_LONG
//                            ).show()

                        }
                    } else if (it.exception is FirebaseAuthInvalidUserException) {
                        val exception =
                            it.exception as FirebaseAuthInvalidUserException
                        if (exception.errorCode == "ERROR_USER_NOT_FOUND") {
//                            Toast.makeText(
//                                requireContext(),
//                                R.string.ERROR_USER_NOT_FOUND,
//                                Toast.LENGTH_LONG
//                            ).show()
                        }
                    } else if (it.exception is FirebaseNetworkException) {
//                            Toast.makeText(
//                                requireContext(),
//                                R.string.NetworkException,
//                                Toast.LENGTH_LONG
//                            ).show()
                    } else {
//                        Toast.makeText(
//                            requireContext(),
//                            it.exception.toString(),
//                            Toast.LENGTH_SHORT
//                        )
//                            .show()
                    }
                }

            }
    }

    override suspend fun signUp(signUpData: SignUpItem, message: (String) -> Unit) {

        Log.d("MyLog", "STAAAART")

        val userInfo = userInfoVerification(signUpData.epidN, signUpData.dateBirth)

        when (userInfo) {

            USER_NOT_FOUND -> {
                Log.d("MyLog", "USER NOT FOUND")
                message("Пациент не найден, проверьте введенный Эпид№ и дату рождения!")
            }
            UID_FOUND -> {
                Log.d("MyLog", "USER EXISTS")
                message("Учетная запись для пациента с введенным Эпид№ была создана ранее!")
            }
            UID_NOT_FOUND -> {
                Log.d("MyLog", "USER REGISTER")

                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.createUserWithEmailAndPassword(
                    signUpData.login,
                    signUpData.password
                )
                    .addOnCompleteListener { task ->

                        if (task.isSuccessful) {
                            sendEmailVerification(task.result?.user!!)

                            Log.d("MyLog", "Auth")

                            //                        val token = "test token"
                            //                        tokenSave(token)

                            //добавляем токен в 1С
                            Log.d(
                                "MyLog",
                                "USER UID: ${firebaseAuth.currentUser?.uid}"
                            )
                            //FirebaseAuth.getInstance().currentUser

                            val uid = firebaseAuth.currentUser?.uid.toString()

//                            CoroutineScope(Dispatchers.IO).launch {
//                                val uidInfo = uidSave(signUpData.epidN, signUpData.dateBirth, uid)
//                                if (uidInfo == UID_SAVE) {
//                                    Log.d("MyLog", "UID save")
//                                } else {
//                                    Log.d("MyLog", "UID not save")
//                                }
//                            }

                            message("Учетная запись успешно зарегистрирована!")


                        } else {
//                            Log.d("MyLog", "Exception ${task.exception}")

                            if (task.exception is FirebaseAuthUserCollisionException) {
                                val exception =
                                    task.exception as FirebaseAuthUserCollisionException
                                if (exception.errorCode == "ERROR_EMAIL_ALREADY_IN_USE") {
                                    //                            Toast.makeText(
                                    //                                requireContext(),
                                    //                                com.google.firebase.auth.R.string.ERROR_EMAIL_ALREADY_IN_USE,
                                    //                                Toast.LENGTH_LONG
                                    //                            ).show()

                                    message("Электронная почта уже используется!")
                                }

                            } else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                val exception =
                                    task.exception as FirebaseAuthInvalidCredentialsException
                                if (exception.errorCode == "ERROR_INVALID_EMAIL") {
                                    //                            Toast.makeText(
                                    //                                requireContext(),
                                    //                                com.google.firebase.auth.R.string.ERROR_INVALID_EMAIL,
                                    //                                Toast.LENGTH_LONG
                                    //                            ).show()
                                    message("Недействительная электронная почта!")
                                }
                            }
                            if (task.exception is FirebaseAuthWeakPasswordException) {
                                val exception =
                                    task.exception as FirebaseAuthWeakPasswordException
                                if (exception.errorCode == "ERROR_WEAK_PASSWORD") {
                                    //                            Toast.makeText(
                                    //                                requireContext(),
                                    //                                com.google.firebase.auth.R.string.ERROR_WEAK_PASSWORD,
                                    //                                Toast.LENGTH_LONG
                                    //                            ).show()

                                    message("Введен слабый пароль!")

//                                    toastMessage(message)

                                }
                            }
                        }
                      }

            }
        }
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

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
        }
//        }
        return userInfo
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
            Log.d("MyLog", "Coroutine error", e)
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

        private const val UID_NOT_SAVE = 0
        private const val UID_SAVE = 1
    }

}