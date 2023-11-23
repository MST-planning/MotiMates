package com.example.motimates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.motimates.databinding.AchItemViewBinding
import com.example.motimates.databinding.FragmentAchievementMainBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class ViewHolder(val binding: AchItemViewBinding) : RecyclerView.ViewHolder(binding.root)

class Adapter(val datas: MutableList<String>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = datas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        ViewHolder(
            AchItemViewBinding.inflate(LayoutInflater.from(parent.context), parent,
            false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding= (holder as ViewHolder).binding
        binding.achContent.text = datas[position]
    }
}
class AchievementMain : Fragment() {
    lateinit var binding: FragmentAchievementMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAchievementMainBinding.inflate(layoutInflater, container, false)
        return inflater.inflate(R.layout.fragment_achievement_main, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AchievementMain.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AchievementMain().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}