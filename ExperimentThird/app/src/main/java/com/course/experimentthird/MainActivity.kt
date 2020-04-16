package com.course.experimentthird

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var dataSet: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDataset()
        initView()
    }

    private fun initView() {
        recyclerView = findViewById(R.id.rv_grid)
        layoutManager = GridLayoutManager(this, 2 * MyValues.weekDisplayNum + 1)
        recyclerView.layoutManager = layoutManager
        (layoutManager as GridLayoutManager).spanSizeLookup =
            object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position % (MyValues.weekDisplayNum + 1) == 0) {
                        1
                    } else {
                        2
                    }
                }
            }
        (layoutManager as GridLayoutManager).spanSizeLookup
        recyclerView.adapter = MyAdapter(this, dataSet)


    }

    private fun initDataset() {
        dataSet =
            Array((MyValues.weekDisplayNum + 1) * MyValues.courseNum) { i -> "This is element # $i" }
    }
}
