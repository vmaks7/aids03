package com.vandanov.aids03.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.FragmentRegisterBinding
import com.vandanov.aids03.databinding.FragmentSignInBinding
import com.vandanov.aids03.domain.register.entity.RegisterItem
import com.vandanov.aids03.presentation.tabs.TabsFragmentDirections
import com.vandanov.aids03.presentation.tabs.register.RegisterFragment
import com.vandanov.aids03.presentation.tabs.register.RegisterListAdapter
import com.vandanov.aids03.presentation.tabs.register.RegisterViewModel
import com.vandanov.aids03.presentation.tabs.utils.findTopNavController

class SignInFragment: Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding ?: throw RuntimeException("FragmentSignInBinding == null")

    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        binding.btnSignIn.setOnClickListener{

            val email = binding.loginEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.signInEmail(email, password)

//            val direction = SignInFragmentDirections.actionSignInFragmentToTabsFragment()
//            findNavController().navigate(direction)
        }

        binding.tvRegister.setOnClickListener {
//            val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            val direction = SignInFragmentDirections.actionSignInFragmentToChoiceRegistrationMethodFragment()
            findNavController().navigate(direction)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}