package me.chronick.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.chronick.weatherapp.R
import me.chronick.weatherapp.databinding.FragmentDaysBinding
import me.chronick.weatherapp.databinding.FragmentHoursBinding

class DaysFragment : Fragment() {

    private lateinit var binding: FragmentDaysBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = DaysFragment()
    }
}