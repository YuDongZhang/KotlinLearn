package com.pdxx.kotlinlearn.activity.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- 演示Navigation用法的说明文字 ---
        val explanationText = ""
            .plus("欢迎来到Jetpack Navigation组件教学!\n\n")
            .plus("1. Navigation Graph (导航图):\n")
            .plus("   这是导航的可视化地图 (res/navigation/mobile_navigation.xml)。它定义了所有页面(Destinations)以及它们之间的跳转路径(Actions)。\n\n")
            .plus("2. NavHostFragment:\n")
            .plus("   这是一个特殊的Fragment，作为其他所有目标Fragment的容器。它被放置在Activity的布局中。\n\n")
            .plus("3. NavController:\n")
            .plus("   导航的“大脑”。我们用它来触发导航事件，例如 `navController.navigate(...)`。\n\n")
            .plus("点击下面的按钮，将会执行在Graph中定义的Action，跳转到详情页并传递一个ID。")

        binding.textHome.text = explanationText
        binding.buttonToDetails.text = "跳转到详情页 (ID: ID-12345)"

        // --- 导航点击事件 ---
        binding.buttonToDetails.setOnClickListener {
            // 使用Safe Args生成的操作类来创建导航动作，并传入参数
            val action = HomeFragmentDirections.actionHomeToDetails(itemId = "ID-12345")
            // 执行导航
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
