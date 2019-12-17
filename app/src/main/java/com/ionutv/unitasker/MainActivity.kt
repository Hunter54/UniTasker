package com.ionutv.unitasker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ionutv.unitasker.dataClasses.Classes
import com.ionutv.unitasker.dataClasses.Data
import com.ionutv.unitasker.databinding.ActivityMainBinding
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val moshi: Moshi = Moshi.Builder().build()
    private val listType = Types.newParameterizedType(List::class.java, Classes::class.java)
    private val jsonAdapter: JsonAdapter<List<Classes>> = moshi.adapter(listType)
    private val CLASS_PREFFERENCE = "class"
    private var TAB_VIEWED = "odd.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)


        binding.viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "ODD"
                1 -> "EVEN"
                else -> "ODD"
            }

        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                TAB_VIEWED = when (position) {
                    0 -> "odd.json"
                    1 -> "even.json"
                    else -> "odd.json"
                }
            }
        })

        fab.setOnClickListener {
            Log.d("FAB Click Listener", "Pressing fab button")
            val userClasses: ArrayList<Classes> = ArrayList()
            val classes = jsonAdapter.fromJson(loadJson(TAB_VIEWED, this))

            classes?.forEach {
                Log.i("Json Parsing", it.toString())
                userClasses.add(it)
            }
            userClasses.add(
                Classes(
                    "Marian",
                    "Advanced Data Structures",
                    false,
                    "13:00",
                    "Friday",
                    "032"
                )
            )
            val jsonString: String = jsonAdapter.toJson(userClasses)
            saveJson(TAB_VIEWED, jsonString)
        }
    }

    private fun saveJson(week: String, json: String) {
        val sharedPreferences = this.getSharedPreferences(
            CLASS_PREFFERENCE,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(week, json).apply()
    }

    private fun loadJson(week: String, context: Context?): String {
        val sharedPreferences = context?.getSharedPreferences(
            CLASS_PREFFERENCE,
            Context.MODE_PRIVATE
        )
        return sharedPreferences?.getString(week, "null").toString()
    }

}
