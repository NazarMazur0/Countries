package com.example.countries.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import com.example.countries.databinding.ActivityMainBinding
import com.example.countries.viewmodel.CountriesViewModel
import com.example.countries.viewmodel.CountriesViewModelFactory

class MainActivity : AppCompatActivity() {
    private var frameInfo :FrameLayout? = null
    private var frameSelection :FrameLayout? = null
    private lateinit var  viewModel:CountriesViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel= ViewModelProvider(this, CountriesViewModelFactory(this))[CountriesViewModel::class.java]
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        frameInfo=binding.countryInfo
        frameSelection=binding.countrySelection
        val fm = supportFragmentManager
        fm
            .beginTransaction()
            .add((frameInfo as FragmentContainerView).id, FragmentInfo(this), "info")
            .add((frameSelection as FragmentContainerView).id, FragmentSelection(this), "selection")
            .commit()

    }
}