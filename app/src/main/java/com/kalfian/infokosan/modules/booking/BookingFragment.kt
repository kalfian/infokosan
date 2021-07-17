package com.kalfian.infokosan.modules.booking

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kalfian.infokosan.R
import com.kalfian.infokosan.adapters.GridPropertyAdapter
import com.kalfian.infokosan.adapters.ListRentAdapter
import com.kalfian.infokosan.databinding.FragmentBookingBinding
import com.kalfian.infokosan.databinding.FragmentHomeBinding
import com.kalfian.infokosan.models.properties.PropertyResponse
import com.kalfian.infokosan.models.rent.DataRent
import com.kalfian.infokosan.models.rent.RentResponse
import com.kalfian.infokosan.utils.Constant
import com.kalfian.infokosan.utils.Midtrans
import com.kalfian.infokosan.utils.RetrofitClient
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookingFragment : Fragment(R.layout.fragment_booking), SwipeRefreshLayout.OnRefreshListener,
    ListRentAdapter.AdapterRentOnClickListener, TransactionFinishedCallback {

    private lateinit var b: FragmentBookingBinding

    private lateinit var adapter: ListRentAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private var page = 1
    private var totalPage = 10
    private var isLoad = false

    lateinit var sharedPref : SharedPreferences
    var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentBookingBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(context)
        b.swipeRefresh.setOnRefreshListener(this)

        sharedPref = this.requireActivity().getSharedPreferences(Constant.PREF_CONF_NAME, Constant.PREF_CONF_MODE)
        userId = sharedPref.getInt(Constant.PREF_ID, 0)

        setupRecycleView()
        getRent(false)

        b.list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapter.itemCount

                if (!isLoad && page < totalPage) {
                    if (visibleItemCount + pastVisibleItem >= total) {
                        page++
                        getRent(false)
                    }
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun setupRecycleView() {
        b.list.setHasFixedSize(true)
        b.list.layoutManager = layoutManager

        adapter = ListRentAdapter(this)
        b.list.adapter = adapter

    }

    private fun getRent(isOnRefresh: Boolean) {
        isLoad = true

        if (!isOnRefresh) {
            b.progressBar.visibility = View.VISIBLE
        }

        val token = "Bearer " + sharedPref.getString(Constant.PREF_TOKEN, "")

        RetrofitClient.instance.getRents(token).enqueue(object:
            Callback<RentResponse> {
            override fun onResponse(
                call: Call<RentResponse>,
                response: Response<RentResponse>
            ) {
                totalPage = response.body()?.data?.lastPage ?: 0

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
                    b.list.visibility = View.VISIBLE
                    b.emptyState.root.visibility = View.GONE
                } else {
                    b.list.visibility = View.GONE
                    b.emptyState.root.visibility = View.VISIBLE
                }

                isLoad = false
                b.swipeRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<RentResponse>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_LONG).show()
                Log.d("RESPONSE_API_ERROR", "${t.message}")
                isLoad = false
                b.swipeRefresh.isRefreshing = false
                b.progressBar.visibility = View.INVISIBLE
            }

        })
    }

    override fun onRefresh() {
        adapter.clear()
        page = 1
        getRent(true)
    }

    override fun onBayarClickListener(data: DataRent) {
        val midtrans = context?.let { Midtrans(it, this) }
        midtrans?.initSdk()
        midtrans?.snapToken(data.payment.snapToken)
    }

    override fun onTransactionFinished(p0: TransactionResult?) {
        adapter.clear()
        page = 1
        getRent(true)
    }
}