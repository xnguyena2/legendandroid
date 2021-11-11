package com.example.coffeordering.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.coffeordering.R
import com.example.coffeordering.databinding.FragmentTableSelectBinding
import com.example.coffeordering.ui.main.Adapter.GridTableAdapter
import com.example.coffeordering.ui.main.Config.AppValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.logging.Logger


/**
 * A simple [Fragment] subclass.
 * Use the [TableSelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TableSelectFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var tableNo: Int = 0

    private var _binding: FragmentTableSelectBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tableNo = it.getInt(ARG_TABLE_NO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTableSelectBinding.inflate(inflater, container, false)
        val root = binding.root

        val gridTableAdapter = GridTableAdapter(this.requireContext(), "Bàn", 0){
            Log.d("Table click", "table: ${it}")
            MenuActivity.start(this.requireContext(), "Ban So: ${it+1}", AppValue.coffeConfig.listProduct)
        }
        binding.listTable.adapter = gridTableAdapter
        AppValue.ObserverTableChagne { no->
            GlobalScope.launch {
                withContext(Dispatchers.Main) {
                    gridTableAdapter.setTableNo(no)
                }
            }
        }
        return root
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
        fun newInstance(tableNo: Int) =
            TableSelectFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_TABLE_NO, tableNo)
                }
            }
    }
}