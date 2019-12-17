package com.ionutv.unitasker


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ionutv.unitasker.dataClasses.Classes
import com.ionutv.unitasker.databinding.FragmentOddWeekBinding
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import android.R.id.edit
import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE


class OddWeekFragment : Fragment() {

    private lateinit var binding: FragmentOddWeekBinding
    private lateinit var adapter: WeekClassAdapter
    private val CLASS_PREFFERENCE = "class"
    var userClasses: ArrayList<Classes> = ArrayList()
    private var selectedPage = 0
    private var tabViewed: String = ""
    private val moshi: Moshi = Moshi.Builder().build()
    private val listType = Types.newParameterizedType(List::class.java, Classes::class.java)
    private val jsonAdapter: JsonAdapter<List<Classes>> = moshi.adapter(listType)


    companion object {
        private const val ARG_SELECTED_WEEK = "selectedWeek"
        fun newInstance(position: Int): OddWeekFragment {
            return OddWeekFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SELECTED_WEEK, position)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedPage = arguments?.getInt(ARG_SELECTED_WEEK) ?: 0

        if (selectedPage == 0) {
            tabViewed = "odd.json"
        } else {
            tabViewed = "even.json"
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_odd_week, container, false)


        adapter = WeekClassAdapter(userClasses)
        binding.rvOddClassesList.adapter = adapter

//        var jsonString: String = jsonAdapter.toJson(userClasses)
//        saveJson(tabViewed, jsonString)

        adapter.notifyDataSetChanged()

        return binding.root
    }

    var listener: SharedPreferences.OnSharedPreferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            Log.d("Shared Prefference", "Calling Shared Prefference Change Listener "+key)
            if (key == tabViewed) {
                userClasses.clear()
                loadUserClasses()
                adapter.notifyDataSetChanged()
            }

        }


    override fun onResume() {
        super.onResume()
        Log.d("Json Parsing", "Entering Json parsing test")
        loadUserClasses()
        context?.getSharedPreferences(CLASS_PREFFERENCE, MODE_PRIVATE)
            ?.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        context?.getSharedPreferences(CLASS_PREFFERENCE, MODE_PRIVATE)
            ?.unregisterOnSharedPreferenceChangeListener(listener)
        userClasses.clear()

    }

    private fun loadUserClasses() {
        val classes = jsonAdapter.fromJson(loadJson(tabViewed, context))
        classes?.forEach {
            Log.d("Json Parsing", it.toString())
            userClasses.add(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun saveJson(week: String, json: String) {
        val sharedPreferences =
            this.activity!!.getSharedPreferences(CLASS_PREFFERENCE, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(week, json).apply()
    }

    private fun loadJson(week: String, context: Context?): String {
        val sharedPreferences = context?.getSharedPreferences(CLASS_PREFFERENCE, MODE_PRIVATE)
        return sharedPreferences?.getString(week, "null").toString()
    }
}
