package com.tinyapps.transactions.ui

import android.util.Log
import androidx.compose.*
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.lazy.LazyColumnItems
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.HorizontalGradient
import androidx.ui.graphics.TileMode
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.TextFieldValue
import androidx.ui.layout.*
import androidx.ui.layout.ColumnScope.gravity
import androidx.ui.layout.RowScope.weight
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Close
import androidx.ui.material.ripple.RippleIndication
import androidx.ui.res.imageResource
import androidx.ui.res.stringResource
import androidx.ui.res.vectorResource
import androidx.ui.text.TextRange
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.Dp
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import androidx.ui.viewinterop.AndroidView
import com.google.android.material.datepicker.MaterialDatePicker
import com.hootsuite.nachos.NachoTextView
import com.hootsuite.nachos.terminator.ChipTerminatorHandler
import com.tinyapps.common_jvm.extension.date.format
import com.tinyapps.common_jvm.extension.nullable.defaultZero
import com.tinyapps.common_jvm.extension.number.format
import com.tinyapps.common_jvm.extension.string.moneyToDouble
import com.tinyapps.presentation.features.transactions.model.Transaction
import com.tinyapps.presentation.features.transactions.model.Wallet
import com.tinyapps.transactions.R
import com.tinyapps.transactions.ui.listener.IFilter
import com.tinyapps.transactions.ui.state.*
import java.util.*


/**
 * Created by ChuTien on ${1/25/2017}.
 */

@Composable
fun HeaderComponent(enableDarkMode: Boolean, onCheckChanged: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        verticalGravity = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(start = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.accounts),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = titleText,
                    fontWeight = FontWeight.W700
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Switch(checked = enableDarkMode, onCheckedChange = onCheckChanged)
    }
}

@Composable
fun WalletsComponent(wallets: List<Wallet>) {
    HorizontalScroller(modifier = Modifier.fillMaxWidth()) {
        val context = ContextAmbient.current
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        // Compute the screen width using the actual display width and the density of the display.
        val screenWidth = displayMetrics.widthPixels / displayMetrics.density
        val spacing = 16.dp
        Row {
            for (wallet in wallets) {
                Card(
                    shape = RoundedCornerShape(4.dp),
                    color = accountBackground,
                    modifier = Modifier.fillMaxWidth().height(150.dp).padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.preferredWidth(screenWidth.dp - (spacing * 2)),

                        horizontalGravity = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            wallet.name,
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = textSecondary,
                                fontWeight = FontWeight.W300
                            )
                        )
                        Text(
                            "${wallet.balance.format()}$",
                            style = TextStyle(
                                fontSize = 30.sp,
                                color = textPrimary,
                                fontWeight = FontWeight.W700
                            )
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun TransactionsComponent(
    transactionsLiveData: LiveData<List<Transaction>>,
    appState: AppState
) {
    val transactions by transactionsLiveData.observeAsState(initial = emptyList())

    appState.numOfTransaction = transactions.size
    if (transactions.isNotEmpty()) {
        LazyColumnItems(
            items = transactions,
            modifier = Modifier.fillMaxHeight().padding(8.dp)
        ) {
            TransactionItem(it = it)
        }
    } else {
        EmptyView()
    }
}


@Composable
fun TransactionItem(it: Transaction) {
    val gradientBrush = HorizontalGradient(
        colors = listOf(gradientStart, gradientEnd),
        startX = 0f,
        endX = 900f,
        tileMode = TileMode.Clamp
    )
    val isProfit = it.amount > 0

    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth().height(120.dp).padding(8.dp)
    ) {
        Row(
            verticalGravity = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.drawBackground(gradientBrush)
        ) {

            Divider(
                modifier = Modifier.width(4.dp).fillMaxHeight(),
                color = if (isProfit) positive else negative
            )

            DateComponent(timeInMillis = it.date)

            Column(
                modifier = Modifier.weight(0.7f).fillMaxHeight().padding(top = 16.dp),
                horizontalGravity = Alignment.Start
            ) {
                Text(
                    it.name,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = textPrimary,
                        fontWeight = FontWeight.W700
                    )
                )
                Text(
                    it.comment,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = textSecondary,
                        fontWeight = FontWeight.W100
                    )
                )
                TransactionTags(tags = it.tags)
            }

            Text(
                "${(if (isProfit) "+" else "")}${it.amount.format()}$",
                modifier = Modifier.weight(0.3f),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = if (isProfit) textPositive else textNegative,
                    fontWeight = FontWeight.W400
                )
            )

        }

    }
}

