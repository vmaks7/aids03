package com.vandanov.aids03.presentation.tabs.utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.vandanov.aids03.R

fun Fragment.findTopNavController(): NavController {
    val topLevelHost = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}