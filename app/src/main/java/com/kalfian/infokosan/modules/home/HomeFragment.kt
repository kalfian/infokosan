package com.kalfian.infokosan.modules.home

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kalfian.infokosan.R
import com.kalfian.infokosan.databinding.FragmentHomeBinding
import com.kalfian.infokosan.models.properties.PropertyResponse
import com.kalfian.infokosan.adapters.GridPropertyAdapter
import com.kalfian.infokosan.models.favorite.Favorite
import com.kalfian.infokosan.models.properties.Property
import com.kalfian.infokosan.modules.auth.LoginActivity
import com.kalfian.infokosan.modules.property.DetailPropertyActivity
import com.kalfian.infokosan.modules.search.SearchActivity
import com.kalfian.infokosan.utils.*
import com.kalfian.infokosan.utils.db.FavoriteDB
import com.kalfian.infokosan.utils.db.FavoriteDao
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(R.layout.fragment_home), SwipeRefreshLayout.OnRefreshListener,
    GridPropertyAdapter.AdapterPropertyOnClickListener, TransactionFinishedCallback {

    private lateinit var b: FragmentHomeBinding

    private lateinit var adapter: GridPropertyAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var page = 1
    private var totalPage = 10
    private var isLoad = false

    lateinit var sharedPref : SharedPreferences

    // DAO
    private lateinit var database: FavoriteDB
    private lateinit var dao: FavoriteDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentHomeBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FavoriteDB.getDatabase(requireContext())
        dao = database.getFavoriteDao()

        layoutManager = GridLayoutManager(context, 2)
        b.swipeRefreshRekomendasi.setOnRefreshListener(this)

        sharedPref = this.requireActivity().getSharedPreferences(Constant.PREF_CONF_NAME, Constant.PREF_CONF_MODE)

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

        // Add Search
        b.searchEdit.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                v.hideKeyboard()
                var q = b.searchEdit.text.toString()
                b.searchEdit.setText("")
                if (q != "") {
                    searchProperty(q)
                }

                true
            }
            false
        }
    }

    private fun searchProperty(q: String) {
        val intent = Intent(context, SearchActivity::class.java)
        intent.putExtra(Constant.SEARCH_INTENT, q)
        startActivity(intent)
    }

    private fun getRecomendationKos(isOnRefresh: Boolean) {
        isLoad = true

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
                Log.d("RESPONSE_API", "${response.body()}")

                if (responses != null) {
                    responses.forEach {
                        val fav = dao.getById(it.id)
                        if (fav.isNotEmpty()) {
                            it.isFavorite = true
                        }
                    }
                    adapter.addList(responses)
                }

                if (page == totalPage) {
                    b.progressBar.visibility = View.GONE
                } else {
                    b.progressBar.visibility = View.INVISIBLE
                }

                // ADD Empty State
                if (adapter.itemCount > 0) {
                    b.listRecomendedKos.visibility = View.VISIBLE
                    b.emptyState.root.visibility = View.GONE
                } else {
                    b.listRecomendedKos.visibility = View.GONE
                    b.emptyState.root.visibility = View.VISIBLE
                }

                isLoad = false
                b.swipeRefreshRekomendasi.isRefreshing = false
            }

            override fun onFailure(call: Call<PropertyResponse>, t: Throwable) {
                Toast.makeText(context, "${t.message}", Toast.LENGTH_LONG).show()
                Log.d("RESPONSE_API_ERROR", "${t.message}")
                isLoad = false
                b.swipeRefreshRekomendasi.isRefreshing = false
                b.progressBar.visibility = View.INVISIBLE
            }

        })
    }

    private fun setupRecycleView() {
        b.listRecomendedKos.setHasFixedSize(true)
        b.listRecomendedKos.layoutManager = layoutManager

        adapter = GridPropertyAdapter(this)
        b.listRecomendedKos.adapter = adapter

    }

    override fun onRefresh() {
        adapter.clear()
        page = 1
        getRecomendationKos(true)
    }

    override fun onPropertyClickListener(data: Property) {
        val intent = Intent(context, DetailPropertyActivity::class.java)
        intent.putExtra(Constant.DETAIL_PROPERTY_INTENT, data.id)
        startActivity(intent)
//        var midtrans = context?.let { Midtrans(it, this) }
//        midtrans?.initSdk()
//        midtrans?.snapToken("3928102d-631e-4616-bc82-b0f1bd4b7c28")
    }

    override fun onFavoriteClickListener(ch: CheckBox, data: Property) {
        val image = if(data.propertyImages.isNotEmpty()) data.propertyImages[0].image else "-"
        var fav = Favorite(data.id, data.title, image, data.location.address, data.basicPrice)
        saveOrDeleteFavorite(fav, ch)
    }

    private fun saveOrDeleteFavorite(fav: Favorite, ch: CheckBox){
        if (dao.getById(fav.id).isEmpty()){
            dao.insert(fav)
            ch.isChecked = true
            Toast.makeText(context, "Berhasil menambah favorit!", Toast.LENGTH_SHORT).show()
        }
        else{
            dao.delete(fav)
            ch.isChecked = false
            Toast.makeText(context, "Berhasil menghapus favorit!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onTransactionFinished(p0: TransactionResult?) {

    }

}
