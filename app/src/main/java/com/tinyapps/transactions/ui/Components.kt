package com.tinyapps.transactions.ui

import android.util.Log
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.remember
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
import androidx.ui.layout.*
import androidx.ui.layout.RowScope.weight
import androidx.ui.livedata.observeAsState
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Close
import androidx.ui.material.ripple.RippleIndication
import androidx.ui.res.imageResource
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.tinyapps.common_jvm.extension.date.format
import com.tinyapps.common_jvm.extension.number.format
import com.tinyapps.presentation.features.transactions.model.Transaction
import com.tinyapps.presentation.features.transactions.model.Wallet
import com.tinyapps.transactions.R
import com.tinyapps.transactions.ui.listener.IFilter
import com.tinyapps.transactions.ui.state.*
import java.util.*
import kotlin.math.abs

/**
 * Created by ChuTien on ${1/25/2017}.
 */

@Composable
fun HeaderComponent(enableDarkMode: Boolean, onCheckChanged: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        verticalGravity = Alignment.CenterVertically
    ) {
        // A pre-defined composable that's capable of rendering a switch. It honors the Material
        // Design specification.
        Row(
            modifier = Modifier.fillMaxWidth().weight(1f).padding(start = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Accounts",
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
            for ((index, wallet) in wallets.withIndex()) {
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
fun TransactionsComponent(transactionsLiveData : LiveData<List<Transaction>>,
    tagState: TagState,
    typeState: TypeState,
    amountState: AmountState,
    appState: AppState
) {
    val transactions by transactionsLiveData.observeAsState(initial = emptyList())
    val amountValue = amountState.value ?: amountState.max
    Log.d("Tien", "TransactionsComponent ${amountValue}")
    val listTransactions =
        when (typeState.selectedOption) {
            "Expenses" -> {
                transactions.filter { transaction -> (transaction.amount < 0 && abs(transaction.amount) < amountValue) }
            }
            "Revenue" -> {
                transactions.filter { transaction -> (transaction.amount >= 0 && abs(transaction.amount) < amountValue) }
            }
            else -> {
                transactions.filter { transaction -> abs(transaction.amount) < amountValue }
            }
        }
    Log.d("Tien", "TransactionsComponent $listTransactions")
    appState.numOfTransaction = listTransactions.size
    if (listTransactions.isNotEmpty()) {
        LazyColumnItems(
            items = listTransactions,
            modifier = Modifier.fillMaxHeight().padding(8.dp)
        ) {
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
                    modifier = Modifier.drawBackground(gradientBrush)
                ) {

                    Divider(
                        modifier = Modifier.width(4.dp).fillMaxHeight(),
                        color = if (isProfit) positive else negative
                    )

                    DateComponent(timeInMillis = it.date)

                    Column(modifier = Modifier.weight(0.7f)) {
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
    } else {
        EmptyView()
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
                text = "Transaction history     ", style = TextStyle(
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
    iFilter: IFilter
) {
    val amountFilterState = remember { AmountState() }
    val tagFilterState = remember { TagFilterState() }
    val typeFilterState = remember { TypeFilterState() }
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
                    Icon(asset = Icons.Default.Close,
                        tint = filterText,
                        modifier = Modifier.clickable(
                            onClick = {
                                iFilter.fillerCancel()
                            },indication = RippleIndication(bounded = true, radius = 16.dp)
                        ))
                }
                Text(
                    modifier = Modifier.weight(1f).padding(end = 16.dp),
                    text = "Filters",
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
                    value = amountFilterState.value?.toFloat() ?: amountFilterState.max.toFloat(),
                    valueRange = 0f..amountFilterState.max.toFloat(),
                    color = filterText,
                    onValueChange = { amountFilterState.value = it.toDouble() })
            }

            FilterByType(listOf("All", "Revenue", "Expenses"), typeFilterState)
            FilterHeaderLine("TAGS")
            FilterByTag(listOf("Fx", "Coin", "Food", "Drink"), tagFilterState)
        }
        Box(
            modifier = Modifier.fillMaxWidth().height(50.dp)
                .clickable(onClick = {
                    iFilter.fillerResults(
                        amountState = amountFilterState,
                        tagState = tagFilterState,
                        typeState = typeFilterState
                    )
                },indication = RippleIndication(bounded = true)),
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
    //todo Make tags can be multi choice
    Column(
        modifier = Modifier.fillMaxWidth() + Modifier.padding(top = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        tags.forEach { text ->
            val selected = text == formState.selectedOption
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
                    onCheckedChange = { formState.selectedOption = text })
            }
        }

    }
}

@Composable
private fun FilterHeaderLine(title: String) {
    Row(verticalGravity = Alignment.CenterVertically, modifier = Modifier.padding(top = 16.dp)) {
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
                text = "Oh crap, you've\ngot nothing.",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.W700,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
            )
            Text(
                modifier = Modifier.padding(bottom = 56.dp),
                text = "Use add button to add transactions",
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
