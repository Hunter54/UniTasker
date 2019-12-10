package com.ionutv.unitasker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ionutv.unitasker.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)

        createTabs()


    }

    private fun createTabs(){
        val adapter=ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(OddWeekFragment(),"ODD")
        adapter.addFragment(EvenWeekFragment(),"EVEN")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
}
