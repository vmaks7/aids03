package com.vandanov.aids03.presentation.auth

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.vandanov.aids03.R

public class SignUpFragmentDirections private constructor() {
  public companion object {
    public fun actionSignUpFragmentToOtpFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_signUpFragment_to_otpFragment)
  }
}
