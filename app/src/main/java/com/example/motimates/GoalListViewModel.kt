package com.example.motimates

import androidx.lifecycle.ViewModel

class GoalListViewModel : ViewModel() {
    var headerText: String = ""
    var goalList: List<String> = emptyList()
}
