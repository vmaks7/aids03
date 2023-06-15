package com.vandanov.aids03.data.auth

import androidx.fragment.app.FragmentActivity

interface ActivityRequired {

    fun onActivityCreated(activity: FragmentActivity)

    fun onActivityStarted()

    fun onActivityStopped()

    fun onActivityDestroyed()

}