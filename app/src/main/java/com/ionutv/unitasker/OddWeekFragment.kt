package com.ionutv.unitasker


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.ionutv.unitasker.DataClasses.Classes
import com.ionutv.unitasker.databinding.FragmentOddWeekBinding
import kotlinx.android.synthetic.main.fragment_odd_week.*


class OddWeekFragment : Fragment() {

    private lateinit var binding:FragmentOddWeekBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_odd_week, container, false)


        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userClasses: ArrayList<Classes> = ArrayList()
        userClasses.add(Classes("Operating Systems","Pungila",false,"14:20","035"))
        userClasses.add(Classes("Programming 3","Pop",false,"18:00","034"))

        binding.rvOddclassesList.layoutManager = LinearLayoutManager(context)
        val adapter=WeekClassAdapter(userClasses)
        binding.rvOddclassesList.adapter = adapter
    }
}
