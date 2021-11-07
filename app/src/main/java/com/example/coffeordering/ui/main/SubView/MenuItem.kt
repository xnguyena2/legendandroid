package com.example.coffeordering.ui.main.SubView

import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet




class MenuItem (context: Context, val productName: String, val orderProduct: (String,Int)->Unit): ConstraintLayout(context) {

    var orderNumber: Int = 0

    val label = TextView(context).apply {
        text = productName
        this.id = generateViewId()
        layoutParams = LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )
    }

    val subBtn = Button(this.context).apply {
        this.id = generateViewId()
        text = "-"
        layoutParams = LinearLayout.LayoutParams(
            0,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        setOnClickListener{
            if(orderNumber>0) {
                orderNumber--
                orderProduct(orderNumber)
            }
        }
    }

    val addBtn = Button(this.context).apply {
        this.id = generateViewId()
        text = "+"
        layoutParams = LinearLayout.LayoutParams(
            100,
            ViewGroup.LayoutParams.WRAP_CONTENT,
        )
        setOnClickListener{
            orderNumber++
            orderProduct(orderNumber)
        }
    }

    fun orderProduct(no: Int){
        if(no == 0){
            label.text = productName
        }else{
            label.text = "${productName} (${no})"
        }
        orderProduct(productName, no)
    }

    init {

        // View Hierarchy
        addView(label)
        addView(subBtn)
        addView(addBtn)

        // Layout
        val constraintSet = ConstraintSet()
        constraintSet.clone(this)
        constraintSet.connect(addBtn.id,ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT,0)
        constraintSet.connect(subBtn.id,ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT,0)
        constraintSet.connect(label.id,ConstraintSet.RIGHT,addBtn.id,ConstraintSet.LEFT,0)
        constraintSet.connect(label.id,ConstraintSet.LEFT,subBtn.id,ConstraintSet.RIGHT,0)
        constraintSet.applyTo(this)

    }
}