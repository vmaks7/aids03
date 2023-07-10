package com.vandanov.aids03.presentation.auth

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.FragmentDialogOtpBinding
import com.vandanov.aids03.databinding.FragmentOtpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpDialogFragment : DialogFragment() {

//    private val verificationID: String?
//        get() = requireArguments().getString(VERIFICATION_ID)

    private val epidN: String?
        get() = requireArguments().getString(EPID_N)

    private val dateBirth: String?
        get() = requireArguments().getString(DATE_BIRTH)

//    private lateinit var viewModel: OtpDialogViewModel
    private val viewModel: OtpDialogViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

//        viewModel = ViewModelProvider(this)[OtpDialogViewModel::class.java]

        val dialogBinding = FragmentDialogOtpBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Код подтверждения")
            .setView(dialogBinding.root)
            .setPositiveButton("Ок", null)
            .setNeutralButton("Закрыть", null)
            .create()

        dialog.setOnShowListener {
            dialogBinding.smsCodeInputEditText.requestFocus()
            showKeyboard(dialogBinding.smsCodeInputEditText)

            dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
                val smsCode = dialogBinding.smsCodeInputEditText.text.toString()
                if (smsCode.isBlank()) {
                    dialogBinding.smsCodeInputEditText.error = "Введите код подтверждения!"
                    return@setOnClickListener
                }
//                parentFragmentManager.setFragmentResult(requestKey, bundleOf(KEY_VOLUME_RESPONSE to volume))

//                verificationID?.let { it1 -> viewModel.otpSignUp(smsCode, it1) }

                viewModel.otpSignUp(smsCode, epidN!!, dateBirth!!)

//                dismiss()


//                viewModel.navigateTabs.observe(viewLifecycleOwner) {
//                    Log.d("MyLog", "onViewCreatedDialogObserve $it")
//                    if (it) {
//                        findNavController().navigate(R.id.action_otpFragment_to_tabsFragment)
//                    } else {
//                        dismiss()
//                    }
//                }

            }
        }
        dialog.setOnDismissListener { hideKeyboard(dialogBinding.smsCodeInputEditText) }

        return dialog
    }


    private fun showKeyboard(view: View) {
        view.post {
            getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    private fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    companion object {
        private val TAG = OtpDialogFragment::class.java.simpleName
//        private val VERIFICATION_ID = "VERIFICATION_ID"
//        @JvmStatic private val ARG_VOLUME = "ARG_VOLUME"
//        @JvmStatic private val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"
//
//        @JvmStatic val DEFAULT_REQUEST_KEY = "$TAG:defaultRequestKey"

//        fun show(manager: FragmentManager, verificationID: String) {
//            val dialogFragment = OtpDialogFragment()
//            dialogFragment.arguments = bundleOf(
//                VERIFICATION_ID to verificationID
//            )
//            dialogFragment.show(manager, TAG)
//        }

        private val EPID_N = "EPID_N"
        private val DATE_BIRTH = "DATE_BIRTH"

        fun show(manager: FragmentManager, epidN: String, dateBirth: String) {
            val dialogFragment = OtpDialogFragment()
            dialogFragment.arguments = bundleOf(
                EPID_N to epidN,
                DATE_BIRTH to dateBirth
            )
            dialogFragment.show(manager, TAG)
        }

//        fun setupListener(manager: FragmentManager, lifecycleOwner: LifecycleOwner, requestKey: String, listener: CustomInputDialogListener) {
//            manager.setFragmentResultListener(requestKey, lifecycleOwner, FragmentResultListener { key, result ->
//                listener.invoke(key, result.getInt(KEY_VOLUME_RESPONSE))
//            })
//        }
    }
}