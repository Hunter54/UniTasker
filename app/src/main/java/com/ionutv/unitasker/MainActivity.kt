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
        setSupportActionBar(main_toolbar)

        val tabPosition = this.getSharedPreferences(
            "TAB_VIEWED",
            Context.MODE_PRIVATE
        ).getInt("week", 0)

        when(tabPosition){
            0 -> TAB_VIEWED="odd.json"
            1 -> TAB_VIEWED="even.json"
        }

        binding.viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "ODD"
                1 -> "EVEN"
                else -> "ODD"
            }

            binding.viewPager.setCurrentItem(tabPosition,true)

        }.attach()

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("Page Change Listener", "Changed tab page")
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

    override fun onStop() {
        super.onStop()
        when (TAB_VIEWED) {
            "odd.json" -> this.getSharedPreferences("TAB_VIEWED", Context.MODE_PRIVATE).edit().putInt("week",0).apply()
            "even.json" -> this.getSharedPreferences("TAB_VIEWED", Context.MODE_PRIVATE).edit().putInt("week",1).apply()
        }
    }

    override fun onSavePress(clas: Classes) {

        val chosenWeek = when (clas.week) {
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

    override fun onUpdatePress(clas: Classes, id: Int, oldweek: String) {
        onDeletePress(id,oldweek)

        onSavePress(clas)
    }

    override fun onDeletePress(id: Int, oldweek: String) {
        val chosenWeek = when (oldweek) {
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

                userClasses.removeIf { it.id == id }

                val jsonString: String = jsonAdapter.toJson(userClasses)
                saveJson(i, jsonString)
            }
        } else {
            val userClasses: ArrayList<Classes> = ArrayList()
            val classes = jsonAdapter.fromJson(loadJson(chosenWeek, this))
            classes?.forEach {
                Log.i("Json Parsing", it.toString())
                userClasses.add(it)
            }

            userClasses.removeIf { it.id == id }

            val jsonString: String = jsonAdapter.toJson(userClasses)
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
        when(TAB_VIEWED){
            "odd.json" -> ClassesDialogFragment.display(supportFragmentManager,week = "Odd week")
            "even.json" -> ClassesDialogFragment.display(supportFragmentManager,week = "Even week")
        }
    }
}


