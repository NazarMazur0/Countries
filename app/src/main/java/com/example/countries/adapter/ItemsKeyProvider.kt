package com.example.countries.adapter
import androidx.recyclerview.selection.ItemKeyProvider
class ItemsKeyProvider(private val adapter: CountryListAdapter) : ItemKeyProvider<String>(
    SCOPE_MAPPED) {

    override fun getKey(position: Int): String =
        adapter.dataSet[position].code

    override fun getPosition(key: String): Int =
        adapter.dataSet.indexOfFirst { it.code == key }
}