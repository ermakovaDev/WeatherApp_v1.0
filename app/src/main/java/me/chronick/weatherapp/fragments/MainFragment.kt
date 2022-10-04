package me.chronick.weatherapp.fragments

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import me.chronick.weatherapp.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { //validate permission
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    private fun permissionListener(){ // initialize Launcher. gave or did not give permission
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            Toast.makeText(activity, "Permission is $it", Toast.LENGTH_LONG).show()
        }
    }

    private fun checkPermission(){ // checking permission
        if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissionListener()
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) //requesting permission
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}