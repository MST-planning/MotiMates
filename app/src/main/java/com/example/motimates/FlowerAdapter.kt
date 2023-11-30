package com.example.motimates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motimates.databinding.FlowerItemViewBinding


class FlowerViewHolder(val binding: FlowerItemViewBinding) : RecyclerView.ViewHolder(binding.root) //꽃 리사이클러 뷰 홀더
class FlowerAdapter(val images: MutableList<Int>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //꽃 리스트 리사이클러 뷰 어댑터
    override fun getItemCount(): Int = images.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        FlowerViewHolder(
            FlowerItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding= (holder as FlowerViewHolder).binding
        binding.flowerContent.setImageResource(images[position])
        // 꽃 리스트 바인딩
    }
}