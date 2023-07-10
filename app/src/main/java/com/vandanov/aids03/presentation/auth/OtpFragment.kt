package com.vandanov.aids03.presentation.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.FragmentOtpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpFragment : Fragment() {

    private val args by navArgs<OtpFragmentArgs>()
    private val epidN by lazy { args.epidN }
    private val dateBirth by lazy { args.dateBirth }

    private var _binding: FragmentOtpBinding? = null
    private val binding: FragmentOtpBinding
        get() = _binding ?: throw RuntimeException("FragmentOtpBinding == null")

    //    private lateinit var viewModel: SignInViewModel
    private val viewModel: OtpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOtpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTextChangeListener()

        binding.verifyOTPBtn.setOnClickListener {

            val smsCode =
                (binding.otpEditText1.text.toString() + binding.otpEditText2.text.toString() + binding.otpEditText3.text.toString()
                        + binding.otpEditText4.text.toString() + binding.otpEditText5.text.toString() + binding.otpEditText6.text.toString())

            if (smsCode.isNotEmpty()) {
                if (smsCode.length == 6) {
                    viewModel.otpSignUp(smsCode, epidN, dateBirth)
//                    progressBar.visibility = View.VISIBLE

                    viewModel.navigateTabs.observe(viewLifecycleOwner) {
                        if (it) {
                            findNavController().navigate(R.id.action_otpFragment_to_tabsFragment)
                        }
                    }

                } else {
                    Toast.makeText(requireContext(), "Please Enter Correct OTP", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(requireContext(), "Please Enter OTP", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun addTextChangeListener() {
        binding.apply {
            otpEditText1.addTextChangedListener(EditTextWatcher(otpEditText1))
            otpEditText2.addTextChangedListener(EditTextWatcher(otpEditText2))
            otpEditText3.addTextChangedListener(EditTextWatcher(otpEditText3))
            otpEditText4.addTextChangedListener(EditTextWatcher(otpEditText4))
            otpEditText5.addTextChangedListener(EditTextWatcher(otpEditText5))
            otpEditText6.addTextChangedListener(EditTextWatcher(otpEditText6))
        }
    }

    inner class EditTextWatcher(private val view: View) : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {
            val text = p0.toString()
            when (view.id) {
                R.id.otpEditText1 -> if (text.length == 1) binding.otpEditText2.requestFocus()
                R.id.otpEditText2 -> if (text.length == 1) binding.otpEditText3.requestFocus() else if (text.isEmpty()) binding.otpEditText1.requestFocus()
                R.id.otpEditText3 -> if (text.length == 1) binding.otpEditText4.requestFocus() else if (text.isEmpty()) binding.otpEditText2.requestFocus()
                R.id.otpEditText4 -> if (text.length == 1) binding.otpEditText5.requestFocus() else if (text.isEmpty()) binding.otpEditText3.requestFocus()
                R.id.otpEditText5 -> if (text.length == 1) binding.otpEditText6.requestFocus() else if (text.isEmpty()) binding.otpEditText4.requestFocus()
                R.id.otpEditText6 -> if (text.isEmpty()) binding.otpEditText5.requestFocus()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}