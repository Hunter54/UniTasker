package com.ionutv.unitasker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.ionutv.unitasker.dataClasses.Classes
import com.ionutv.unitasker.databinding.ActivityMainBinding
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.activity_main.*
import java.time.DayOfWeek


class MainActivity : AppCompatActivity(), ClassesDialogFragment.OnCompleteListener {

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
            displayDialog()
        }
    }

    override fun onComplete(clas: Classes, week: String) {

        val chosenWeek = when (week) {
            "Odd week" -> "odd.json"
            "Even week" -> "even.json"
            else -> "both"
        }

        if (chosenWeek == "both") {
            for (i in arrayOf("odd.json", "even.json")) {
                val userClasses: ArrayList<Classes> = ArrayList()
                val classes = jsonAdapter.fromJson(loadJson(i, this))
                classes?.forEach {
                    Log.i("Json Parsing", it.toString())
                    userClasses.add(it)
                }
                userClasses.add(clas)

                val sortedUserClasses = userClasses.sortedWith(
                    compareBy({ DayOfWeek.valueOf(it.day.toUpperCase()) },
                        { it.time.split(":")[0].toInt() })
                )

                val jsonString: String = jsonAdapter.toJson(sortedUserClasses)
                saveJson(i, jsonString)
            }
        } else {
            val userClasses: ArrayList<Classes> = ArrayList()
            val classes = jsonAdapter.fromJson(loadJson(chosenWeek, this))
            classes?.forEach {
                Log.i("Json Parsing", it.toString())
                userClasses.add(it)
            }
            userClasses.add(clas)

            val sortedUserClasses = userClasses.sortedWith(
                compareBy({ DayOfWeek.valueOf(it.day.toUpperCase()) },
                    { it.time.split(":")[0].toInt() })
            )

            val jsonString: String = jsonAdapter.toJson(sortedUserClasses)
            saveJson(chosenWeek, jsonString)
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

    private fun displayDialog() {
        ClassesDialogFragment.display(supportFragmentManager)
    }
}
