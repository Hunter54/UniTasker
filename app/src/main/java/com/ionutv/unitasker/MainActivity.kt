package com.ionutv.unitasker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.ionutv.unitasker.dataClasses.Classes
import com.ionutv.unitasker.dataClasses.Data
import com.ionutv.unitasker.databinding.ActivityMainBinding
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)


        binding.viewPager.adapter=ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when(position){
                0->"ODD"
                1->"EVEN"
                else -> "ODD"
            }
        }.attach()
    }



}
