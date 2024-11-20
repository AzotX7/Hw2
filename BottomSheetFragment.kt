package com.example.homew1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var editText: EditText
    private lateinit var addRandomButton: Button
    private lateinit var removeRandomButton: Button
    private lateinit var addOneButton: Button
    private lateinit var removeOneButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.bottom_sheet, container, false)

        editText = rootView.findViewById(R.id.editTextNumber)
        addRandomButton = rootView.findViewById(R.id.buttonAddMultiple)
        removeRandomButton = rootView.findViewById(R.id.buttonRemoveMultiple)
        addOneButton = rootView.findViewById(R.id.buttonAddSingle)
        removeOneButton = rootView.findViewById(R.id.buttonRemoveSingle)

        addRandomButton.setOnClickListener {
            val quantity = editText.text.toString().toIntOrNull() ?: 0
            (activity as? MainActivity)?.addRandomItems(quantity)
        }

        removeRandomButton.setOnClickListener {
            val quantity = editText.text.toString().toIntOrNull() ?: 0
            (activity as? MainActivity)?.removeRandomItems(quantity)
        }

        addOneButton.setOnClickListener {
            (activity as? MainActivity)?.addOneItem()
        }

        removeOneButton.setOnClickListener {
            (activity as? MainActivity)?.removeOneItem()
        }

        return rootView
    }
}

