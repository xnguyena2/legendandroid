package com.example.coffeordering.ui.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.coffeordering.databinding.ActivityMenuBinding
import com.example.coffeordering.ui.main.Adapter.GridTableAdapter
import com.example.coffeordering.ui.main.Adapter.MenuAdapter
import com.example.coffeordering.ui.main.Socket.MySocket
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MenuActivity : AppCompatActivity() {

    val orderProduct = mutableMapOf<String, Int>()

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridTableAdapter = intent.getStringArrayExtra(ARG_TABLE_NO)?.let {
            MenuAdapter(this, it){ productName, no->
                Log.d("Product click", "product: ${productName}, NO: ${no}")
                orderProduct[productName] = no
            }
        }
        binding.listProduct.adapter = gridTableAdapter

        val orderBtn: Button = binding.buttonOrder
        orderBtn.apply {
            setOnClickListener {
                GlobalScope.launch {
                    val order = orderProduct.filter { kv ->
                        kv.value > 0
                    }
                    MySocket.sendMSD(Gson().toJson(order), {
                        finish();
                    }, {
                        GlobalScope.launch {
                            withContext(Dispatchers.Main) {
                                // Show Toast here
                                Toast.makeText(
                                    context,
                                    "Không thể kết nối máy chủ!!",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                    })
                }
            }
        }

    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_TABLE_NO = "tableno"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param tableNo table number.
         * @return A new instance of fragment TableSelectFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun start(context: Context, nenu: Array<String>) {

            val intent = Intent(context, MenuActivity::class.java).apply {
                putExtra(ARG_TABLE_NO, nenu)
            }
            context.startActivity(intent)

        }
    }
}