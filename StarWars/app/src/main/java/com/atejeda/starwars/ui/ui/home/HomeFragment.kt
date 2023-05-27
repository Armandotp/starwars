package com.atejeda.starwars.ui.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.atejeda.starwars.data.model.Element
import com.atejeda.starwars.databinding.FragmentHomeBinding
import com.atejeda.starwars.interfaces.ElementEvents
import com.atejeda.starwars.ui.DetailActivity
import com.atejeda.starwars.ui.adapters.AdapterElements
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), ElementEvents {

    private var _binding: FragmentHomeBinding? = null
    companion object{
        var flagSearch = false
    }

    private val binding get() = _binding!!
    lateinit var adapter: AdapterElements
    var elements: MutableList<Element> = mutableListOf()
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        binding.searchEdittext.setOnEditorActionListener { userEntryTextView, actionId, _ ->
            hideKeyboard()
            adapter.getFilter().filter(userEntryTextView.text.toString().uppercase())
            return@setOnEditorActionListener true
        }
        binding.searchEdittext.doOnTextChanged { text, _, _, _ ->
            handleSearchBarTextChanged(text)
        }
        binding.cancelSearchButton.setOnClickListener {
            binding.searchEdittext.text!!.clear()
        }
        binding.allTypesChip.setOnClickListener {
            adapter.submitList(elements)
            binding.recyclerView.scrollToPosition(0)

        }

        initObservers()
        setAdapter()


        return root
    }


    fun setChips(){
        var types = mutableListOf<String>()

        elements.map {
            if(!types.contains(it.species)) {
                val chip = Chip(context)
                chip.text = it.species
                chip.isClickable = true
                chip.isCheckable = true
                chip.setOnCheckedChangeListener { buttonView, isChecked ->
                    adapter.submitList(elements)
                    binding.recyclerView.scrollToPosition(0)
                    adapter.getFilter().filter(it.species.uppercase())
                }
                binding.typesChipGroup.addView(chip)
                types.add(it.species)
            }
        }
    }

    private fun handleSearchBarTextChanged(text: CharSequence?) {
        with(binding) {
            if (text!!.isNotEmpty()) {
                cancelSearchButton.visibility = View.VISIBLE
            } else {
                cancelSearchButton.visibility = View.GONE
                adapter.submitList(elements)
                binding.recyclerView.scrollToPosition(0)
            }
        }
    }

    fun initObservers(){
        viewModel.allElements.observe(viewLifecycleOwner) {
            elements = it as MutableList<Element>
            adapter.submitList(elements)
            setChips()
            binding?.shimmerLayout?.stopShimmer()
            binding?.shimmerLayout?.visibility = View.GONE
        }
    }

    private fun setAdapter(){
        if(elements.isNullOrEmpty()){
            binding?.shimmerLayout?.startShimmer()
            binding?.shimmerLayout?.visibility = View.VISIBLE
        }else{
            binding?.shimmerLayout?.stopShimmer()
            binding?.shimmerLayout?.visibility = View.GONE

        }
        adapter = AdapterElements(this)
        val llm = LinearLayoutManager(activity)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.recyclerView.layoutManager = llm
        binding.recyclerView.adapter = adapter
        binding.recyclerView.hasFixedSize()

        viewModel.getAll()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onclickElement(element: Element, postion: Int) {
        startActivity(
            Intent(activity, DetailActivity::class.java)
            .putExtra("element",element)
            .putExtra("position",postion))
    }
}