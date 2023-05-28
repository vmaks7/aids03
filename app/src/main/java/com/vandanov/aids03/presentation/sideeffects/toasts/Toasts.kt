package com.vandanov.aids03.presentation.sideeffects.toasts

/**
 * Интерфейс для показа всплывающих сообщений пользователю из моделей просмотра.
 * Вам необходимо добавить [ToastsPlugin] в свою активность, прежде чем использовать эту функцию.
 */
interface Toasts {

    /**
     * Показать простое тост-сообщение.
     */
    fun toast(message: String)

}