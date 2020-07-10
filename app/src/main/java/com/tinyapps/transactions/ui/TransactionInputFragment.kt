package com.tinyapps.transactions.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.datepicker.MaterialDatePicker
import com.tinyapps.common_jvm.extension.string.moneyToDouble
import com.tinyapps.common_jvm.extension.string.toDateLong
import com.tinyapps.transactions.R
import java.util.*

/**
 * Created by ChuTien on ${1/25/2017}.
 */
class TransactionInputFragment : DialogFragment() {
    var callbackResult: CallbackResult? = null
    var requestCode = 0

    private lateinit var edName: EditText
    private lateinit var edAmount: EditText
    private lateinit var edDate: EditText
    private lateinit var edDescription: EditText
    private lateinit var edTags: EditText
    private lateinit var chipTags: ChipGroup

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.view_transaction_input, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnAddTransaction = view.findViewById<AppCompatButton>(R.id.btn_add_transaction)
        edName = view.findViewById(R.id.edName)
        edAmount = view.findViewById(R.id.edAmount)
        edDate = view.findViewById(R.id.edDate)
        edDescription = view.findViewById(R.id.edDescription)
        edTags = view.findViewById(R.id.edTags)
        chipTags = view.findViewById(R.id.chip_tags)
        edDate.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
                .also {
                    it.setTitleText("Pick Date")
                }

            val datePicker = builder.build()

            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.time = Date(it)
                edDate.setText("${calendar.get(Calendar.DAY_OF_MONTH)}/" +
                        "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}")
            }

            datePicker.show(childFragmentManager, "DatePicker")
        }
        edTags.addTextChangedListener(
            afterTextChanged = { afterText ->
                if (!afterText.isNullOrEmpty() && afterText[afterText.lastIndex] == '\n') {
                    //enter trigger
                    val chip = Chip(context)
                    chip.text = afterText.toString()
                    chip.chipIcon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_hashtag)
                    chip.isCloseIconVisible = true
                    chip.setChipIconTintResource(R.color.bg_btn_add_transaction)
                    // necessary to get single selection working
                    chip.isClickable = true
                    chip.isCheckable = false
                    chipTags.addView(chip as View)
                    chip.setOnCloseIconClickListener { chipTags.removeView(chip as View) }
                    edTags.setText("")
                }
            }
        )
        btnAddTransaction.setOnClickListener {
            sendDataResult()
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private fun sendDataResult() {
        val tags = arrayListOf<String>()
        for (i in 0 until chipTags.size) {
            tags.add((chipTags.getChildAt(i) as Chip).text.toString())
        }
        val transaction = com.tinyapps.presentation.features.transactions.model.Transaction(
            name = edName.text.toString(),
            date = edDate.text.toString().toDateLong(),
            amount = edAmount.text.toString().moneyToDouble(),
            comment = edDescription.text.toString(),
            tags = tags,
            id = "1"
        )
        callbackResult?.sendResult(requestCode = requestCode,obj = transaction)
    }

    interface CallbackResult {
        fun sendResult(requestCode: Int, obj: Any)
    }
}