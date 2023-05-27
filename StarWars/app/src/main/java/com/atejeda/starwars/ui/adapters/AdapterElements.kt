package com.atejeda.starwars.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atejeda.starwars.data.model.Element
import com.atejeda.starwars.databinding.ItemBinding
import com.atejeda.starwars.interfaces.ElementEvents
import com.atejeda.starwars.ui.ui.home.HomeFragment
import com.atejeda.starwars.utils.load
import com.bumptech.glide.Glide

class AdapterElements(private val elementEvents: ElementEvents)
    : ListAdapter<Element, AdapterElements.CustomViewHolder>(DiffUtilCallback), Filterable {

    var all: MutableList<Element> = mutableListOf()
    var filter: MutableList<Element> = mutableListOf()

    override fun submitList(list: MutableList<Element>?) {
        super.submitList(list)
        all = list!!
    }

    private object DiffUtilCallback: DiffUtil.ItemCallback<Element>() {
        override fun areItemsTheSame(oldItem: Element, newItem:Element): Boolean {
            return true
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Element, newItem: Element): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val itemBinding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }

    inner class CustomViewHolder(item: View): RecyclerView.ViewHolder(item) {

        private lateinit var binding: ItemBinding


        constructor(itemBinding: ItemBinding) : this(itemBinding.root) {
            this.binding = itemBinding
        }

        @SuppressLint("NewApi")
        fun bind(element: Element, position: Int) = with(binding){

            binding.name.text = element.name
            binding.gender.text = element.gender
            binding.species.text = element.species
            //binding.img.load(element.image)

            binding.cardView.setOnClickListener {
                elementEvents.onclickElement(element,position)
            }

        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                filter = if (charString.isEmpty()) all else {
                    val filteredList = ArrayList<Element>()
                    all
                        .filter {

                            (it.name.toUpperCase().contains(constraint!!)
                                    ) or (it.species.toUpperCase() == (constraint!!)
                                    )
                        }
                        .forEach { filteredList.add(it) }
                    filteredList

                }
                return FilterResults().apply { values = filter }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                filter = if (results?.values == null)
                    all
                else
                    results.values as ArrayList<Element>


                submitList(filter)
            }
        }
    }

}