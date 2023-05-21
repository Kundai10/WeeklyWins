package com.example.wwapp.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieAnimationView
import com.example.wwapp.R


class BreatheFragment : Fragment() {
    private lateinit var lottieAnimationView: LottieAnimationView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
// Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_breathe, container, false)



        lottieAnimationView = view.findViewById(R.id.lottieAnimationView)
        //lottieAnimationView.setAnimation("breathe_animation.json")
        lottieAnimationView.playAnimation()

        return view
    }


}