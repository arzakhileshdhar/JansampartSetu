package com.app.creativeapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.creativeapp.R
import com.app.creativeapp.adapter.RecyclerViewAdapter
import com.app.creativeapp.model.ListItem
import java.util.ArrayList

class HomeFragment : Fragment() {

    lateinit var recyclerid : RecyclerView
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        recyclerid = root.findViewById(R.id.recyclerid)
        recyclerid.layoutManager = LinearLayoutManager(activity)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerid.adapter =  RecyclerViewAdapter(getItemList())

         }

    private fun getItemList(): ArrayList<ListItem>? {
        var listOfItem = ArrayList<ListItem>()
        return listOfItem
    }
}