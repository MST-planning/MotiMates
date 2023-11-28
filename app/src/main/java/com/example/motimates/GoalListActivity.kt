//package com.example.motimates
//
//// GoalListActivity.kt
//import android.os.Bundle
//import android.widget.ArrayAdapter
//import androidx.appcompat.app.AppCompatActivity
//import com.example.motimates.databinding.GoalListBinding
//
//class GoalListActivity : AppCompatActivity() {
//
//    private lateinit var binding: GoalListBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = GoalListBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // 가상의 목표 데이터 생성 (실제 데이터로 교체 할건지, 더미 데이터 쓸건지)
//        val goals = listOf(
//            Goal("목표1", "2023-12-31", R.drawable.goal_image1),
//            Goal("목표2", "2024-01-31", R.drawable.goal_image2),
//            // 추가적인 목표들...
//        )
//
//        // 목표 목록을 표시할 어댑터 생성
//        val adapter = ArrayAdapter(this, R.layout.goal_list_item, goals)
//
//        // ListView에 어댑터 설정
//        binding.goalListView.adapter = adapter
//    }
//}
