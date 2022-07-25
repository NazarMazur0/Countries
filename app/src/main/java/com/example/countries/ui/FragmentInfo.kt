package com.example.countries.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.countries.databinding.FragmentInfoBinding
import com.example.countries.utils.ImageLoader
import com.example.countries.viewmodel.CountriesViewModel
import com.example.countries.viewmodel.CountriesViewModelFactory
import com.example.rocketreserver.CountryQuery

class FragmentInfo(val appContext: Context) : Fragment() {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var name: TextView
    private lateinit var continent: TextView
    private lateinit var capital: TextView
    private lateinit var phoneCode: TextView
    private lateinit var phone: TextView
    private lateinit var currency: TextView
    private lateinit var language: TextView
    private lateinit var flag: ImageView
    private lateinit var viewModel: CountriesViewModel
    //injection
    private val imageLoader = ImageLoader(appContext)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(activity!!.viewModelStore, CountriesViewModelFactory(appContext))[CountriesViewModel::class.java]
        name=binding.countryName
        continent=binding.continent
        capital=binding.capital
        phoneCode=binding.code
        phone=binding.phone
        currency=binding.currency
        language=binding.language
        flag=binding.flag
        viewModel.currentCountryCode.observe(viewLifecycleOwner){
            Log.d("click",it+" from fragmentInfo")
            viewModel.getCountry()
        }

        viewModel.currentCountry.observe(viewLifecycleOwner){
            showCountryInfo(it)
        }
    }
    fun showCountryInfo(country: CountryQuery.Country){

        name.text=country.name
        continent.text= country.continent.name
        capital.text= country.capital ?: ""
        phoneCode.text="+"+country.phone
        phone.text=""+country.name.hashCode()
        currency.text= country.currency ?: ""
        if(country.languages.isNotEmpty())
            language.text =  if(country.languages.size>1) country.languages[0].name+" +"+(country.languages.size-1) else  country.languages[0].name
        else language.text=""
        imageLoader.loadCountryImage(flag,country.name,15U)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}