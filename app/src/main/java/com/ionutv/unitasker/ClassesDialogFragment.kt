package com.ionutv.unitasker

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.chip.Chip
import com.ionutv.unitasker.dataClasses.Classes
import com.ionutv.unitasker.databinding.DialogLayoutBinding


class ClassesDialogFragment : DialogFragment() {

    private lateinit var binding: DialogLayoutBinding
    var idclas = 0
    var error = false
    var triggered = 0
    var dayOfWeek = ""
    var classType = true
    var week = ""
    var name = ""
    var room = ""
    var time = ""
    var teacher = ""
    var clas: Classes? = null

    interface OnCompleteListener {
        fun onSavePress(clas: Classes)
        fun onUpdatePress(clas:Classes,id:Int,oldweek:String)
        fun onDeletePress(id: Int, oldweek: String)
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

        if (clas != null) {
            binding.dialogToolbar.title = "Update classes"
            val auxweek = week
            val auxid=idclas
            binding.dialogToolbar.inflateMenu(R.menu.delete_button)

            binding.dialogToolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> {
                        this.mListener?.onDeletePress(idclas, auxweek)
                        Toast.makeText(context, "Class deleted", Toast.LENGTH_LONG)
                            .show()
                        this.dismiss()
                        true
                    }
                    else -> false
                }
            }


            binding.dayChoiceChipGroup.check(
                when (dayOfWeek) {
                    "Monday" -> R.id.day_choice_chip1
                    "Tuesday" -> R.id.day_choice_chip2
                    "Wednesday" -> R.id.day_choice_chip3
                    "Thursday" -> R.id.day_choice_chip4
                    "Friday" -> R.id.day_choice_chip5
                    else -> R.id.day_choice_chip1
                }
            )

            binding.classChoiceChipGroup.check(
                if(classType)
                    R.id.choice_chip1
            else
                    R.id.choice_chip2
            )

            binding.weekChoiceChipGroup.check(
                when(week){
                    "Odd week" -> R.id.week_choice_chip1
                    "Even week" -> R.id.week_choice_chip2
                    else -> R.id.week_choice_chip3
                }
            )
            binding.nameTextInput.setText(name)
            binding.roomTextInput.setText(room)
            binding.timeTextInput.setText(time)
            binding.teacherTextInput.setText(teacher)
            addUpdateButtonCliclListener(auxweek)

        }
        else{
            addSaveButtonClickListener()
            binding.dialogToolbar.title = "Add classes"
            binding.weekChoiceChipGroup.check(
                when(week){
                    "Odd week" -> R.id.week_choice_chip1
                    "Even week" -> R.id.week_choice_chip2
                    else -> R.id.week_choice_chip3
                }
            )
        }

        binding.dialogToolbar.setNavigationOnClickListener { dismiss() }



        addChipGroupClickListener()
        binding.cancelButton.setOnClickListener {
            this.dismiss()
        }


        return binding.root
    }

    private fun addUpdateButtonCliclListener(auxweek:String){
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
            if (error) {
                error = false
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

            if (error) {
                error = false
                return@setOnClickListener
            }
            var newid = context!!.getSharedPreferences("id", Context.MODE_PRIVATE).getInt("id", 0)
            this.mListener?.onUpdatePress(
                Classes(newid, week, teacher, name, classType, time, dayOfWeek, room),
                idclas,auxweek
            )
            context!!.getSharedPreferences("id", Context.MODE_PRIVATE).edit()
                .putInt("id", ++newid).apply()
            this.dismiss()
        }
    }

    private fun addSaveButtonClickListener() {

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
            if (error) {
                error = false
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

            if (error) {
                error = false
                return@setOnClickListener
            }
            var idclass = context!!.getSharedPreferences("id", Context.MODE_PRIVATE).getInt("id", 0)
            this.mListener?.onSavePress(
                Classes(idclass, week, teacher, name, classType, time, dayOfWeek, room)
            )
            context!!.getSharedPreferences("id", Context.MODE_PRIVATE).edit()
                .putInt("id", ++idclass).apply()
            this.dismiss()
        }
    }

    private fun addChipGroupClickListener() {
        binding.dayChoiceChipGroup.setOnCheckedChangeListener { _, checkedId ->
            val chip: Chip? = view?.findViewById(checkedId)
            dayOfWeek = chip?.text.toString()
        }

        binding.classChoiceChipGroup.setOnCheckedChangeListener { _, checkedId ->
            val chip: Chip? = view?.findViewById(checkedId)
            classType = chip?.text.toString() == "Course"
            triggered = 1
        }

        binding.weekChoiceChipGroup.setOnCheckedChangeListener { _, checkedId ->
            val chip: Chip? = view?.findViewById(checkedId)
            week = chip?.text.toString()
        }
    }

    companion object {

        const val TAG = "example_dialog"

        fun display(
            fragmentManager: FragmentManager,
            clas: Classes? = null,
            week: String = ""
        ): ClassesDialogFragment {
            val exampleDialog = ClassesDialogFragment()
            exampleDialog.week=week
            clas?.let {
                exampleDialog.week = clas.week
                exampleDialog.clas = clas
                exampleDialog.idclas = clas.id
                exampleDialog.dayOfWeek = clas.day
                exampleDialog.classType = clas.type
                exampleDialog.name = clas.name
                exampleDialog.time = clas.time
                exampleDialog.room = clas.room
                exampleDialog.teacher = clas.teacher
                exampleDialog.triggered = 1

            }
            exampleDialog.show(fragmentManager, TAG)
            return exampleDialog
        }
    }
}