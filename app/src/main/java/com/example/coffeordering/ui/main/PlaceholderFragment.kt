package com.example.coffeordering.ui.main

import android.R.attr
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coffeordering.R
import com.example.coffeordering.databinding.FragmentMainBinding
import android.R.attr.port
import android.content.Context
import android.graphics.Color

import android.system.Os.socket
import android.transition.Visibility
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import com.example.coffeordering.ui.main.Config.AppValue
import com.example.coffeordering.ui.main.Socket.MySocket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.Socket
import java.net.UnknownHostException


/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val notificationLabel: TextView = binding.notificationLabel
        val ipInput: EditText = binding.editText
        val connectBtn: Button = binding.buttonConnect


        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        sharedPref?.apply {
            getString(getString(R.string.host_ip), "192.168.1.1").also { ipInput.setText(it) }
        }


        connectBtn.setOnClickListener {

            val button = it as Button
            button.isEnabled = false
            notificationLabel.visibility = View.GONE

            GlobalScope.launch {
                val hostname = ipInput.text.toString()

                MySocket.setIP(hostname, { response ->
                    AppValue.LoadCoffeConfig(response)
                    Log.i("AppConfig", AppValue.coffeConfig.tableNo.toString())
                    Log.i("AppConfig", AppValue.coffeConfig.listProduct.toString())
                    GlobalScope.launch {
                        withContext(Dispatchers.Main) {
                            button.isEnabled = true;
                            notificationLabel.text = "Kết nối máy chủ thành công!!"
                            notificationLabel.setTextColor(Color.parseColor("#115511"))
                            notificationLabel.visibility = View.VISIBLE

                            val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                            if(sharedPref!=null){
                                with (sharedPref.edit()) {
                                    putString(getString(R.string.host_ip), hostname)
                                    apply()
                                }
                            }
                        }
                    }
                }) {
                    GlobalScope.launch {
                        withContext(Dispatchers.Main) {
                            // Show Toast here
                            button.isEnabled = true;
                            notificationLabel.visibility = View.VISIBLE
                            notificationLabel.text = "Không thể kết nối máy chủ!!"
                            notificationLabel.setTextColor(Color.parseColor("#FF0000"))
                            Toast.makeText(
                                context,
                                "Không thể kết nối máy chủ!!",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }
            }
        }

//        val textView: TextView = binding.ipLabel
//        pageViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}