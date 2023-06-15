package com.vandanov.aids03.presentation.auth

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.vandanov.aids03.R
import com.vandanov.aids03.domain.auth.entity.RegistrationMethod
import java.io.Serializable
import java.lang.UnsupportedOperationException
import kotlin.Int
import kotlin.Suppress

public class ChoiceRegistrationMethodFragmentDirections private constructor() {
  private data class ActionChoiceRegistrationMethodFragmentToSignUpFragment(
    public val registrationMethod: RegistrationMethod
  ) : NavDirections {
    public override val actionId: Int =
        R.id.action_choiceRegistrationMethodFragment_to_signUpFragment

    public override val arguments: Bundle
      @Suppress("CAST_NEVER_SUCCEEDS")
      get() {
        val result = Bundle()
        if (Parcelable::class.java.isAssignableFrom(RegistrationMethod::class.java)) {
          result.putParcelable("registrationMethod", this.registrationMethod as Parcelable)
        } else if (Serializable::class.java.isAssignableFrom(RegistrationMethod::class.java)) {
          result.putSerializable("registrationMethod", this.registrationMethod as Serializable)
        } else {
          throw UnsupportedOperationException(RegistrationMethod::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        return result
      }
  }

  public companion object {
    public fun actionChoiceRegistrationMethodFragmentToSignInFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_choiceRegistrationMethodFragment_to_signInFragment)

    public
        fun actionChoiceRegistrationMethodFragmentToSignUpFragment(registrationMethod: RegistrationMethod):
        NavDirections = ActionChoiceRegistrationMethodFragmentToSignUpFragment(registrationMethod)
  }
}
