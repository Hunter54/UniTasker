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


class OddWeekFragment : Fragment() {

    private lateinit var binding:FragmentOddWeekBinding
    var userClasses:ArrayList<Classes> =  ArrayList()
    private var selectedPage = 0
    private var tabViewed:String = ""
    private val moshi: Moshi = Moshi.Builder().build()
    private val listType = Types.newParameterizedType(List::class.java, Classes::class.java)
    private val jsonAdapter: JsonAdapter<List<Classes>> = moshi.adapter(listType)

    companion object{
        private const val ARG_SELECTED_WEEK="selectedWeek"
        fun newInstance(position :Int ): OddWeekFragment{
            return OddWeekFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SELECTED_WEEK, position)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedPage= arguments?.getInt(ARG_SELECTED_WEEK) ?: 0

        if(selectedPage == 0){
            tabViewed="odd.json"
        }
        else{
            tabViewed="even.json"
        }

        var classesJson: String = ""

        try {
            val inputStream = this.context!!.assets.open(tabViewed)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.use { it.read(buffer) }
            classesJson = String(buffer)
        }
        catch (ioException: IOException){
            ioException.printStackTrace()
        }

        if(classesJson != "") {
            Log.d("Json Parsing","Entering Json parsing test")
            val classes = jsonAdapter.fromJson(classesJson)
            classes?.forEach {
                Log.d("Json Parsing", it.toString())
                userClasses.add(it)
            }
        }


    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_odd_week, container, false)


        val adapter=WeekClassAdapter(userClasses)
        binding.rvOddclassesList.adapter = adapter

        userClasses.add(Classes("Marin","ADS",false,"11:20","Friday","035"))

        var jsonString:String = jsonAdapter.toJson(userClasses)
        adapter.notifyDataSetChanged()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }
}
