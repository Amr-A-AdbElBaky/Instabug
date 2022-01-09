package com.nagwa.instabugchallenge.modules.words.presentation.view.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nagwa.instabugchallenge.databinding.ItemWordBinding
import com.nagwa.instabugchallenge.modules.words.domain.entity.WordEntity
import java.util.ArrayList


class WordsAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data =  listOf<WordEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return WordViewHolder(
            ItemWordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = data[position]
        (holder as WordViewHolder).bind(model)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class WordViewHolder(private val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(wordEntity: WordEntity){
                binding.tvWord.text = wordEntity.name
                binding.tvCount.text = wordEntity.count.toString()

            }
    }

    fun setItems(words :List<WordEntity>){
       data = words
       notifyDataSetChanged()
    }

    fun reset() {
        data = emptyList()
        notifyDataSetChanged()
    }
}