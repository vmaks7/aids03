package com.vandanov.aids03.presentation.auth

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.FragmentSignUpBinding
import com.vandanov.aids03.domain.auth.entity.SignUpItem
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class SignUpFragment : Fragment(), TextView.OnEditorActionListener {

//    private lateinit var firebaseAuth: FirebaseAuth

    private val args by navArgs<SignUpFragmentArgs>()
    val registrationMethod by lazy { args.registrationMethod }

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("FragmentSignUpBinding == null")

    //private lateinit var viewModel: SignUpViewModel
    private val viewModel: SignUpViewModel by viewModels()

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
//        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding.btnSignUp.setOnClickListener {
            addTextChangeListeners()
            observeViewModel()

            if (binding.etPassword.text.toString() != binding.etRepeatPassword.text.toString()) {
                Toast.makeText(context, "Пароли несовпадают!", Toast.LENGTH_LONG).show()
            } else {
                val phoneNumber = binding.etPhoneNumber.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val dateBirth = binding.etDateBirth.text.toString()
                val epidN = binding.etEpidN.text.toString()
                val repeatPassword = binding.etRepeatPassword.text.toString()

                val signUpItem =
                    SignUpItem(phoneNumber, email, epidN, dateBirth, password, repeatPassword)

                viewModel.signUp(signUpItem, registrationMethod)

//                viewModel.signUp(signUpItem, registrationMethod) {
//                    OtpDialogFragment.show(requireActivity().supportFragmentManager)
//                }

                viewModel.otpCodeSend.observe(viewLifecycleOwner) {
                    if (it) {
//                    OtpDialogFragment.show(requireActivity().supportFragmentManager, it)
//                        OtpDialogFragment.show(requireActivity().supportFragmentManager, epidN, dateBirth)

                        val direction = SignUpFragmentDirections.actionSignUpFragmentToOtpFragment(
                            epidN,
                            dateBirth
                        )
                        findNavController().navigate(direction)

                        Log.d("MyLog", "LiveDataObserve $it")
                    }
                }

                viewModel.navigateLogin.observe(viewLifecycleOwner) {
                    if (it) {
                        val direction =
                            SignUpFragmentDirections.actionSignUpFragmentToSignInFragment(
                                email,
                                password
                            )
                        findNavController().navigate(direction)
                    }
                }

                viewModel.signUpInProgress.observe(viewLifecycleOwner) {
                    binding.progressBarSignUp.visibility = if (it) View.VISIBLE else View.INVISIBLE
                    binding.btnSignUp.isEnabled = !it
                }

//                if (registrationMethod == RegistrationMethod.EMAIL) {
//                    viewModel.signUpEmail(signUpItem)
//                } else if (registrationMethod == RegistrationMethod.PHONE) {
//                    viewModel.signUpPhone(signUpItem)
//                } else if (registrationMethod == RegistrationMethod.GOOGLE) {
//                    // TODO: "GOOGLE AUTH"
//                }
            }
        }

        binding.etDateBirth.setOnClickListener {
            openDatePickerDialog()
        }

