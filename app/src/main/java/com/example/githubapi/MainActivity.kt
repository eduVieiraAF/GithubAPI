package com.example.githubapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.githubapi.R.layout.activity_main

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(activity_main)

        supportActionBar?.hide()
        initRecyclerView()
        initViewModel()
        initRefreshListener()
    }

    private fun initRecyclerView() {

        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter

        // scroll to figure out when to page
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val pos = layoutManager.findLastVisibleItemPosition()
                val numItems: Int = recyclerView.adapter!!.itemCount - 1
                if (pos >= numItems) {
                    mainActivityViewModel.makeAPICal()
                }
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initViewModel() {

        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityViewModel.getLiveDataObserver().observe(this) { t ->

            if (t != null) {

                recyclerViewAdapter.addUpdatedData(t.items)
                recyclerViewAdapter.notifyDataSetChanged()
            } else {

                Toast.makeText(this, "Unable to fetch data.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        mainActivityViewModel.makeAPICal()
    }

    private fun initRefreshListener() {

        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.refreshLayout)

        swipeRefreshLayout.setOnRefreshListener {

            Toast.makeText(this, "Data has been refreshed", Toast.LENGTH_SHORT)
                .show()
            mainActivityViewModel.makeAPICal()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {

        super.onSaveInstanceState(outState, outPersistentState)

        outState.putParcelable("SAVE_STATE", recyclerView.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {

        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.getParcelable<Parcelable>("SAVE_STATE")?.let {

            recyclerView.layoutManager?.onRestoreInstanceState(it)
        }
    }
}