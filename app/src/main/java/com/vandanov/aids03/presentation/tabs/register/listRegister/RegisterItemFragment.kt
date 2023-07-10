package com.vandanov.aids03.presentation.tabs.register.listRegister

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
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.FragmentRegisterItemBinding
import com.vandanov.aids03.presentation.tabs.register.appointmentTime.dateAdapter.CalendarAdapter
import com.vandanov.aids03.presentation.tabs.register.appointmentTime.dateAdapter.CalendarDateModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

class RegisterItemFragment : Fragment() {

   // private val args by navArgs<RegisterItemFragmentArgs>()
   // private val registerID by lazy { args.registerID }
  //  private val screenMode by lazy { args.mode }

    private var _binding: FragmentRegisterItemBinding? = null
    private val binding: FragmentRegisterItemBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterItemBinding == null")

    private lateinit var viewModel: RegisterItemViewModel

    private lateinit var adapter: CalendarAdapter
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()

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

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)
        adapter = CalendarAdapter()
        binding.recyclerView.adapter = adapter
        setUpClickListener()
        setUpCalendar()

        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun setUpClickListener() {
        binding.ivCalendarNext.setOnClickListener {
            cal.add(Calendar.MONTH, 1)
            setUpCalendar()
        }
        binding.ivCalendarPrevious.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
//            if (cal == currentDate)
//                setUpCalendar()
//            else
            setUpCalendar()
        }
    }

    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
        binding.textDateMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()

        //val datetime = LocalDateTime.now()

        val cal2 = Calendar.getInstance()
        val dateEND = cal2.set(Calendar.DAY_OF_MONTH, 30)

        Log.d("MyLog", "Конечная дата $dateEND")

        if (LocalDateTime.now().month.value != cal.get(Calendar.MONTH)+1) {
            monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        }


        //конвертация из LocalDate в Date
        val localDate = LocalDate.parse("2023-07-10")
        val date: Date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())


       // while (dates.size < maxDaysInMonth) {
        while (monthCalendar.time < date) {
            dates.add(monthCalendar.time)
            calendarList.add(CalendarDateModel(monthCalendar.time))

//            if (monthCalendar.get(Calendar.DAY_OF_MONTH) == maxDaysInMonth) {
//                break
//            } else {
                monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
//            }

        }
//        calendarList2.clear()
//        calendarList2.addAll(calendarList)
//        adapter.setOnItemClickListener(this@MainActivity)
        adapter.setData(calendarList)
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
//        when (screenMode) {
//            MODE_ADD -> launchAddMode()
//            MODE_EDIT -> launchEditMode()
//        }
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
//        viewModel.getRegisterID(registerID)
//        viewModel.registerItem.observe(viewLifecycleOwner) {
//            binding.apply {
//                etDepartment.setText(it.department)
//                etDoctor.setText(it.doctor)
//                etDateRegister.setText(it.dateRegister)
//                etTimeRegister.setText(it.timeRegister)
//                etNote.setText(it.note)
//            }
//        }
//        binding.btnSaveRegister.setOnClickListener {
//            binding.apply {
//                viewModel.editRegister(
//                    etDepartment.text.toString(),
//                    etDoctor.text.toString(),
//                    etDateRegister.text.toString(),
//                    etTimeRegister.text.toString(),
//                    etNote.text.toString()
//                )
//            }
//        }
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