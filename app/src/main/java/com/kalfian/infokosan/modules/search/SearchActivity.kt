package com.kalfian.infokosan.modules.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kalfian.infokosan.adapters.GridPropertyAdapter
import com.kalfian.infokosan.databinding.ActivitySearchBinding
import com.kalfian.infokosan.models.properties.PropertyResponse
import com.kalfian.infokosan.utils.Constant
import com.kalfian.infokosan.utils.RetrofitClient
import com.kalfian.infokosan.utils.hideKeyboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var b: ActivitySearchBinding

    private lateinit var adapter: GridPropertyAdapter
    private lateinit var layoutManager: GridLayoutManager

    private var page = 1
    private var totalPage = 10
    private var isLoad = false
    var q: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(b.root)

        q = intent.getStringExtra(Constant.SEARCH_INTENT).toString()
        b.searchEdit.setText(q)

        layoutManager = GridLayoutManager(applicationContext, 2)
        b.swipeRefresh.setOnRefreshListener(this)

        b.backButton.setOnClickListener {
            finish()
        }

        setupRecycleView()
        getPropertyKos(false, q)

        b.listSearchProperty.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount

                if (!isLoad && page < totalPage) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        page++
                        getPropertyKos(false, q)
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        b.searchEdit.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                v.hideKeyboard()
                var q = b.searchEdit.text.toString()
                if (q != "") {
                    searchProperty(q)
                }

                true
            }
            false
        }

    }

    private fun searchProperty(q: String) {
        val intent = Intent(applicationContext, SearchActivity::class.java)
        intent.putExtra(Constant.SEARCH_INTENT, q)
        startActivity(intent)
    }

    private fun getPropertyKos(isOnRefresh: Boolean, q: String) {
        isLoad = true

        if (!isOnRefresh) {
            b.progressBar.visibility = View.VISIBLE
        }

        val params = HashMap<String, String>()
        params["page"] = page.toString()
        params["per_page"] = 9.toString()
//        params["is_featured"] = 1.toString()
        params["q"] = q
//        params["with_owner"] = 1.toString()
//        params["city_id"] = 1.toString()

        RetrofitClient.instance.getProperties(parameters = params).enqueue(object:
            Callback<PropertyResponse> {
            override fun onResponse(
                call: Call<PropertyResponse>,
                response: Response<PropertyResponse>
            ) {


                totalPage = response.body()?.data?.meta?.last_page ?: 0

                val responses = response.body()?.data?.data
                Log.d("RESPONSE_API", "${response.body()}")

                if (responses != null) {
                    adapter.addList(responses)
                }

                if (page == totalPage) {
                    b.progressBar.visibility = View.GONE
                } else {
                    b.progressBar.visibility = View.INVISIBLE
                }

                // ADD Empty State
                if (adapter.itemCount > 0) {
                    b.listSearchProperty.visibility = View.VISIBLE
                    b.emptyState.root.visibility = View.GONE
                } else {
                    b.listSearchProperty.visibility = View.GONE
                    b.emptyState.root.visibility = View.VISIBLE
                }

                isLoad = false
                b.swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<PropertyResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "${t.message}", Toast.LENGTH_LONG).show()
                Log.d("RESPONSE_API_ERROR", "${t.message}")
                isLoad = false
                b.swipeRefresh.isRefreshing = false
                b.progressBar.visibility = View.INVISIBLE
            }

        })
    }

    private fun setupRecycleView() {
        b.listSearchProperty.setHasFixedSize(true)
        b.listSearchProperty.layoutManager = layoutManager

        adapter = GridPropertyAdapter()
        b.listSearchProperty.adapter = adapter

    }

    override fun onRefresh() {
        adapter.clear()
        page = 1
        getPropertyKos(true, q)
    }
}