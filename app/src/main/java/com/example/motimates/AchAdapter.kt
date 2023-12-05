package com.example.motimates

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motimates.databinding.AchItemViewBinding

class AchViewHolder(val binding: AchItemViewBinding) : RecyclerView.ViewHolder(binding.root) //목표 리사이클러 뷰 홀더

class AchAdapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //목표 리스트 리사이클러 뷰 어댑터
    override fun getItemCount(): Int = datas.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        AchViewHolder(
            AchItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding= (holder as AchViewHolder).binding
        binding.achContent.text = datas[position]
        binding.achContent.setOnClickListener(){
            val intent= Intent(binding.achContent.context, Achievement::class.java)
            binding.achContent.context.startActivity(intent) //목표 리스트 리사이클러 뷰 클릭 이벤트 처리(목표 수행 페이지로 이동)
        }
    }
}

