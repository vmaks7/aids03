package com.vandanov.aids03.presentation.tabs.register

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.FragmentRegisterItemBinding
import com.vandanov.aids03.domain.register.RegisterItem

class RegisterItemFragment : Fragment() {

    private val args by navArgs<RegisterItemFragmentArgs>()
    private val registerID by lazy { args.registerID }
    private val screenMode by lazy { args.mode }

    private var _binding: FragmentRegisterItemBinding? = null
    private val binding: FragmentRegisterItemBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterItemBinding == null")

    private lateinit var viewModel: RegisterItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[RegisterItemViewModel::class.java]

        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.errorInputDepartment.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_department)
            } else {
                null
            }
            binding.tilDepartment.error = message
        }

        viewModel.errorInputDoctor.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_doctor)
            } else {
                null
            }
            binding.tilDoctor.error = message
        }

        viewModel.errorInputDateRegister.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_dateRegister)
            } else {
                null
            }
            binding.tilDateRegister.error = message
        }

        viewModel.errorInputTimeRegister.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_timeRegister)
            } else {
                null
            }
            binding.tilTimeRegister.error = message
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
    }

    private fun launchAddMode() {
        binding.btnSaveRegister.setOnClickListener {
            binding.apply {
                viewModel.addRegister(
                    etDepartment.text.toString(),
                    etDoctor.text.toString(),
                    etDateRegister.text.toString(),
                    etTimeRegister.text.toString(),
                    etNote.text.toString()
                )
            }
        }
    }

    private fun launchEditMode() {
        viewModel.getRegisterID(registerID)
        viewModel.registerItem.observe(viewLifecycleOwner) {
            binding.apply {
                etDepartment.setText(it.department)
                etDoctor.setText(it.doctor)
                etDateRegister.setText(it.dateRegister)
                etTimeRegister.setText(it.timeRegister)
                etNote.setText(it.note)
            }
        }
        binding.btnSaveRegister.setOnClickListener {
            binding.apply {
                viewModel.editRegister(
                    etDepartment.text.toString(),
                    etDoctor.text.toString(),
                    etDateRegister.text.toString(),
                    etTimeRegister.text.toString(),
                    etNote.text.toString()
                )
            }
        }
    }

    private fun addTextChangeListeners() {
        binding.etDepartment.addTextChangedListener(object : TextWatcher {
            //до того как текст изменен
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            //в момент изменения текста
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //скрывааем ошибку незаполненного поля в поле ввода
                viewModel.resetErrorInputDepartment()
            }
            //после изменения текста
            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etDoctor.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputDoctor()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etDateRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputDateRegister()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        binding.etTimeRegister.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputTimeRegister()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
    }
}