package com.vandanov.aids03.presentation.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.FragmentSignInBinding
import com.vandanov.aids03.domain.auth.entity.RegistrationMethod
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern


@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val args by navArgs<SignInFragmentArgs>()

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding ?: throw RuntimeException("FragmentSignInBinding == null")

    //    private lateinit var viewModel: SignInViewModel
    private val viewModel: SignInViewModel by viewModels()

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
//        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        binding.etLogin.setText(args.email)
        binding.etPassword.setText(args.password)

        binding.btnSignIn.setOnClickListener {

            addTextChangeListeners()
            observeViewModel()

            var login = binding.etLogin.text.toString()
            login = login.replace("\\s".toRegex(), "")
            var password = binding.etPassword.text.toString()
            password = password.replace("\\s".toRegex(), "")

            if (isValidEmail(login)) {

                viewModel.signInEmail(login, password, RegistrationMethod.EMAIL)

                viewModel.navigateTabs.observe(viewLifecycleOwner) {
                    if (it) {
                        if (findNavController().currentDestination?.id == R.id.signInFragment)
                            findNavController().navigate(R.id.action_signInFragment_to_tabsFragment)
                    }
                }

                viewModel.signInInProgress.observe(viewLifecycleOwner) {
                    binding.progressBarSignIn.visibility = if (it) View.VISIBLE else View.INVISIBLE
                    binding.btnSignIn.isEnabled = !it
                }

            } else if (validCellPhone(login)) {



            } else {
                Toast.makeText(context, "Некорректный логин", Toast.LENGTH_LONG).show()
            }
        }

        binding.tvRegister.setOnClickListener {
//            val direction = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
//            val direction = SignInFragmentDirections.actionSignInFragmentToChoiceRegistrationMethodFragment()
            findNavController().navigate(R.id.choiceRegistrationMethodFragment)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun validCellPhone(number: String): Boolean {
        val pattern: Pattern = Patterns.PHONE
        return pattern.matcher(number).matches()
    }

    private fun addTextChangeListeners() {
        binding.etLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputLogin()
            }

            override fun afterTextChanged(s: Editable?) {}

        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(s: Editable?) {}

        })
    }

    private fun observeViewModel() {
        viewModel.errorInputLogin.observe(viewLifecycleOwner) {
            val errorMessage = if (it) {
                "Введите логин"
            } else {
                null
            }
            binding.tiLogin.error = errorMessage
        }
        viewModel.errorInputPassword.observe(viewLifecycleOwner) {
            val errorMessage = if (it) {
                "Введите пароль"
            } else {
                null
            }
            binding.tiPassword.error = errorMessage
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}