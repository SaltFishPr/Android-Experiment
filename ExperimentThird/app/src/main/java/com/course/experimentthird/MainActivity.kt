package com.course.experimentthird

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var mDataSet: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDataSet()
        initView()
    }

    private fun initView() {
        recyclerView = findViewById(R.id.rv_grid)
        layoutManager = GridLayoutManager(this, MyValues.weekDisplayNum)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = MyAdapter(this, mDataSet)
    }

    private fun initDataSet() {
        mDataSet =
            Array(MyValues.weekDisplayNum * (MyValues.courseNum + 1)) { i -> "This is element # $i" }
    }
}