@Composable
fun TransactionTags(tags: List<String>) {
    Row(
        modifier = Modifier.wrapContentHeight()
    ) {
        tags.forEach {
            Card(
                modifier = Modifier.wrapContentWidth().wrapContentHeight().padding(2.dp),
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    modifier = Modifier.padding(2.dp).wrapContentHeight(),
                    text = "#$it",
                    style = TextStyle(
                        color = filterSection,
                        fontWeight = FontWeight.W300,
                        fontSize = 11.sp
                    )
                )
            }
        }

    }
}

@Composable
fun DateComponent(timeInMillis: Long) {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis
    val date = calendar.time
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 4.dp,
        modifier = Modifier.width(70.dp).height(70.dp).padding(8.dp)
    ) {
        Column(horizontalGravity = Alignment.CenterHorizontally) {
            Text(
                date.format("dd"),
                style = TextStyle(
                    fontSize = 18.sp,
                    color = textPrimary,
                    fontWeight = FontWeight.W700
                )
            )
            Text(
                date.format("MMM"),
                style = TextStyle(
                    fontSize = 16.sp,
                    color = textSecondary,
                    fontWeight = FontWeight.W200
                )
            )
        }
    }
}

@Composable
fun FilterComponent(appState: AppState) {

    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp).padding(start = 16.dp, end = 16.dp),
        verticalGravity = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Stack(modifier = Modifier.wrapContentWidth().fillMaxHeight()) {
            Text(
                modifier = Modifier.gravity(Alignment.CenterStart),
                text = stringResource(id = R.string.transaction_history), style = TextStyle(
                    fontWeight = FontWeight.W300, fontSize = 16.sp, color = textPrimary
                )
            )
            Box(
                modifier = Modifier.gravity(Alignment.TopEnd)
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .padding(top = 12.dp, end = 8.dp)
            ) {
                Badge(num = appState.numOfTransaction)
            }
        }

        Surface(
            shape = RoundedCornerShape(16.dp),
            color = filterSection
        ) {
            Row(
                verticalGravity = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(onClick = {
                        appState.updateOpenDialogFlag(true)
                        Log.d("Tien", "Filter Clicked")
                    }, indication = RippleIndication(bounded = true, radius = 20.dp))
            ) {
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 4.dp),
                    text = " Filter ", style = TextStyle(
                        fontWeight = FontWeight.W700, fontSize = 12.sp, color = filterText
                    )
                )
                FilterCircle(countFilter = 5)
            }
        }

    }
}

