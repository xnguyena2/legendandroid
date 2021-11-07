package com.example.coffeordering.ui.main.Adapter

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView

class GridTableAdapter(private val context: Context, private val typeStr: String, private var tableNo: Int,
                       private val itemCLickHandle: (no: Int)->Unit): BaseAdapter() {


    override fun getCount(): Int {
        return this.tableNo
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0;
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val tableText = TextView(this.context).apply {
            setOnClickListener{
                itemCLickHandle(p0)
            }
        }
        tableText.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        );
        tableText.gravity = Gravity.CENTER;
        tableText.setPadding(0, 30, 0,30)
        val tableIndex: Int = p0+1
        tableText.text = "${typeStr} ${tableIndex.toString()}"
        tableText.setTag(p0)
        return tableText;
    }

    fun setTableNo(no: Int){
        this.tableNo = no
        notifyDataSetChanged()
    }
}