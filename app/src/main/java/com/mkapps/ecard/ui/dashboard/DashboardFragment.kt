package com.mkapps.ecard.ui.dashboard

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mkapps.ecard.R
import com.mkapps.ecard.model.OrderItem
import com.mkapps.ecard.ui.ScannerActivity

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    private lateinit var adapter: HistoryItemAdapter
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val scanButton = root.findViewById<ImageButton>(R.id.scanButton)
        scanButton.setOnClickListener {
            val intent = Intent(requireActivity(),ScannerActivity::class.java)
            startActivity(intent)
        }
        val recyclerView: RecyclerView = root.findViewById(R.id.historyRV)
        recyclerView.layoutManager =  LinearLayoutManager(activity)


//        val textView: TextView = root.findViewById(R.id.text_dashboard)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
        adapter = HistoryItemAdapter(dashboardViewModel.historyItem.value,requireActivity())
        recyclerView.adapter = adapter
        dashboardViewModel.historyItem.observe(viewLifecycleOwner, object : Observer<ArrayList<OrderItem>>{
            override fun onChanged(t: ArrayList<OrderItem>?) {
                Log.i(DashboardFragment::class.simpleName, "data : ${t}")
                adapter = HistoryItemAdapter(t,requireActivity())
                recyclerView.adapter = adapter

            }

        })
        return root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pref = activity?.getSharedPreferences("ECard",Activity.MODE_PRIVATE)
        val token = pref?.getString("token","")
        if (!TextUtils.isEmpty(token)){
            dashboardViewModel.loadHistory(token!!)
        }
    }
}

