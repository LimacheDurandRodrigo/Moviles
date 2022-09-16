package com.example.proyectoagriaplicacion.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.proyectoagriaplicacion.R


class HistoryFragment : Fragment() {
    private lateinit var spinner: Spinner
    private val context = null


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val root = inflater.inflate(R.layout.fragment_history, container, false)
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.historySpinner, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner = root.findViewById(R.id.spinner)
        spinner.setAdapter(adapter)
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> readTransactions("Compras")
                    1 -> readTransactions("Ventas")
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        })
        return root

    }

    private fun readTransactions(s: String) {


    }


}