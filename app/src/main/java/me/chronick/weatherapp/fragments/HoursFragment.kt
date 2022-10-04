package me.chronick.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.chronick.weatherapp.R
import me.chronick.weatherapp.databinding.FragmentHoursBinding
import me.chronick.weatherapp.databinding.FragmentMainBinding

class HoursFragment : Fragment() {

    private lateinit var binding: FragmentHoursBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance()= HoursFragment()
    }
}