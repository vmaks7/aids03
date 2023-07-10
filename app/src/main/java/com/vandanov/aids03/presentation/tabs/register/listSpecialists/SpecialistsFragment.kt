package com.vandanov.aids03.presentation.tabs.register.listSpecialists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.vandanov.aids03.databinding.FragmentSpecialistsBinding

class SpecialistsFragment : Fragment() {

    private var _binding: FragmentSpecialistsBinding? = null
    private val binding: FragmentSpecialistsBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterSpecialistsBinding == null")

    private lateinit var adapter: SpecialistsAdapter
    private lateinit var viewModel: SpecialistsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSpecialistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        viewModel = ViewModelProvider(this)[SpecialistsViewModel::class.java]
        viewModel.getListSpecialists()
        viewModel.listSpecialists.observe(viewLifecycleOwner) {
//            if (it.size != 0) {
//                adapter = SpecialistsAdapter()
//                binding.rvSpecialists.adapter = adapter
//                adapter.onRegisterSpecialistsClickListener = {
//                    val direction = SpecialistsFragmentDirections.actionSpecialistsFragmentToRegisterDateFragment()
//                    findNavController().navigate(direction)
//                }
//            } else {
//                findNavController().popBackStack()
//            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}