package com.example.countries.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.ActionMode
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.countries.R
import com.example.countries.adapter.CountryListAdapter
import com.example.countries.adapter.ItemsDetailsLookup
import com.example.countries.adapter.ItemsKeyProvider
import com.example.countries.databinding.FragmentSelectionBinding
import com.example.countries.viewmodel.CountriesViewModel
import com.example.countries.viewmodel.CountriesViewModelFactory

class FragmentSelection(val appContext:Context) : Fragment(){
    private var _binding: FragmentSelectionBinding? = null
    private val binding get() = _binding!!
    private lateinit var  recyclerView:RecyclerView
    private lateinit var viewModel: CountriesViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSelectionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel= ViewModelProvider(activity!!.viewModelStore, CountriesViewModelFactory(appContext))[CountriesViewModel::class.java]
        recyclerView=binding.recyclerCountries
        val lm=LinearLayoutManager(appContext)
        lm.orientation=RecyclerView.VERTICAL
       recyclerView.layoutManager=lm
        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        var tracker: SelectionTracker<String>?
        var adapter: CountryListAdapter?
        dividerItemDecoration.setDrawable(resources.getDrawable(androidx.constraintlayout.widget.R.drawable.abc_list_divider_material))
        recyclerView.addItemDecoration(dividerItemDecoration)
        viewModel.countryList.observe(viewLifecycleOwner) { data ->
            run {
                adapter= CountryListAdapter(data,appContext)
                recyclerView.adapter = adapter
                tracker = SelectionTracker.Builder(
                    "selectionItem",
                    recyclerView,
                    ItemsKeyProvider(adapter!!),
                    ItemsDetailsLookup(recyclerView),
                    StorageStrategy.createStringStorage()
                ).withSelectionPredicate(
                    SelectionPredicates.createSelectSingleAnything()
                ).build()
                tracker!!.addObserver(
                    object : SelectionTracker.SelectionObserver<String>() {
                        override fun onSelectionChanged() {
                            super.onSelectionChanged()
                            if(adapter?.tracker?.selection?.size()==1){
                                Log.d("click", adapter?.tracker?.selection?.first() as String )
                                viewModel.currentCountryCode.value=adapter?.tracker?.selection?.first() as String
                                Log.d("click", viewModel.currentCountryCode.value!!)

                            }
                        }
                    })

                adapter!!.tracker = tracker
                adapter!!.tracker?.select(viewModel.countryList.value!![0]!!.code)


            }
        }
        viewModel.getCountries()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}