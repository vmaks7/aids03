package com.vandanov.aids03.presentation.tabs.register.listRegister

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.FragmentRegisterBinding
import com.vandanov.aids03.presentation.tabs.register.listRegister.adapter.RegisterListAdapter
import com.vandanov.aids03.presentation.tabs.utils.findTopNavController


//    override fun onEditingFinished() {
//        Toast.makeText(this@RegisterItemActivity, "Success", Toast.LENGTH_SHORT).show()
//        finish()
//    }
//}

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding
        get() = _binding ?: throw RuntimeException("FragmentRegisterBinding == null")

    private lateinit var viewModel: RegisterViewModel
    private lateinit var registerListAdapter: RegisterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerListAdapter = RegisterListAdapter()

        val rvRegisterList = binding.rvRegister
        rvRegisterList.adapter = registerListAdapter

//        setupClickListener()
        setupSwipeListener(rvRegisterList)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewModel.register.observe(viewLifecycleOwner) {
            registerListAdapter.submitList(it)
        }

        binding.fabAddRegister.setOnClickListener {
//            findTopNavController().navigate(
//                TabsFragmentDirections.actionTabsFragmentToRegisterItemFragment(
//                    MODE_ADD,
//                    RegisterItem.DEFAULT_ID
//                )
//            )

//            findNavController().navigate(R.id.registerSpecialistsFragment)
            viewModel.getListSpecialists()
            viewModel.listSpecialists.observe(viewLifecycleOwner) {
                if (it.size != 0) {
                    findTopNavController().navigate(R.id.SpecialistsFragment)
                }
            }

        }

    }

//    private fun setupClickListener() {
//        registerListAdapter.onRegisterItemClickListener = {
//            findTopNavController().navigate(
//                TabsFragmentDirections.actionTabsFragmentToRegisterItemFragment(
//                    MODE_EDIT,
//                    it.id
//                )
//            )
//
//        }
//    }

    private fun setupSwipeListener(rvRegisterList: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = registerListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteRegister(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvRegisterList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
    }

}