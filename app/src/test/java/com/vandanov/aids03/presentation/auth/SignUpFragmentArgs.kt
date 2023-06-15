package com.vandanov.aids03.presentation.auth

import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import com.vandanov.aids03.domain.auth.entity.RegistrationMethod
import java.io.Serializable
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import kotlin.Suppress
import kotlin.jvm.JvmStatic

public data class SignUpFragmentArgs(
  public val registrationMethod: RegistrationMethod
) : NavArgs {
  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toBundle(): Bundle {
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

  @Suppress("CAST_NEVER_SUCCEEDS")
  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    if (Parcelable::class.java.isAssignableFrom(RegistrationMethod::class.java)) {
      result.set("registrationMethod", this.registrationMethod as Parcelable)
    } else if (Serializable::class.java.isAssignableFrom(RegistrationMethod::class.java)) {
      result.set("registrationMethod", this.registrationMethod as Serializable)
    } else {
      throw UnsupportedOperationException(RegistrationMethod::class.java.name +
          " must implement Parcelable or Serializable or must be an Enum.")
    }
    return result
  }

  public companion object {
    @JvmStatic
    @Suppress("DEPRECATION")
    public fun fromBundle(bundle: Bundle): SignUpFragmentArgs {
      bundle.setClassLoader(SignUpFragmentArgs::class.java.classLoader)
      val __registrationMethod : RegistrationMethod?
      if (bundle.containsKey("registrationMethod")) {
        if (Parcelable::class.java.isAssignableFrom(RegistrationMethod::class.java) ||
            Serializable::class.java.isAssignableFrom(RegistrationMethod::class.java)) {
          __registrationMethod = bundle.get("registrationMethod") as RegistrationMethod?
        } else {
          throw UnsupportedOperationException(RegistrationMethod::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__registrationMethod == null) {
          throw IllegalArgumentException("Argument \"registrationMethod\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"registrationMethod\" is missing and does not have an android:defaultValue")
      }
      return SignUpFragmentArgs(__registrationMethod)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): SignUpFragmentArgs {
      val __registrationMethod : RegistrationMethod?
      if (savedStateHandle.contains("registrationMethod")) {
        if (Parcelable::class.java.isAssignableFrom(RegistrationMethod::class.java) ||
            Serializable::class.java.isAssignableFrom(RegistrationMethod::class.java)) {
          __registrationMethod = savedStateHandle.get<RegistrationMethod?>("registrationMethod")
        } else {
          throw UnsupportedOperationException(RegistrationMethod::class.java.name +
              " must implement Parcelable or Serializable or must be an Enum.")
        }
        if (__registrationMethod == null) {
          throw IllegalArgumentException("Argument \"registrationMethod\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"registrationMethod\" is missing and does not have an android:defaultValue")
      }
      return SignUpFragmentArgs(__registrationMethod)
    }
  }
}
