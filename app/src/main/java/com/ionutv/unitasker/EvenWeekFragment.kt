package com.ionutv.unitasker


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ionutv.unitasker.databinding.FragmentEvenWeekBinding

/**
 * A simple [Fragment] subclass.
 */
class EvenWeekFragment : Fragment() {
    private lateinit var binding:FragmentEvenWeekBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_even_week, container, false)
        return binding.root
    }


}