//        binding.btnOtp.setOnClickListener {
//
//            firebaseAuth = FirebaseAuth.getInstance()
//
//            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
//                .setPhoneNumber(binding.etPhoneNumber.text.toString())       // Phone number to verify
//                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                .setActivity(requireActivity())                 // Activity (for callback binding)
//                .setCallbacks(object :
//                    PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//
//                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//                        // Этот обратный вызов будет вызываться в двух случаях:
//                        // 1 - Мгновенная проверка. В некоторых случаях номер телефона может быть мгновенно
//                        // проверено без отправки или ввода кода подтверждения.
//                        // 2 - Автопоиск. На некоторых устройствах сервисы Google Play могут автоматически
//                        // обнаружим входящее СМС с подтверждением и выполним проверку без
//                        // действие пользователя.
////            signInWithPhoneAuthCredential(credential)
//                        Log.d("MyLog", "onVerificationCompleted")
//                    }
//
//                    override fun onVerificationFailed(e: FirebaseException) {
//                        // Этот обратный вызов вызывается при недопустимом запросе на проверку,
//                        // например, если формат номера телефона недействителен.
//
//                        Log.d("MyLog", "onVerificationFailed ${e.toString()}")
//
////                            if (e is FirebaseAuthInvalidCredentialsException) {
////                                // Invalid request
////                                Log.d("MyLog", "onVerificationFailed: ${e.toString()}")
////                            } else if (e is FirebaseTooManyRequestsException) {
////                                // The SMS quota for the project has been exceeded
////                                Log.d("MyLog", "onVerificationFailed: ${e.toString()}")
////                            }
////            mProgressBar.visibility = View.VISIBLE
//                        // Show a message and update the UI
//                    }
//
//                    override fun onCodeSent(
//                        verificationId: String,
//                        token: PhoneAuthProvider.ForceResendingToken
//                    ) {
//                        // SMS-код подтверждения отправлен на указанный номер телефона, мы
//                        // теперь нужно попросить пользователя ввести код, а затем создать учетные данные
//                        // комбинируя код с идентификатором проверки.
//                        // Сохраняем идентификатор подтверждения и токен повторной отправки, чтобы мы могли использовать их позже
////            val intent = Intent(this@PhoneActivity , OTPActivity::class.java)
////            intent.putExtra("OTP" , verificationId)
////            intent.putExtra("resendToken" , token)
////            intent.putExtra("phoneNumber" , number)
////            startActivity(intent)
////            mProgressBar.visibility = View.INVISIBLE
//
//                        ConfirmationCode(verificationId, token)
//
//                        Log.d("MyLog", "onCodeSent")
//
////                            onSuccess()
//
//                        OtpDialogFragment.show(requireActivity().supportFragmentManager, verificationId)
//
////                                val credential: PhoneAuthCredential =
////                                    PhoneAuthProvider.getCredential(
////                                        verificationId, "123456"
////                                    )
////                                signInWithPhoneAuthCredential(credential)
//
//                    }
//                }) // OnVerificationStateChangedCallbacks
//                .build()
//
//            PhoneAuthProvider.verifyPhoneNumber(options)
//        }
//
    }

    private fun addTextChangeListeners() {
        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputEmail()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputPhoneNumber()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.etEpidN.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputEpidN()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.etDateBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputDateBirth()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        binding.etRepeatPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputRepeatPassword()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun observeViewModel() {
        viewModel.errorInputEmail.observe(viewLifecycleOwner) {
            val errorMessage = if (it) {
                "Заполните поле email"
            } else {
                null
            }
            binding.tiEmail.error = errorMessage
        }
        viewModel.errorInputPhoneNumber.observe(viewLifecycleOwner) {
            val errorMessage = if (it) {
                "Заполните поле телефон"
            } else {
                null
            }
            binding.tiPhoneNumber.error = errorMessage
        }
        viewModel.errorInputEpidN.observe(viewLifecycleOwner) {
            val errorMessage = if (it) {
                "Заполните поле Эпид№"
            } else {
                null
            }
            binding.tiEpidN.error = errorMessage
        }
        viewModel.errorInputDateBirth.observe(viewLifecycleOwner) {
            val errorMessage = if (it) {
                "Заполните поле дата рождения"
            } else {
                null
            }
            binding.tiDateBirth.error = errorMessage
        }
        viewModel.errorInputPassword.observe(viewLifecycleOwner) {
            val errorMessage = if (it) {
                "Заполните поле пароль"
            } else {
                null
            }
            binding.tiPassword.error = errorMessage
        }
        viewModel.errorInputRepeatPassword.observe(viewLifecycleOwner) {
            val errorMessage = if (it) {
                "Заполните поле подтвердить пароль"
            } else {
                null
            }
            binding.tiRepeatPassword.error = errorMessage
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