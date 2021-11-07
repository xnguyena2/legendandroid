package com.example.coffeordering.ui.main.Adapter

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.coffeordering.ui.main.SubView.MenuItem

class MenuAdapter (private val context: Context, private val menu: Array<String>,
                   private val itemCLickHandle: (String, Int)->Unit): BaseAdapter() {


    override fun getCount(): Int {
        return this.menu.size
    }

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0;
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        return MenuItem(context, menu[p0], itemCLickHandle);
    }
}