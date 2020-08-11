package com.tinyapps.transactions.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.tinyapps.common_jvm.extension.string.isChipEmpty
import com.tinyapps.common_jvm.extension.string.moneyToDouble
import com.tinyapps.common_jvm.extension.string.toDateLong
import com.tinyapps.transactions.R
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*


/**
 * Created by ChuTien on ${1/25/2017}.
 */
class TransactionInputFragment : DialogFragment() {
    var callbackResult: CallbackResult? = null
    private var requestCode = 0

    private lateinit var imgBack: ImageView
    private lateinit var edName: EditText
    private lateinit var edAmount: EditText
    private lateinit var txtDate: TextView
    private lateinit var edDescription: EditText
    private lateinit var edTags: EditText
    private lateinit var chipTags: ChipGroup
    private lateinit var scrollViewChips: HorizontalScrollView

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
        imgBack = view.findViewById(R.id.img_transaction_input_back)
        edName = view.findViewById(R.id.edName)
        edAmount = view.findViewById(R.id.edAmount)
        txtDate = view.findViewById(R.id.txtDate)
        edDescription = view.findViewById(R.id.edDescription)
        edTags = view.findViewById(R.id.edTags)
        chipTags = view.findViewById(R.id.chip_tags)
        scrollViewChips = view.findViewById(R.id.scrollViewChips)
        txtDate.setOnClickListener {
            showDatePicker()
        }
        edTags.doAfterTextChanged { afterText ->
            handleChipInput(afterText)
        }
        imgBack.setOnClickListener {
            hideKeyboard(it)
            dismiss()
        }
        btnAddTransaction.setOnClickListener {
            sendDataResult()
            dismiss()
        }
    }

    private fun handleChipInput(afterText: Editable?) {
        if (afterText!=null && !afterText.toString().isChipEmpty() && afterText[afterText.lastIndex] == '\n') {
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

            if (!scrollViewChips.canScrollHorizontally(1)) {
                scrollViewChips.post { scrollViewChips.fullScroll(View.FOCUS_RIGHT) }
            }
        }
    }

    private fun showDatePicker(){
        val cur_calender = Calendar.getInstance()
        val datePicker: DatePickerDialog = DatePickerDialog.newInstance(
            { _, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar[Calendar.YEAR] = year
                calendar[Calendar.MONTH] = monthOfYear
                calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                txtDate.text = "${calendar.get(Calendar.DAY_OF_MONTH)}/" +
                        "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"
            },
            cur_calender[Calendar.YEAR],
            cur_calender[Calendar.MONTH],
            cur_calender[Calendar.DAY_OF_MONTH]
        )
        //set dark light
        //set dark light
        datePicker.isThemeDark = false
        datePicker.minDate = cur_calender
        context?.let{
            datePicker.accentColor = ContextCompat.getColor(it,R.color.bg_btn_add_transaction)
            datePicker.setOkColor(ContextCompat.getColor(it,R.color.white))
            datePicker.setCancelColor(ContextCompat.getColor(it,R.color.white))
        }

        datePicker.show(childFragmentManager,"OK")
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
            date = txtDate.text.toString().toDateLong(),
            amount = edAmount.text.toString().moneyToDouble(),
            comment = edDescription.text.toString(),
            tags = tags,
            id = "1"
        )
        callbackResult?.sendResult(requestCode = requestCode, obj = transaction)
    }

    interface CallbackResult {
        fun sendResult(requestCode: Int, obj: Any)
    }

    private fun hideKeyboard(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}