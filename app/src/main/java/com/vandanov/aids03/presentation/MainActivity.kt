package com.vandanov.aids03.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vandanov.aids03.R
import com.vandanov.aids03.databinding.ActivityMainBinding
import com.vandanov.aids03.domain.register.RegisterItem
import com.vandanov.aids03.presentation.register.RegisterItemActivity
import com.vandanov.aids03.presentation.register.RegisterItemFragment
import com.vandanov.aids03.presentation.register.RegisterListAdapter
import com.vandanov.aids03.presentation.register.RegisterViewModel

class MainActivity : AppCompatActivity() { //, RegisterItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: RegisterViewModel
    private lateinit var registerListAdapter: RegisterListAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        registerListAdapter = RegisterListAdapter()

        val rvRegisterList = binding.rvRegister
        rvRegisterList.adapter = registerListAdapter

        setupClickListener()
        setupSwipeListener(rvRegisterList)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        viewModel.register.observe(this) {
            registerListAdapter.submitList(it)
        }

        binding.fabAddRegister.setOnClickListener {
            val intent = RegisterItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupClickListener() {
        registerListAdapter.onRegisterItemClickListener = {
            val intent = RegisterItemActivity.newIntentEditItem(this, it.id)
            startActivity(intent)
        }
    }

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

//    override fun onEditingFinished() {
//        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
//        supportFragmentManager.popBackStack()
//    }

}