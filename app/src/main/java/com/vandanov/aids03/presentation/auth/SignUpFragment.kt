package com.vandanov.aids03.presentation.auth

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.vandanov.aids03.databinding.FragmentSignUpBinding
import com.vandanov.aids03.domain.auth.entity.SignUpItem
import java.text.SimpleDateFormat
import java.util.*

class SignUpFragment: Fragment(), TextView.OnEditorActionListener {

    private val args by navArgs<SignUpFragmentArgs>()
    private val registrationMethod by lazy { args.registrationMethod }

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("FragmentSignUpBinding == null")

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        Log.d("MyLog", "Argument: $registrationMethod")

        binding.btnSignUp.setOnClickListener {
            val login = binding.etLogin.text.toString()
            val password = binding.etPassword.text.toString()
            val dateBirth = binding.etDateBirth.text.toString()
            val epidN = binding.etEpidN.text.toString()
            val repeatPassword = binding.etRepeatPassword.text.toString()

            viewModel.signUp(SignUpItem(login, epidN, dateBirth, password, repeatPassword))
        }

        binding.etDateBirth.setOnClickListener {
            openDatePickerDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_NEXT && binding.etDateBirth.text.toString() == "") {
            openDatePickerDialog()
            return true;
        }
        return false;
    }

    private fun openDatePickerDialog() {

        val timeZone = TimeZone.getTimeZone("UTC")

        // создаем построитель ограничений календаря
        val calendarConstraintBuilder = CalendarConstraints.Builder()
        // ограничение на выбор даты меньше текущей
//        calendarConstraintBuilder.setValidator(DateValidatorPointForward.now())
        // ограничение на выбор даты больше текущей
//        calendarConstraintBuilder.setValidator(DateValidatorPointBackward.now())

        val calendar: Calendar = Calendar.getInstance(timeZone)

        //val myFormat = "dd/MM/yyyy"
        val myFormat = "dd/MM/yyyy"
        val formattedDate = "01/01/1990"
        val sdf = SimpleDateFormat(myFormat)
        val date = sdf.parse(formattedDate)
        val timeInMillis = date?.time
//        val constraintBuilder = CalendarConstraints.Builder().setOpenAt(
//            timeInMillis
//        ).build()

        //начальная дата
        calendar.set(Calendar.YEAR, 1920)
        val dataN: Long = calendar.timeInMillis
        calendarConstraintBuilder.setStart(dataN)
        //конечная дата
//        calendar.set(Calendar.YEAR, 2022)
//        val dataK: Long = calendar.timeInMillis
//        calendarConstraintBuilder.setEnd(dataK)
        calendarConstraintBuilder.setValidator(DateValidatorPointBackward.now())

        val materialDateBuilder: MaterialDatePicker.Builder<Long> =
            MaterialDatePicker.Builder.datePicker()
        materialDateBuilder.setTitleText("Выберите дату рождения")
        //дата по-умолчанию
        materialDateBuilder.setSelection(timeInMillis)
//        builder.setCalendarConstraints(constraintBuilder)

        //текущая дата
        //val today = MaterialDatePicker.todayInUtcMilliseconds()
//        builder.setSelection()
        // создание экземпляра диалогового окна выбора даты материала
        materialDateBuilder.setCalendarConstraints(calendarConstraintBuilder.build());
        // теперь строим диалоговое окно выбора даты материала
        val materialDatePicker: MaterialDatePicker<*> = materialDateBuilder.build()

        Locale.setDefault(Locale("ru", "RU"))

        materialDatePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")

//        val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).apply {
//            timeZone
//        }

        val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).apply {
            timeZone
        }


//        var dateBirthFormat = ""
//        val dateBirthDateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())

        materialDatePicker.addOnPositiveButtonClickListener {
            //binding.dateBirthEditText.setText(materialDatePicker.headerText)
            binding.etDateBirth.setText(outputDateFormat.format(it))
//            dateBirthFormat = dateBirthDateFormat.format(it)
//            dateBirthMillis = it.toString()

            //Log.d("MyLog", "ата - ${picker}")
            binding.tiPassword.requestFocus()
        }
    }
}