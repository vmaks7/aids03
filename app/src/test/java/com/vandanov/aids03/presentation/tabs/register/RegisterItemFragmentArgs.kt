package com.vandanov.aids03.presentation.tabs.register

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import java.lang.IllegalArgumentException
import kotlin.Int
import kotlin.String
import kotlin.jvm.JvmStatic

public data class RegisterItemFragmentArgs(
  public val mode: String,
  public val registerID: Int
) : NavArgs {
  public fun toBundle(): Bundle {
    val result = Bundle()
    result.putString("mode", this.mode)
    result.putInt("registerID", this.registerID)
    return result
  }

  public fun toSavedStateHandle(): SavedStateHandle {
    val result = SavedStateHandle()
    result.set("mode", this.mode)
    result.set("registerID", this.registerID)
    return result
  }

  public companion object {
    @JvmStatic
    public fun fromBundle(bundle: Bundle): RegisterItemFragmentArgs {
      bundle.setClassLoader(RegisterItemFragmentArgs::class.java.classLoader)
      val __mode : String?
      if (bundle.containsKey("mode")) {
        __mode = bundle.getString("mode")
        if (__mode == null) {
          throw IllegalArgumentException("Argument \"mode\" is marked as non-null but was passed a null value.")
        }
      } else {
        throw IllegalArgumentException("Required argument \"mode\" is missing and does not have an android:defaultValue")
      }
      val __registerID : Int
      if (bundle.containsKey("registerID")) {
        __registerID = bundle.getInt("registerID")
      } else {
        throw IllegalArgumentException("Required argument \"registerID\" is missing and does not have an android:defaultValue")
      }
      return RegisterItemFragmentArgs(__mode, __registerID)
    }

    @JvmStatic
    public fun fromSavedStateHandle(savedStateHandle: SavedStateHandle): RegisterItemFragmentArgs {
      val __mode : String?
      if (savedStateHandle.contains("mode")) {
        __mode = savedStateHandle["mode"]
        if (__mode == null) {
          throw IllegalArgumentException("Argument \"mode\" is marked as non-null but was passed a null value")
        }
      } else {
        throw IllegalArgumentException("Required argument \"mode\" is missing and does not have an android:defaultValue")
      }
      val __registerID : Int?
      if (savedStateHandle.contains("registerID")) {
        __registerID = savedStateHandle["registerID"]
        if (__registerID == null) {
          throw IllegalArgumentException("Argument \"registerID\" of type integer does not support null values")
        }
      } else {
        throw IllegalArgumentException("Required argument \"registerID\" is missing and does not have an android:defaultValue")
      }
      return RegisterItemFragmentArgs(__mode, __registerID)
    }
  }
}
