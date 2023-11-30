package com.example.motimates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.motimates.databinding.ActivityGoalListBinding

class GoalListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGoalListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ViewModel 생성 및 데이터 설정
        val viewModel = GoalListViewModel()
        viewModel.headerText = "내가 달성 중인 목표"
        viewModel.goalList = listOf("목표 1", "목표 2", "목표 3")

        // 데이터 바인딩에 ViewModel 할당
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }
}
