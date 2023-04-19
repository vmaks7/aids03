package com.vandanov.aids03.presentation.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.FragmentRegisterItemBinding
import com.vandanov.aids03.databinding.FragmentTabsBinding

class TabsFragment : Fragment() {

    private var _binding: FragmentTabsBinding? = null
    private val binding: FragmentTabsBinding
        get() = _binding ?: throw RuntimeException("FragmentTabsBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Связываем навигацию и bottom menu
        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}