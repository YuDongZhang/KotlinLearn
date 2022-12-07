package com.pdxx.kotlinlearn.activity


import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.adapter.MyAdapter
import com.pdxx.kotlinlearn.bean.PersonEntity
import com.pdxx.kotlinlearn.databinding.ActivityListBinding


class ListActivity : AppCompatActivity() {


    private var mMyAdapter: MyAdapter? = null
    private val personList = ArrayList<PersonEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityListBinding>(/* activity = */ this, /* layoutId = */
                R.layout.activity_list
            )
        // setContentView(R.layout.activity_list)
//         recyclerView = findViewById<RecyclerView>(R.id.recycler)
        initData()
        binding.apply {
            mMyAdapter = MyAdapter(personList)
            mMyAdapter!!.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                    Toast.makeText(this@ListActivity, "this is toast message", Toast.LENGTH_SHORT)
                        .show()
                }
            })

            binding.recycler.layoutManager = LinearLayoutManager(this@ListActivity)
            binding.recycler.adapter = mMyAdapter
        }
    }

    private fun initData() {
        var person: PersonEntity = PersonEntity()
        person.name = "名字 1"
        person.age = 18

        personList.add(person)

        for (a in 0..9) {
            var personTwo = PersonEntity()
            personTwo.name = "for名字" + a
            personTwo.age = a
            personList.add(personTwo)
        }
    }


}