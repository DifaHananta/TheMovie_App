package com.project.themovie.highRated

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.themovie.R
import com.project.themovie.databinding.FragmentHighRatedBinding

class HighRatedFragment : Fragment() {

    private lateinit var binding: FragmentHighRatedBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHighRatedBinding.inflate(inflater, container, false)

        return  binding.root
    }

}