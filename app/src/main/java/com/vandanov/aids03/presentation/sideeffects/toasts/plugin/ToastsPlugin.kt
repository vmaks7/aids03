package com.vandanov.aids03.presentation.sideeffects.toasts.plugin

import android.content.Context
import com.vandanov.aids03.presentation.sideeffects.SideEffectMediator
import com.vandanov.aids03.presentation.sideeffects.SideEffectPlugin
import com.vandanov.aids03.presentation.sideeffects.toasts.Toasts

/**
 * Плагин для отображения всплывающих сообщений от моделей представления.
 * Позволяет добавить интерфейс [Toasts] в конструктор модели представления.
 */

class ToastsPlugin : SideEffectPlugin<Toasts, Nothing> {

    override val mediatorClass: Class<Toasts>
        get() = Toasts::class.java

    override fun createMediator(applicationContext: Context): SideEffectMediator<Nothing> {
        return ToastsSideEffectMediator(applicationContext)
    }

}