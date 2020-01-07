package com.ionutv.unitasker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.chip.Chip
import com.ionutv.unitasker.dataClasses.Classes
import com.ionutv.unitasker.databinding.DialogLayoutBinding
import java.time.DayOfWeek


class ClassesDialogFragment : DialogFragment() {

    private lateinit var binding: DialogLayoutBinding


    interface OnCompleteListener {
        fun onComplete(clas: Classes,week:String)
    }

    private var mListener: OnCompleteListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mListener = activity as OnCompleteListener
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnCompleteListener")
        }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_layout, container, false)
        var error: Boolean = false
        var triggered = 0
        var dayOfWeek = ""
        var classType = true
        var week = ""
        var name = ""
        var room = ""
        var time = ""
        var teacher = ""

        binding.toolbar.setNavigationOnClickListener { dismiss() }
        binding.toolbar.title = "Add classes"

        binding.dayChoiceChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = view?.findViewById(checkedId)
            dayOfWeek = chip?.text.toString()
        }

        binding.classChoiceChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = view?.findViewById(checkedId)
            classType = chip?.text.toString() == "Course"
            triggered = 1
        }

        binding.weekChoiceChipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip: Chip? = view?.findViewById(checkedId)
            week = chip?.text.toString()
        }

        binding.cancelButton.setOnClickListener {
            this.dismiss()
        }
        binding.saveButton.setOnClickListener {
            when {
                dayOfWeek == "" -> {
                    Toast.makeText(
                        context,
                        "Please select a day of the week",
                        Toast.LENGTH_LONG
                    ).show()
                    error = true
                }
                triggered == 0 -> {
                    Toast.makeText(
                        context,
                        "Please select a type of class",
                        Toast.LENGTH_LONG
                    ).show()
                    error = true
                }
                week == "" -> {
                    Toast.makeText(
                        context,
                        "Please select an even or odd week",
                        Toast.LENGTH_LONG
                    ).show()
                    error = true
                }

            }
            if(error){
                error=false
                return@setOnClickListener
            }
            name = binding.nameTextInput.text.toString()
            room = binding.roomTextInput.text.toString()
            time = binding.timeTextInput.text.toString()
            teacher = binding.teacherTextInput.text.toString()
            when {
                name == "" -> {
                    Toast.makeText(
                        context,
                        "Please enter a class name",
                        Toast.LENGTH_LONG
                    ).show()
                    error = true
                }
                room == "" -> {
                    Toast.makeText(
                        context,
                        "Please enter the room",
                        Toast.LENGTH_LONG
                    ).show()
                    error = true
                }
                time == "" -> {

                    Toast.makeText(
                        context,
                        "Please enter time of the class",
                        Toast.LENGTH_LONG
                    ).show()
                    error = true
                }
            }

            if(error){
                error=false
                return@setOnClickListener
            }

            this.mListener?.onComplete(Classes(teacher,name,classType,time,dayOfWeek,room),week)
            this.dismiss()
        }

        return binding.root
    }

    companion object {

        val TAG = "example_dialog"

        fun display(fragmentManager: FragmentManager): ClassesDialogFragment {
            val exampleDialog = ClassesDialogFragment()
            exampleDialog.show(fragmentManager, TAG)
            return exampleDialog
        }
    }
}