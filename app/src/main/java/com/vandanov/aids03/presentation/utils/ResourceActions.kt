package com.vandanov.aids03.presentation.utils

import java.util.concurrent.Executor


typealias ResourceAction<T> = (T) -> Unit

/**
 * Очередь действий, где действия выполняются только при наличии ресурса. Если это не так, то
 * действие добавляется в очередь и ожидает, пока ресурс не станет доступным.
 */
class ResourceActions<T>(
    private val executor: Executor
) {

    var resource: T? = null
        set(newValue) {
            field = newValue
            if (newValue != null) {
                actions.forEach { action ->
                    executor.execute {
                        action(newValue)
                    }
                }
                actions.clear()
            }
        }

    private val actions = mutableListOf<ResourceAction<T>>()

    /**
     * Вызывать действие только тогда, когда [ресурс] существует (не нуль). В противном случае
     * действие откладывается до тех пор, пока [ресурсу] не будет присвоено какое-то ненулевое значение
     */
    operator fun invoke(action: ResourceAction<T>) {
        val resource = this.resource
        if (resource == null) {
            actions += action
        } else {
            executor.execute {
                action(resource)
            }
        }
    }

    fun clear() {
        actions.clear()
    }
}