package com.vandanov.aids03.presentation.register

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
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.FragmentRegisterItemBinding
import com.vandanov.aids03.domain.register.RegisterItem

class RegisterItemFragment : Fragment() {

    private var _binding: FragmentRegisterItemBinding? = null
    private val binding: FragmentRegisterItemBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterItemBinding == null")

    private lateinit var viewModel: RegisterItemViewModel

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var screenMode = MODE_UNKNOWN
    private var registerItemID = RegisterItem.DEFAULT_ID

    // метод когда фрагмент прикрепляется к активити
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("RegisterItemFragment", "onCreate")
        super.onCreate(savedInstanceState)
        parseParams()
    }

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
//            activity?.onBackPressed()
//            activity?.onBackPressedDispatcher?.onBackPressed()
            onEditingFinishedListener.onEditingFinished()
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
        viewModel.getRegisterID(registerItemID)
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

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(KEY_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }

        val mode = args.getString(KEY_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(KEY_REGISTER_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            registerItemID = args.getInt(KEY_REGISTER_ITEM_ID, RegisterItem.DEFAULT_ID)
        }
    }

    // интерфейс чтобы отправить сообщение из Фрагмента в Активити
    interface OnEditingFinishedListener {

        fun onEditingFinished()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val KEY_SCREEN_MODE = "mode"
        private const val KEY_REGISTER_ITEM_ID = "register_item_id"
        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"

        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): RegisterItemFragment {
//            val args = Bundle()
//            args.putString(SCREEN_MODE, MODE_ADD)
//            val fragment = RegisterItemFragment()
//            fragment.arguments = args
//            return fragment

            //код в стиле kotlin
//            val args = Bundle().apply {
//                putString(SCREEN_MODE, MODE_ADD)
//            }
//            val fragment = RegisterItemFragment().apply {
//                arguments = args
//            }
//            return fragment

            //код в стиле kotlin укороченный
            return RegisterItemFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(registerItemID: Int): RegisterItemFragment {
            return RegisterItemFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_SCREEN_MODE, MODE_EDIT)
                    putInt(KEY_REGISTER_ITEM_ID, registerItemID)
                }
            }
        }

    }

}