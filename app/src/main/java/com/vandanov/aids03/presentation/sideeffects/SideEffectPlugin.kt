package com.vandanov.aids03.presentation.sideeffects

import android.content.Context

/**
 * Точко входа для каждого side-effect plugin.
 */
interface SideEffectPlugin<Mediator, Implementation> {

    /**
     * Class of side-effect mediator.
     */
    val mediatorClass: Class<Mediator>

    /**
     * Create a mediator instance which acts on view-model side.
     */
    fun createMediator(applicationContext: Context): SideEffectMediator<Implementation>

    /**
     * * Создайте реализацию для медиатора, созданного методом [createMediator].
     * Может возвращать `null`. NULL-значение может быть полезно, если логика может быть реализована непосредственно в посреднике.
     * (например, side-effect не требует activity instance)
     */
    fun createImplementation(mediator: Mediator): Implementation? = null

}