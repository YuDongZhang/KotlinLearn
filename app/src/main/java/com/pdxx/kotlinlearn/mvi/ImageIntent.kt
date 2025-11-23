package com.pdxx.kotlinlearn.mvi

/**
 * Step 2.2: 定义意图 (Intent)
 * 在MVI中，"Intent" 不是指Android的Activity Intent，而是代表用户的操作意图或想要触发的动作。
 * 我们使用一个密封类(sealed class)来定义所有可能的意图，这样做的好处是可以在when表达式中进行详尽的检查，确保处理了所有情况。
 */
sealed class ImageIntent {
    // 意图：首次加载数据
    object LoadInitial : ImageIntent()

    // 意图：下拉刷新数据
    object Refresh : ImageIntent()

    // 意图：上拉加载更多数据
    object LoadMore : ImageIntent()
}
