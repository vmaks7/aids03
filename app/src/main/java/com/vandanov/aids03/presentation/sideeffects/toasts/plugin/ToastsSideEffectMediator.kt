package com.vandanov.aids03.presentation.sideeffects.toasts.plugin

import android.content.Context
import android.widget.Toast
import com.vandanov.aids03.presentation.sideeffects.SideEffectMediator
import com.vandanov.aids03.presentation.sideeffects.toasts.Toasts
import com.vandanov.aids03.presentation.utils.MainThreadExecutor

/**
 * Android implementation of [Toasts]. Displaying simple toast message and getting string from resources.
 */
class ToastsSideEffectMediator(
    private val appContext: Context
) : SideEffectMediator<Nothing>(), Toasts {

    private val executor = MainThreadExecutor()

    override fun toast(message: String) {
        executor.execute {
            Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
        }
    }

}