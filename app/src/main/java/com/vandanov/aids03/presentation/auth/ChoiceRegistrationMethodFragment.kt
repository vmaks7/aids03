package com.vandanov.aids03.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vandanov.aids03.databinding.FragmentChoiceRegistrationMethodBinding
import com.vandanov.aids03.domain.auth.entity.RegistrationMethod


class ChoiceRegistrationMethodFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentChoiceRegistrationMethodBinding? = null
    private val binding: FragmentChoiceRegistrationMethodBinding
        get() = _binding ?: throw RuntimeException("FragmentChoiceRegistrationMethodBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoiceRegistrationMethodBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llPhone.setOnClickListener {
            val direction = ChoiceRegistrationMethodFragmentDirections.actionChoiceRegistrationMethodFragmentToSignUpFragment(RegistrationMethod.PHONE)
            findNavController().navigate(direction)
        }

        binding.llEmail.setOnClickListener {
            val direction = ChoiceRegistrationMethodFragmentDirections.actionChoiceRegistrationMethodFragmentToSignUpFragment(RegistrationMethod.EMAIL)
            findNavController().navigate(direction)
        }

        binding.llGoogle.setOnClickListener {
            val direction = ChoiceRegistrationMethodFragmentDirections.actionChoiceRegistrationMethodFragmentToSignUpFragment(RegistrationMethod.GOOGLE)
            findNavController().navigate(direction)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}