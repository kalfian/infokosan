package com.kalfian.infokosan.modules.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kalfian.infokosan.R
import com.kalfian.infokosan.databinding.FragmentHomeBinding
import com.kalfian.infokosan.models.properties.PropertyResponse
import com.kalfian.infokosan.adapters.GridPropertyAdapter
import com.kalfian.infokosan.utils.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home), SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentHomeBinding? = null
    private val b get() = _binding!!

    private lateinit var adapter: GridPropertyAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var page = 1
    private var totalPage = 10
    private var isLoad = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = GridLayoutManager(context, 2)
        b.swipeRefreshRekomendasi.setOnRefreshListener(this)

        setupRecycleView()
        getRecomendationKos(false)

        b.listRecomendedKos.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount

                if (!isLoad && page < totalPage) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        page++
                        getRecomendationKos(false)
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getRecomendationKos(isOnRefresh: Boolean) {
        isLoad = true
//        b.greetingText.text = "rene saiki"
        if (!isOnRefresh) {
            b.progressBar.visibility = View.VISIBLE
        }

        val params = HashMap<String, String>()
        params["page"] = page.toString()
        params["per_page"] = 9.toString()
        params["is_featured"] = 1.toString()
//        params["with_owner"] = 1.toString()
//        params["city_id"] = 1.toString()

        RetrofitClient.instance.getProperties(parameters = params).enqueue(object: Callback<PropertyResponse>{
            override fun onResponse(
                call: Call<PropertyResponse>,
                response: Response<PropertyResponse>
            ) {


                totalPage = response.body()?.data?.meta?.last_page ?: 0

                val responses = response.body()?.data?.data
                Log.d("RESPONSE_API_2", "${response.body()}")

                if (responses != null) {
                    adapter.addList(responses)
                }

                b.progressBar.visibility = View.INVISIBLE
                isLoad = false
                b.swipeRefreshRekomendasi.isRefreshing = false
            }

            override fun onFailure(call: Call<PropertyResponse>, t: Throwable) {
                b.greetingText.text = "${t.message}"
                Toast.makeText(context, "${t.message}", Toast.LENGTH_LONG).show()
                Log.d("RETURN_API", "${t.message}")
            }

        })
    }

    private fun setupRecycleView() {
        b.listRecomendedKos.setHasFixedSize(true)
        b.listRecomendedKos.layoutManager = layoutManager

        adapter = GridPropertyAdapter()
        b.listRecomendedKos.adapter = adapter

    }

    override fun onRefresh() {
//        adapter.clear()
        page = 1
        getRecomendationKos(true)
    }

}
