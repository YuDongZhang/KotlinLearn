package com.pdxx.kotlinlearn.moduleFunny

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pdxx.kotlinlearn.databinding.ListItemJokeBinding
import com.pdxx.kotlinlearn.moduleFunny.model.Joke

/**
 * PagingDataAdapter: 一个特殊的RecyclerView Adapter，用于展示PagingData。
 * 它能自动处理分页数据的加载和UI更新。
 *
 * - 第一个泛型参数 `Joke`: 列表中每个item的数据类型。
 * - 第二个泛型参数 `JokeViewHolder`: ViewHolder的类型。
 */
class JokePagingAdapter : PagingDataAdapter<Joke, JokePagingAdapter.JokeViewHolder>(JOKE_COMPARATOR) {

    /**
     * ViewHolder: 持有Item View的引用。
     */
    class JokeViewHolder(private val binding: ListItemJokeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(joke: Joke?) {
            // Paging 3支持占位符(placeholders)，当数据还未加载时，item可能为null
            if (joke != null) {
                binding.tvJokeAuthor.text = joke.author
                binding.tvJokeText.text = joke.text
            } else {
                // 可以设置占位符的UI
                binding.tvJokeAuthor.text = "Loading..."
                binding.tvJokeText.text = ""
            }
        }
    }

    /**
     * 创建ViewHolder实例。
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val binding = ListItemJokeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(binding)
    }

    /**
     * 将数据绑定到ViewHolder。
     */
    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        // getItem()是PagingDataAdapter提供的方法，用于获取指定位置的数据项
        val joke = getItem(position)
        holder.bind(joke)
    }

    companion object {
        /**
         * DiffUtil.ItemCallback: PagingDataAdapter的核心机制之一。
         * 它告诉Adapter如何计算新旧两个数据列表之间的差异，以便高效地更新UI。
         * 这避免了使用`notifyDataSetChanged()`进行全局刷新，性能更高。
         */
        private val JOKE_COMPARATOR = object : DiffUtil.ItemCallback<Joke>() {
            /**
             * 判断两个item是否是同一个对象。通常通过比较唯一ID来实现。
             */
            override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
                return oldItem.sid == newItem.sid
            }

            /**
             * 判断两个item的内容是否完全相同。如果`areItemsTheSame`返回true，此方法才会被调用。
             */
            override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
                return oldItem == newItem
            }
        }
    }
}
