package com.example.countries.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rocketreserver.AllCountriesQuery
import com.example.rocketreserver.CountryQuery
import kotlinx.coroutines.launch

class CountriesViewModel : ViewModel() {
    private val graphQLRepository = GraphQLRepository()
    var countryList=MutableLiveData<List<AllCountriesQuery.Country>>()
    var  currentCountryCode=MutableLiveData<String>()
    var  currentCountry=MutableLiveData<CountryQuery.Country>()
    fun getCountries(){
        viewModelScope.launch{ countryList.value= graphQLRepository.getAllCountries()!!.sortedWith(({o1,o2->
            o1.name.compareTo(o2.name)
        }))}
    }
    fun getCountry(){
        viewModelScope.launch{ currentCountry.value= graphQLRepository.getCountry(currentCountryCode.value!!)}

    }
}

