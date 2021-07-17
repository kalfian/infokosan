package com.kalfian.infokosan.modules.favorite

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kalfian.infokosan.R
import com.kalfian.infokosan.adapters.GridFavoriteAdapter
import com.kalfian.infokosan.databinding.FragmentFavoriteBinding
import com.kalfian.infokosan.models.favorite.Favorite
import com.kalfian.infokosan.modules.property.DetailPropertyActivity
import com.kalfian.infokosan.utils.Constant
import com.kalfian.infokosan.utils.db.FavoriteDB
import com.kalfian.infokosan.utils.db.FavoriteDao
import okhttp3.internal.notifyAll
import kotlin.let as let1

class FavoriteFragment : Fragment(R.layout.fragment_favorite), SwipeRefreshLayout.OnRefreshListener,
    GridFavoriteAdapter.AdapterFavoriteOnClickListener {

    private lateinit var b: FragmentFavoriteBinding

    private lateinit var adapter: GridFavoriteAdapter
    private lateinit var layoutManager: GridLayoutManager
    private var isLoad = false
    var userId = 0

    lateinit var sharedPref : SharedPreferences

    // DAO
    private lateinit var database: FavoriteDB
    private lateinit var dao: FavoriteDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentFavoriteBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = GridLayoutManager(context, 2)
        b.swipeRefresh.setOnRefreshListener(this)

        sharedPref = this.requireActivity().getSharedPreferences(Constant.PREF_CONF_NAME, Constant.PREF_CONF_MODE)
        userId = sharedPref.getInt(Constant.PREF_ID, 0)

        database = FavoriteDB.getDatabase(requireContext())
        dao = database.getFavoriteDao()

        setupRecycleView()
        getFavKos(false)
    }

    private fun getFavKos(isOnRefresh: Boolean) {
        isLoad = true

        if (!isOnRefresh) {
            b.progressBar.visibility = View.VISIBLE
        }

        val database = context?.let1 { FavoriteDB.getDatabase(it) }!!
        val dao = database.getFavoriteDao()
        var listItems: List<Favorite> = dao.getAll(userId)

        Log.d("DATA", listItems.toString())

        adapter.addList(listItems)

        b.progressBar.visibility = View.INVISIBLE

        // Empty State
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

    private fun setupRecycleView() {
        b.list.setHasFixedSize(true)
        b.list.layoutManager = layoutManager

        adapter = GridFavoriteAdapter(this)
        b.list.adapter = adapter

    }

    override fun onRefresh() {
        adapter.clear()
        getFavKos(true)
    }

    override fun onFavoriteClickListener(data: Favorite) {
        val intent = Intent(context, DetailPropertyActivity::class.java)
        intent.putExtra(Constant.DETAIL_PROPERTY_INTENT, data.id)
        startActivity(intent)
    }

    override fun onDeleteFavoriteListener(data: Favorite) {
        deleteFav(data)
    }

    private fun deleteFav(fav: Favorite){
        dao.delete(fav)
        Toast.makeText(context, "Berhasil menghapus favorit!", Toast.LENGTH_SHORT).show()
        adapter.clear()
        getFavKos(true)
    }
}