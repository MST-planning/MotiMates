package com.example.motimates

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motimates.databinding.FlowerItemViewBinding


class FlowerViewHolder(val binding: FlowerItemViewBinding) : RecyclerView.ViewHolder(binding.root) //꽃 리사이클러 뷰 홀더
class FlowerAdapter(val value: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //꽃 리스트 리사이클러 뷰 어댑터
    override fun getItemCount(): Int = value

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        FlowerViewHolder(
            FlowerItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val flowerList= mutableListOf<Int>(R.drawable.flower1, R.drawable.flower2, R.drawable.flower3) //꽃 이미지 리스트
        val binding= (holder as FlowerViewHolder).binding
        binding.flowerContent.setImageResource(flowerList[position%2])
        // 꽃 리스트 바인딩
        binding.flowerContent.setOnClickListener(){
            val intent= Intent(holder.itemView?.context, MyFlower::class.java)
            intent.putExtra("flowerimage", flowerList[position%2])
            binding.flowerContent.context.startActivity(intent)
        } //리사이클러뷰에 클릭 리스너 설정
    }
}