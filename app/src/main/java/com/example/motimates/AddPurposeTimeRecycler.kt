package com.example.motimates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motimates.databinding.AddPurposeTimeBinding

class AddPurposeTimeRecycler (val binding: AddPurposeTimeBinding): RecyclerView.ViewHolder(binding.root) {}

class ItemAdapter(val data: MutableList<List<Int>>): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun getItemCount(): Int = data.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    = AddPurposeTimeRecycler(AddPurposeTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as AddPurposeTimeRecycler).binding

        binding.timeString.text = "${data[0]}시 ${data[1]}분"
    }
}