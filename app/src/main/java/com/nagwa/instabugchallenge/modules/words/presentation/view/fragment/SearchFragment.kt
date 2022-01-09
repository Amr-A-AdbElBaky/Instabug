package com.nagwa.instabugchallenge.modules.words.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.nagwa.instabugchallenge.R
import com.nagwa.instabugchallenge.core.di.module.InstaViewModelFactory
import com.nagwa.instabugchallenge.core.extensions.gone
import com.nagwa.instabugchallenge.core.extensions.hideKeyboard
import com.nagwa.instabugchallenge.core.extensions.visible
import com.nagwa.instabugchallenge.databinding.FragmentSearchBinding
import com.nagwa.instabugchallenge.modules.words.presentation.view.adapter.WordsAdapter
import com.nagwa.instabugchallenge.modules.words.presentation.viewmodel.WordsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class SearchFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: InstaViewModelFactory

    private val wordsAdapter = WordsAdapter()

    private val wordsViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[WordsViewModel::class.java]
    }

    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initStoriesAdapter()
        initActions()
        initObservations()
    }


    private fun initViews() {
        showKeyboard()
    }

    private fun showKeyboard() {
        binding.svSearch.requestFocus()
    }

    private fun resetSearchPage() {
        binding.svSearch.setQuery("", false)
        wordsAdapter.reset()
    }

    private fun initActions() {
        binding.tvCancel.setOnClickListener {
            resetSearchPage()
        }
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchStories()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun searchStories() {
        if (binding.svSearch.query.isNotBlank()) {
            wordsViewModel.searchStories(
                query = binding.svSearch.query.toString().trim()
            )
        }
        requireActivity().hideKeyboard()
    }

    private fun initObservations() {
        wordsViewModel.wordsSearchEvent.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                wordsAdapter.reset()
                binding.layoutLoadingMessage.tvMsg.apply {
                    visible()
                    text = getString(R.string.msg_no_words)
                }
            } else{
                binding.layoutLoadingMessage.tvMsg.gone()
                wordsAdapter.setItems(it)
            }
        }
    }

    private fun initStoriesAdapter() {
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        )
        binding.rvWords.apply {
            adapter = wordsAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }
    }
}