package com.vandanov.aids03.presentation.tabs.register.appointmentTime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.FragmentRegisterDateBinding
import com.vandanov.aids03.presentation.tabs.register.appointmentTime.dateAdapter.CalendarAdapter
import com.vandanov.aids03.presentation.tabs.register.appointmentTime.dateAdapter.CalendarDateModel
import com.vandanov.aids03.presentation.tabs.register.appointmentTime.timeAdapter.AppointmentTimeAdapter
import com.vandanov.aids03.presentation.tabs.utils.findTopNavController
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale

class RegisterDateFragment : Fragment() {

//    private val args by navArgs<RegisterDateFragmentArgs>()

    private lateinit var viewModel: RegisterDateViewModel

    private var _binding: FragmentRegisterDateBinding? = null
    private val binding: FragmentRegisterDateBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterDateBinding == null")

    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var appointmentTimeAdapter: AppointmentTimeAdapter

    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)
    private val dates = ArrayList<Date>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[RegisterDateViewModel::class.java]

        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvDate)
        calendarAdapter = CalendarAdapter()
        binding.rvDate.adapter = calendarAdapter

        setUpCalendar()

        snapHelper.attachToRecyclerView(binding.rvTime)
//        appointmentTimeAdapter = AppointmentTimeAdapter(args.specialist)
        appointmentTimeAdapter = AppointmentTimeAdapter()
        binding.rvTime.adapter = appointmentTimeAdapter

//        binding.tvSpeciality.text = args.specialist.speciality
//        binding.tvFIO.text = args.specialist.fio

        val manager = GridLayoutManager(requireContext(), 4)
        binding.rvTime.layoutManager = manager

        binding.btnSaveRegister.setOnClickListener {
            findTopNavController().popBackStack(R.id.tabsFragment, false)
        }

    }

    private fun setUpCalendar() {
        val calendarList = ArrayList<CalendarDateModel>()
       // binding.textDateMonth.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        dates.clear()

        //val datetime = LocalDateTime.now()

        val cal2 = Calendar.getInstance()
        val dateEND = cal2.set(Calendar.DAY_OF_MONTH, 30)

        if (LocalDateTime.now().month.value != cal.get(Calendar.MONTH)+1) {
            monthCalendar.set(Calendar.DAY_OF_MONTH, 1)
        }

        //конвертация из LocalDate в Date
        val localDate = LocalDate.parse("2023-08-10")
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
        calendarAdapter.setData(calendarList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}