@Composable
fun FilterCircle(sizeDp: Int = 20, countFilter: Int) {
    Box(
        modifier = Modifier.width(sizeDp.dp).height(sizeDp.dp),
        shape = CircleShape,
        backgroundColor = filter,
        gravity = ContentGravity.Center
    ) {
        Text(
            text = "$countFilter",
            style = TextStyle(color = Color.White, fontSize = 12.sp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Badge(num: Int) {
    Box(
        modifier = Modifier.wrapContentWidth(align = Alignment.CenterHorizontally)
            .wrapContentHeight(align = Alignment.CenterVertically),
        shape = RoundedCornerShape(4.dp),
        backgroundColor = filter,
        gravity = ContentGravity.Center
    ) {
        Text(
            modifier = Modifier.padding(2.dp),
            text = "$num",
            style = TextStyle(color = Color.White, fontSize = 10.sp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun FilterOptionComponent(
    tagsLiveData: LiveData<List<String>>,
    iFilter: IFilter,
    tagState: TagState,
    typeState: TypeState,
    amountState: AmountState
) {
    val amountFilterState = remember { AmountFilterState() }
    val tagFilterState = remember { TagFilterState() }
    val typeFilterState = remember { TypeFilterState() }
    amountFilterState.value = amountState.value
    tagFilterState.selectedOption = tagState.selectedOption
    typeFilterState.selectedOption = typeState.selectedOption

    Box(backgroundColor = filter, modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(16.dp)
        ) {
            Row(
                verticalGravity = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = filter
                ) {
                    Icon(
                        asset = Icons.Default.Close,
                        tint = filterText,
                        modifier = Modifier.clickable(
                            onClick = {
                                iFilter.fillerCancel()
                            }, indication = RippleIndication(bounded = true, radius = 16.dp)
                        )
                    )
                }
                Text(
                    modifier = Modifier.weight(1f).padding(end = 16.dp),
                    text = stringResource(id = R.string.filters),
                    style = TextStyle(color = filterText, fontWeight = FontWeight.W700),
                    textAlign = TextAlign.Center
                )
            }
            FilterHeaderLine("LIMIT")
            Column(modifier = Modifier.padding(top = 8.dp)) {
                Box(modifier = Modifier.fillMaxWidth(), gravity = ContentGravity.Center) {
                    Text(
                        text = "$${amountFilterState.value?.format()}",
                        style = TextStyle(color = filterText, fontWeight = FontWeight.W700)
                    )
                }
                Slider(
                    value = amountFilterState.value?.toFloat()
                        ?: amountFilterState.max.toFloat(),
                    valueRange = 0f..amountFilterState.max.toFloat(),
                    color = filterText,
                    onValueChange = { amountFilterState.value = it.toDouble().defaultZero() })
            }

            FilterByType(
                listOf(
                    stringResource(id = R.string.all),
                    stringResource(id = R.string.revenue),
                    stringResource(id = R.string.expenses)
                ), typeFilterState
            )
            FilterHeaderLine(stringResource(id = R.string.tags))
            FilterByTag(tagsLiveData.value ?: listOf(), tagFilterState)
        }
        Box(
            modifier = Modifier.fillMaxWidth().height(50.dp)
                .clickable(onClick = {
                    iFilter.fillerResults(
                        amountState = amountFilterState,
                        tagState = tagFilterState,
                        typeState = typeFilterState
                    )
                }, indication = RippleIndication(bounded = true)),
            gravity = ContentGravity.Center,
            backgroundColor = filterText
        ) {
            Text(
                text = "APPLY",
                style = TextStyle(fontSize = TextUnit.Sp(20), color = filterSection)
            )
        }
    }

}

@Composable
private fun FilterByType(options: List<String>, formState: TypeFilterState) {
    FilterHeaderLine("TYPE")
    RadioGroup {
        Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            options.forEachIndexed { index, text ->
                run {
                    val selected: Boolean = if (formState.selectedOption.isNotEmpty()) {
                        text == formState.selectedOption
                    } else {
                        index == 0
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = text,
                            style = TextStyle(color = filterText, fontWeight = FontWeight.W500)
                        )
                        RadioButton(
                            selected = selected,
                            onSelect = { formState.selectedOption = text },
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterByTag(tags: List<String>, formState: TagFilterState) {
    LazyColumnItems(
        items = tags,
        modifier = Modifier.fillMaxHeight().padding(8.dp)
    ) {
        val selected = formState.selectedOption.contains(it)
        val text = it
        Row(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = text,
                style = TextStyle(color = filterText, fontWeight = FontWeight.W500)
            )
            Checkbox(
                checked = selected,
                onCheckedChange = {
                    if (formState.selectedOption.contains(text)) {
                        formState.selectedOption.remove(text)
                    } else {
                        formState.selectedOption.add(text)
                    }
                }
            )
        }
    }

}

@Composable
private fun FilterHeaderLine(title: String) {
    Row(
        verticalGravity = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            text = title,
            style = TextStyle(color = filterSection, fontWeight = FontWeight.W500),
            modifier = Modifier.width(80.dp)
        )
        Divider(color = filterSection, thickness = 1.dp)
    }
}

@Composable
private fun EmptyView() {
    val image = imageResource(id = R.drawable.img_empty)
    Box(
        backgroundColor = emptyBackground
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
            , horizontalGravity = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(image)
            Text(
                text = stringResource(id = R.string.empty_title),
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.W700,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                modifier = Modifier.padding(bottom = 56.dp),
                text = stringResource(id = R.string.empty_content),
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.W200,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}

@Composable
fun InputBox(
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    width: Dp,
    height: Dp,
    value: TextFieldValue,
    onvalueChange: (TextFieldValue) -> Unit
) {
    class FocusInputState(focused: Boolean = false) {
        var focusInput = mutableStateOf(focused)
    }

    val focusState = remember { FocusInputState() }
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(4.dp),
        border = Border(
            1.dp,
            if (focusState.focusInput.value) borderInputBoxFocused else borderInputBox
        ),
        modifier = Modifier.width(width).height(height)
    ) {
        TextField(
            textStyle = TextStyle(textAlign = TextAlign.Start, fontSize = 14.sp),
            modifier = Modifier.gravity(align = Alignment.CenterHorizontally).padding(2.dp),
            onFocusChange = { focused -> focusState.focusInput.value = focused },
            keyboardType = keyboardType,
            imeAction = imeAction,
            value = value,
            onValueChange = onvalueChange,
            cursorColor = borderInputBox
        )
    }
}

@Composable
fun DateBox(
    supportFragmentManager: FragmentManager,
    width: Dp,
    height: Dp
) {
    var dateText by state { String() }
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(4.dp),
        border = Border(
            1.dp,
            borderInputBox
        ),
        modifier = Modifier.width(width).height(height).clickable(onClick = {
            val builder = MaterialDatePicker.Builder.datePicker()
                .also {
                    it.setTitleText("Pick Date")
                }

            val datePicker = builder.build()

            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.time = Date(it)
                dateText = "${calendar.get(Calendar.DAY_OF_MONTH)}/" +
                        "${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"

            }

            datePicker.show(supportFragmentManager, "DatePicker")
        })
    ) {
        Box(
            gravity = Alignment.Center
        ) {
            Text(
                text = dateText,
                textAlign = TextAlign.Center,
                style = TextStyle(textAlign = TextAlign.Start, fontSize = 14.sp)
            )
        }

    }
}

@Composable
fun InputTagsBox(
    width: Dp,
    height: Dp,
    transactionInputState: TransactionInputState
) {

    class FocusInputState(focused: Boolean = false) {
        var focusInput = mutableStateOf(focused)
    }

    var tagsView: NachoTextView?

    val focusState = remember { FocusInputState() }
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(4.dp),
        border = Border(
            1.dp,
            if (focusState.focusInput.value) borderInputBoxFocused else borderInputBox
        ),
        modifier = Modifier.width(width).height(height)
    ) {
        AndroidView(resId = R.layout.view_chip) {
            tagsView = it.findViewById(R.id.tags_view)
            tagsView?.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL)
            tagsView?.addTextChangedListener { editable ->
                Log.d("Tien Test", "addTextChangedListener = $editable")
                tagsView?.chipValues?.let { chipvalues -> transactionInputState.tags = chipvalues }
            }
            tagsView?.setOnFocusChangeListener { _, hasFocus ->
                focusState.focusInput.value = hasFocus
            }
        }
    }

}


@Composable
fun TransactionInputBox(
    supportFragmentManager: FragmentManager,
    transactionInputState: TransactionInputState,
    appState: AppState
) {
    val image = vectorResource(id = R.drawable.ic_arrow_back)
    val styleTitleText =
        TextStyle(color = titleAddTransaction, fontSize = 13.sp, fontWeight = FontWeight.W500)
    Box(backgroundColor = emptyBackground) {
        VerticalScroller(modifier = Modifier.fillMaxWidth().weight(1f)) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                        .gravity(Alignment.CenterHorizontally),
                    verticalGravity = Alignment.CenterVertically
                ) {
                    Image(
                        asset = image,
                        modifier = Modifier.width(40.dp).height(40.dp)
                            .padding(8.dp).clickable(onClick = {
                                appState.updateTransactionInputFlag(false)
                            })
                    )
                    Text(
                        text = stringResource(id = R.string.add_transaction),
                        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(end = 40.dp),
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W300,
                            color = titleAddTransaction,
                            textAlign = TextAlign.Center
                        )
                    )
                }

                Spacer(modifier = Modifier.fillMaxWidth().height(40.dp))
                Column(
                    modifier = Modifier.padding(start = 80.dp).fillMaxWidth().fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = stringResource(id = R.string.name),
                        style = styleTitleText
                    )
                    InputBox(
                        width = 200.dp,
                        height = 48.dp,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        value = TextFieldValue(
                            text = transactionInputState.name,
                            selection = TextRange(transactionInputState.name.length)
                        ),
                        onvalueChange = { textFieldValue ->
                            transactionInputState.name = textFieldValue.text
                        })

                    Text(
                        text = stringResource(id = R.string.amount),
                        style = styleTitleText
                    )
                    InputBox(
                        width = 100.dp,
                        height = 48.dp,
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next,
                        value = TextFieldValue(
                            text = transactionInputState.amount.defaultZero().format(),
                            selection = TextRange(
                                transactionInputState.amount.defaultZero()
                                    .format().length
                            )
                        ),
                        onvalueChange = { textFieldValue ->
                            if (textFieldValue.text.isNotEmpty()) {
                                transactionInputState.amount =
                                    textFieldValue.text.moneyToDouble()
                            }

                        })

                    Text(
                        text = stringResource(id = R.string.date),
                        style = styleTitleText
                    )
                    DateBox(
                        supportFragmentManager = supportFragmentManager,
                        width = 200.dp,
                        height = 48.dp
                    )
                    Text(
                        text = stringResource(id = R.string.description),
                        style = styleTitleText
                    )
                    InputBox(
                        width = 200.dp,
                        height = 80.dp,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        value = TextFieldValue(
                            text = transactionInputState.comment
                            , selection = TextRange(transactionInputState.comment.length)
                        ),
                        onvalueChange = { textFieldValue ->
                            transactionInputState.comment =
                                textFieldValue.text
                        })
                    Text(
                        text = stringResource(id = R.string.tags),
                        style = styleTitleText
                    )
                    InputTagsBox(
                        width = 200.dp,
                        height = 56.dp, transactionInputState = transactionInputState
                    )

                }

            }
        }
        Box(
            modifier = Modifier.fillMaxWidth().height(50.dp)
                .clickable(onClick = {
                    Log.d(
                        "Tien Test",
                        "Name -> ${transactionInputState.name}\nAmount -> ${transactionInputState.amount}\nDate -> ${transactionInputState.date}\nDescription -> ${transactionInputState.comment}" +
                                "Tags -> ${transactionInputState.tags}"
                    )
                }, indication = RippleIndication(bounded = true)),
            gravity = ContentGravity.Center,
            backgroundColor = filter
        ) {
            Text(
                text = stringResource(id = R.string.add_transaction).toUpperCase(Locale.getDefault()),
                style = TextStyle(fontSize = TextUnit.Sp(20), color = filterText)
            )
        }
    }
}
