package com.ionutv.unitasker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
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


        val moshi: Moshi=Moshi.Builder().build()
        val listType = Types.newParameterizedType(List::class.java,Data::class.java)
        val adapter:JsonAdapter<List<Data>> = moshi.adapter(listType)

        var classesJson: String = ""
        try {
            val inputStream = this.assets.open("data.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.use { it.read(buffer) }
            classesJson = String(buffer)
        }
        catch (ioException: IOException){
            ioException.printStackTrace()
        }

        if(classesJson != "") {
            val data = adapter.fromJson(classesJson)

        }


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
