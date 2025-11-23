package com.pdxx.kotlinlearn.activity.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.pdxx.kotlinlearn.databinding.FragmentDetailsBinding
import kotlin.getValue

/**
 * Step 2: 创建目标Fragment (DetailsFragment)
 * 这是我们将要从HomeFragment导航到的页面。
 */
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    // Step 6: 接收参数 (Safe Args方式)
    // 通过 by navArgs() 委托，我们可以安全地、类型安全地获取导航传递过来的参数。
    // 'DetailsFragmentArgs' 这个类是Navigation Safe Args插件根据navigation graph自动生成的。
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- 演示接收参数的说明文字 ---
        val explanationText = ""
            .plus("欢迎来到详情页!\n\n")
            .plus("4. Arguments (参数):\n")
            .plus("   我们在Graph的Destination中定义了参数，使得可以在页面间传递数据。\n\n")
            .plus("5. Safe Args (安全参数):\n")
            .plus("   这是一个Gradle插件，它会生成简单的Directions和Args类，让我们可以在编译时检查参数的类型安全，避免运行时错误。\n\n")
            .plus("如此处，我们通过 `by navArgs()` 轻松获取了传递过来的参数。\n\n")

        // 从args中获取传递过来的数据并显示
        val itemId = args.itemId
        binding.textDetails.text = explanationText + "收到的参数(itemId): $itemId"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
