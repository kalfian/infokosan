package com.kalfian.infokosan.modules.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kalfian.infokosan.R
import com.kalfian.infokosan.databinding.ActivityHomeBinding
import com.kalfian.infokosan.modules.account.AccountFragment
import com.kalfian.infokosan.modules.booking.BookingFragment
import com.kalfian.infokosan.modules.favorite.FavoriteFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var b: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityHomeBinding.inflate(layoutInflater)
        val v = b.root
        setContentView(v)

        val homeFragment = HomeFragment()
        val favoriteFragment = FavoriteFragment()
        val bookingFragment = BookingFragment()
        val accountFragment = AccountFragment()

        makeCurrentFragment(homeFragment)

        b.bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.ic_home -> makeCurrentFragment(homeFragment)
                R.id.ic_favorite -> makeCurrentFragment(favoriteFragment)
                R.id.ic_booking -> makeCurrentFragment(bookingFragment)
                R.id.ic_account -> makeCurrentFragment(accountFragment)
            }

            true

        }
    }

    private fun makeCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.content_home, fragment)
            commit()
        }
    }
}