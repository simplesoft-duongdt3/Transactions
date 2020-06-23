package com.tinyapps.transactions.ui

import android.util.Log
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.HorizontalGradient
import androidx.ui.graphics.TileMode
import androidx.ui.layout.*
import androidx.ui.layout.RowScope.weight
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Close
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextAlign
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.tinyapps.common_jvm.extension.number.format
import com.tinyapps.transactions.model.Transaction
import com.tinyapps.transactions.model.Wallet
import com.tinyapps.transactions.ui.state.AmountState
import com.tinyapps.transactions.ui.state.AppState
import com.tinyapps.transactions.ui.state.TagState
import com.tinyapps.transactions.ui.state.TypeState

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
fun TransactionsComponent(transactions: List<Transaction>) {
    VerticalScroller(modifier = Modifier.fillMaxHeight().padding(8.dp)) {
        Column {

            val gradientBrush = HorizontalGradient(
                colors = listOf(gradientStart, gradientEnd),
                startX = 0f,
                endX = 900f,
                tileMode = TileMode.Clamp
            )
            for ((index, transaction) in transactions.withIndex()) {
                val isProfit = transaction.amount > 0

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

                        DateComponent(date = transaction.date)

                        Column(modifier = Modifier.weight(0.7f)) {
                            Text(
                                transaction.name,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = textPrimary,
                                    fontWeight = FontWeight.W700
                                )
                            )
                            Text(
                                transaction.comment,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = textSecondary,
                                    fontWeight = FontWeight.W100
                                )
                            )
                        }


                        Text(
                            "${(if (isProfit) "+" else "")}${transaction.amount.format()}$",
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
        }
    }
}

@Composable
fun DateComponent(date: Long) {
    Card(
        shape = RoundedCornerShape(4.dp),
        elevation = 4.dp,
        modifier = Modifier.width(70.dp).height(70.dp).padding(8.dp)
    ) {
        Column(horizontalGravity = Alignment.CenterHorizontally) {
            Text(
                "12",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = textPrimary,
                    fontWeight = FontWeight.W700
                )
            )
            Text(
                "May",
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
        modifier = Modifier.fillMaxWidth().height(40.dp).padding(start = 16.dp, end = 16.dp),
        verticalGravity = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Transaction history", style = TextStyle(
                fontWeight = FontWeight.W300, fontSize = 16.sp, color = textPrimary
            )
        )
        Row(verticalGravity = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(end = 4.dp),
                text = "Filter", style = TextStyle(
                    fontWeight = FontWeight.W100, fontSize = 14.sp, color = textSecondary
                )
            )
            FilterCircle(countFilter = 5, appState = appState)
        }

    }
}

@Composable
fun FilterCircle(countFilter: Int, appState: AppState) {
    val onClick: () -> Unit = {
        appState.openDialog = true
        Log.d("Tien", "appState -> true")
    }
    Box(
        modifier = Modifier.width(20.dp).height(20.dp).clickable(onClick = onClick),
        shape = CircleShape,
        backgroundColor = filter,
        gravity = ContentGravity.Center
    ) {
        Text(text = "$countFilter", style = TextStyle(color = Color.White, fontSize = 12.sp))
    }
}

@Composable
fun FilterOptionComponent(appState: AppState) {
    var amountState by state { AmountState() }
    var typeState by state { TypeState() }
    var tagState by state { TagState() }
    if (appState.openDialog) {
        Box(backgroundColor = filter, modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f).padding(16.dp)
            ) {
                Row(
                    verticalGravity = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Icon(asset = Icons.Default.Close,
                        tint = filterText,
                        modifier = Modifier.clickable(
                            onClick = { appState.openDialog = false }
                        ))
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
                            text = "$${amountState.value?.format()}",
                            style = TextStyle(color = filterText, fontWeight = FontWeight.W700)
                        )
                    }
                    Slider(
                        value = amountState.value ?: amountState.max,
                        valueRange = 0f..amountState.max,
                        steps = 5,
                        color = filterText,
                        onValueChange = { amountState.value = it })
                }

                FilterByType(listOf("Revenue", "Expenses"), typeState)
                FilterHeaderLine("TAGS")
                FilterByTag(listOf("Fx", "Coin", "Food", "Drink"), tagState)
            }
            Box(
                modifier = Modifier.fillMaxWidth().height(50.dp).clickable(onClick = {
                    appState.openDialog = false
                }),
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


}

@Composable
private fun FilterByType(options: List<String>, formState: TypeState) {
    FilterHeaderLine("TYPE")
    RadioGroup {
        Column(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            options.forEach { text ->
                val selected = text == formState.selectedOption
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

@Composable
private fun FilterByTag(tags: List<String>, formState: TagState) {
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
