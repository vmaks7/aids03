package com.vandanov.aids03.presentation.sideeffects

import com.vandanov.aids03.presentation.utils.MainThreadExecutor
import com.vandanov.aids03.presentation.utils.ResourceActions
import java.util.concurrent.Executor

/**
 * Базовый класс для всех медиаторов side-effects.
 * Эти посредники живут в [ActivityScopeViewModel].
 * Посредник должен делегировать всю логику, связанную с пользовательским интерфейсом, реализациям через поле [target].
 */
open class SideEffectMediator<Implementation>(
    executor: Executor = MainThreadExecutor()
) {

    protected val target = ResourceActions<Implementation>(executor)

    /**
     * Assign/unassign the target implementation for this mediator.
     */
    fun setTarget(target: Implementation?) {
        this.target.resource = target
    }

    fun clear() {
        target.clear()
    }

}