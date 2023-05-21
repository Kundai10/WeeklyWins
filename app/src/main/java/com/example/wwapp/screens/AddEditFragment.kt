package com.example.wwapp.screens

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.wwapp.MyWinsViewModel

import com.example.wwapp.R

import com.example.wwapp.databinding.FragmentAddEditBinding
import com.example.wwapp.model.MyAdapter.Companion.getRandomImagePath

import com.example.wwapp.model.MyWinsItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditFragment : Fragment(R.layout.fragment_add_edit) {
    val viewModel by viewModels<MyWinsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAddEditBinding.bind(requireView())
        val args:AddEditFragmentArgs by navArgs()
        val win = args.win


        if(win != null){
            binding.apply {
                etTitle.setText(win.winTitle)
                etInputNote.setText(win.winContent)
                fabSaveNote.setOnClickListener{
                    val title = etTitle.text.toString()
                    val winBody = etInputNote.text.toString()

                    val updatedNote =win.copy(winTitle = title,winContent=winBody, winDay = System.currentTimeMillis(), winImage = win.winImage ?: getRandomImagePath())
                    viewModel.updateWin(updatedNote)
                }

            }
        }
        else{
            binding.apply {
                fabSaveNote.setOnClickListener{
                    val title = etTitle.text.toString()
                    val winBody = etInputNote.text.toString()
                    val win = MyWinsItems(winTitle = title, winContent = winBody, winDay = System.currentTimeMillis(),  winImage = win?.winImage
                        ?: getRandomImagePath())
                    viewModel.insertWin(win)

                }
            }

        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.winEvents.collect{event->
                if(event is MyWinsViewModel.WinEvents.NavigateToMainScreen){
                    val action = AddEditFragmentDirections.actionAddEditFragmentToHomeScreenFragment()
                    findNavController().navigate(action)
                }

            }
        }
        binding.apply {
            backArrowIcon.setOnClickListener{
                val action = AddEditFragmentDirections.actionAddEditFragmentToHomeScreenFragment()
                findNavController().navigate(action)


            }
        }



    }





}