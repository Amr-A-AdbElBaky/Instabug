package com.nagwa.instabugchallenge.modules.words.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.nagwa.instabugchallenge.core.di.module.InstaViewModelFactory
import com.nagwa.instabugchallenge.core.extensions.gone
import com.nagwa.instabugchallenge.core.extensions.visible
import com.nagwa.instabugchallenge.databinding.FragmentHomeBinding
import com.nagwa.instabugchallenge.modules.words.domain.entity.WordEntity
import com.nagwa.instabugchallenge.modules.words.domain.exceptions.NoInternetError
import com.nagwa.instabugchallenge.modules.words.presentation.view.adapter.WordsAdapter
import com.nagwa.instabugchallenge.modules.words.presentation.viewmodel.WordsViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration





class HomeFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: InstaViewModelFactory

    private val wordsAdapter = WordsAdapter()

    private val wordsViewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory)[WordsViewModel::class.java]
    }
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWordsAdapterViews()
        observeHome()
        initObservations()
        if (wordsViewModel.wordsSuccessEvent.value == null && savedInstanceState == null) {
            requestWords()
        }
    }


    private fun initObservations() {
        wordsViewModel.wordsErrorEvent.observe(viewLifecycleOwner, {
            binding.layoutLoadingMessage.pbHome.gone()
            binding.layoutLoadingMessage.tvMsg.apply {
                visible()
                text = it.message
            }
            if (it is NoInternetError)
                binding.layoutLoadingMessage.tvMsg.setOnClickListener {
                    requestWords()
                }

        })
        wordsViewModel.sortClickEvent.observe(viewLifecycleOwner){
            wordsViewModel.requestWords(it)
        }
    }

    private fun requestWords() {
        binding.layoutLoadingMessage.pbHome.visible()
        wordsViewModel.requestWords()
    }

    private fun observeHome() {

        wordsViewModel.wordsSuccessEvent.observe(viewLifecycleOwner, {
            showWords(it)
        })
        wordsViewModel.wordsErrorEvent.observe(viewLifecycleOwner, {
            binding.layoutLoadingMessage.apply {
                pbHome.gone()
                tvMsg.text = it.message
            }
        })
    }

    private fun showWords(it: List<WordEntity>?) {
        binding.layoutLoadingMessage.apply {
            pbHome.gone()
            tvMsg.gone()
        }

        if (it.isNullOrEmpty())
            binding.layoutLoadingMessage.tvMsg.apply {
                visible()
                text = context.getString(com.nagwa.instabugchallenge.R.string.msg_no_words)
            }
        else
            wordsAdapter.setItems(it)
    }

    private fun initWordsAdapterViews() {
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            LinearLayoutManager.VERTICAL
        )
        binding.rvWords.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = wordsAdapter
            addItemDecoration(dividerItemDecoration)
        }
    }

}