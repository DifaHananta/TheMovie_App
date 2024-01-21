package com.project.themovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.themovie.databinding.ActivityMainBinding
import com.project.themovie.ui.favorite.FavoriteFragment
import com.project.themovie.ui.highRated.HighRatedFragment
import com.project.themovie.ui.popular.PopularFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView

    private val highRatedFragment = HighRatedFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomMenu

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.botmenu_high_rated -> {
                    showFragment(HighRatedFragment())
                    true
                }
                R.id.botmenu_popular -> {
                    showFragment(PopularFragment())
                    true
                }
                R.id.botmenu_favorite -> {
                    showFragment(FavoriteFragment())
                    true
                }
                else -> false
            }
        }
        if (savedInstanceState == null) {
            showFragment(highRatedFragment)
        }
    }

    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        if (!fragment.isAdded) {
            transaction.add(R.id.fragment_container, fragment, fragment.javaClass.simpleName)
        }

        supportFragmentManager.fragments.forEach {
            if (it == fragment) {
                transaction.show(it)
            } else {
                transaction.hide(it)
            }
        }

        transaction.commit()
    }

}