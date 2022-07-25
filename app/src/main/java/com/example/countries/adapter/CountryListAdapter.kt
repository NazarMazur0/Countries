package com.example.countries.adapter
import kotlin.UInt
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.utils.ImageLoader
import com.example.countries.R
import com.example.rocketreserver.AllCountriesQuery

class CountryListAdapter( val dataSet: List<AllCountriesQuery.Country>,private val context: Context ) : RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {
    var tracker: SelectionTracker<String>? = null

    private val imageLoader = ImageLoader(context)
   inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        val textView: TextView

        init {
            textView = view.findViewById(R.id.CountryName)
            imageView=view.findViewById(R.id.flagImageView)

        }
       fun bind(item: AllCountriesQuery.Country){
           imageLoader.loadCountryImage(imageView,item.name, 0U)
          textView.text = item.name
           itemView.tag=item.code
           tracker?.let {
               if (it.isSelected(item.code)) {
                   itemView.setBackgroundColor(Color.parseColor("#bdc4cb"))
               } else {
                   itemView.background = null
               }
           }

       }
        fun getItem(): ItemDetailsLookup.ItemDetails<String> =
            object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getPosition(): Int = bindingAdapterPosition
                override fun getSelectionKey(): String =  dataSet[bindingAdapterPosition].code
            }
    }




    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.country_list_element, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //injection
        viewHolder.bind(dataSet[position])
    }


    override fun getItemCount() = dataSet.size

}
