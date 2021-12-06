package com.pdxx.kotlinlearn.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.pdxx.kotlinlearn.R
import com.pdxx.kotlinlearn.adapter.MyAdapter
import com.pdxx.kotlinlearn.bean.PersonEntity

class ListActivity : AppCompatActivity() {

    private var mRecyclerView: RecyclerView? = null
    private var mMyAdapter: MyAdapter? = null
    private val personList = ArrayList<PersonEntity>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
//         recyclerView = findViewById<RecyclerView>(R.id.recycler)
        initData()
        initView()
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


    private fun initView() {
        mRecyclerView = findViewById<RecyclerView>(R.id.recycler)
        mMyAdapter = MyAdapter(personList)
        mMyAdapter!!.setOnItemClickListener(object : MyAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                Toast.makeText(this@ListActivity, "this is toast message", Toast.LENGTH_SHORT).show()
            }
        })
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
        mRecyclerView!!.adapter = mMyAdapter

    }


}