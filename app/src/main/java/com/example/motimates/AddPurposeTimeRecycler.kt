package com.example.motimates

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.motimates.databinding.AddPurposeTimeBinding

class AddPurposeTimeRecycler (val binding: AddPurposeTimeBinding): RecyclerView.ViewHolder(binding.root)

class TimeAdapter: ListAdapter<List<Int>, RecyclerView.ViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        AddPurposeTimeRecycler(
            AddPurposeTimeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as AddPurposeTimeRecycler).binding
        val item = getItem(position)
        binding.timeString.text = "${getItem(position)[0]}시 ${getItem(position)[1]}분"

        binding.del.setOnClickListener{
            val newList = currentList.toMutableList()
            newList.remove(item)
            submitList(newList)
        }
    }

    override fun submitList(list: MutableList<List<Int>>?) {
        Log.d("currentList", "submit 호출")
        super.submitList(list)
        Log.d("currentList", "원소 수는 ${currentList.count()}")
    }

    companion object {
        val diffUtil = object:DiffUtil.ItemCallback<List<Int>>() {
            override fun areItemsTheSame(oldItem: List<Int>, newItem: List<Int>): Boolean {
                return (oldItem[0] == newItem[0]) && (oldItem[1] == newItem[1])
            }

            override fun areContentsTheSame(oldItem: List<Int>, newItem: List<Int>): Boolean {
                return (oldItem[0] == newItem[0]) && (oldItem[1] == newItem[1])
            }

        }
    }
}

