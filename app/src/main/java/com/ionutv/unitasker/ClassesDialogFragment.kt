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
import com.ionutv.unitasker.dataClasses.Classes
import com.ionutv.unitasker.databinding.DialogLayoutBinding


class ClassesDialogFragment : DialogFragment() {

    private lateinit var binding: DialogLayoutBinding


    interface OnCompleteListener {
        fun onComplete(clas :Classes)
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
        if(dialog!=null){
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_layout, container, false)


        binding.toolbar.setNavigationOnClickListener { dismiss() }
        binding.toolbar.title = "Add classes"

        binding.dayChoiceChipGroup.setOnCheckedChangeListener { group, checkedId ->
            Toast.makeText(context, checkedId.toString(),Toast.LENGTH_LONG).show()
        }

        binding.cancelButton.setOnClickListener {
            this.dismiss()
        }
        binding.saveButton.setOnClickListener {  }

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