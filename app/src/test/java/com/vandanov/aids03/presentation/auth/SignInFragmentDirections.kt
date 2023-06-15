package com.vandanov.aids03.presentation.auth

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.vandanov.aids03.R

public class SignInFragmentDirections private constructor() {
  public companion object {
    public fun actionSignInFragmentToTabsFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_signInFragment_to_tabsFragment)

    public fun actionSignInFragmentToChoiceRegistrationMethodFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_signInFragment_to_choiceRegistrationMethodFragment)
  }
}
