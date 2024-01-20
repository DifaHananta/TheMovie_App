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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationView = binding.bottomMenu

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.botmenu_high_rated -> {
                    replaceFragment(HighRatedFragment())
                    true
                }
                R.id.botmenu_popular -> {
                    replaceFragment(PopularFragment())
                    true
                }
                R.id.botmenu_favorite -> {
                    replaceFragment(FavoriteFragment())
                    true
                }
                else -> false
            }
        }
        replaceFragment(HighRatedFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }

}