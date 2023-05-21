package com.example.wwapp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wwapp.MyWinsViewModel
import com.example.wwapp.R
import com.example.wwapp.databinding.FragmentHomeScreenBinding
import com.example.wwapp.model.MyAdapter
import com.example.wwapp.model.MyWinsItems
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeScreenFragment : Fragment(R.layout.fragment_home_screen), MyAdapter.OnWinClickListener {
    val viewModel by viewModels<MyWinsViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeScreenBinding.bind(requireView())


        binding.apply {
            rvDisplayedNotes.layoutManager = LinearLayoutManager(context)
            rvDisplayedNotes.setHasFixedSize(true)
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.wins.collect { wins ->
                    if (wins.isEmpty()) {
                        binding.rvDisplayedNotes.visibility = View.GONE
                        binding.lottieAnimView.visibility = View.VISIBLE
                        binding.lottieAnimView.playAnimation()
                    } else {
                        binding.rvDisplayedNotes.visibility = View.VISIBLE
                        binding.lottieAnimView.visibility = View.GONE
                        val adapter = MyAdapter(wins, this@HomeScreenFragment)
                        binding.rvDisplayedNotes.adapter = adapter
                    }
                }

            }
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.winEvents.collect { event ->
                    if (event is MyWinsViewModel.WinEvents.ShowUndoSnackBar) {
                        Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG)
                            .setAction("UNDO") {
                                viewModel.insertWin(event.item)
                            }.show()
                    }

                }
            }

        }
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty().trim()
                viewLifecycleOwner.lifecycleScope.launch {
                    if (query.isNotEmpty()) {
                        viewModel.searchNote(query).collect { wins ->
                            if (wins.isEmpty()) {
                                Snackbar.make(
                                    requireView(),
                                    "No results found for '$query'",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            } else {
                                val adapter = MyAdapter(wins, this@HomeScreenFragment)
                                binding.rvDisplayedNotes.adapter = adapter
                            }
                        }
                    } else {
                        viewModel.wins.collect { wins ->
                            val adapter = MyAdapter(wins, this@HomeScreenFragment)
                            binding.rvDisplayedNotes.adapter = adapter
                        }
                    }
                }

                return true
            }


        })
    }


    override fun onItemClick(win: MyWinsItems) {
        val action = HomeScreenFragmentDirections.actionHomeScreenFragmentToAddEditFragment(win)
        findNavController().navigate(action)

    }

    override fun onItemLongClick(win: MyWinsItems) {
        viewModel.deleteWin(win)
    }


}