package com.ionutv.unitasker


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ionutv.unitasker.dataClasses.Classes
import com.ionutv.unitasker.databinding.FragmentOddWeekBinding


class OddWeekFragment : Fragment() {

    private lateinit var binding:FragmentOddWeekBinding
    val userClasses:ArrayList<Classes> =  ArrayList()
    private var selectedPage = 0

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

        userClasses.add(Classes("Operating Systems","Pungila",false,"14:20","035"))
        userClasses.add(Classes("Programming 3","Pop",false,"18:00","034"))
        userClasses.add(Classes("English","Ana",true,"19:40","A02"))


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_odd_week, container, false)
//        binding.tvweek.text = when (selectedPage){
//            0 -> "ODD week"
//            else -> "EVEN week"
//        }

        //binding.rvOddclassesList.layoutManager = LinearLayoutManager(this)
        val adapter=WeekClassAdapter(userClasses)
        binding.rvOddclassesList.adapter = adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }
}
