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
import androidx.ui.material.*
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.tinyapps.common_jvm.extension.number.format
import com.tinyapps.transactions.model.Transaction
import com.tinyapps.transactions.model.Wallet
import com.tinyapps.transactions.ui.state.AppState

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

            FilterComponent()
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
fun FilterComponent() {
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
            FilterCircle(countFilter = 5)
        }

    }
}

@Composable
fun FilterCircle(countFilter: Int) {
    var appState by state { AppState() }
    val onClick: () -> Unit = {
        appState = appState.copy(openDialog = true)
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
fun FilterOptionComponent() {
        Log.d("Tien", "appState -> state -> true")
        AlertDialog(
            onCloseRequest = {
            },
            title = {
                Text(text = "Title")
            },
            text = {
                Text("This is alert dialog in jetpack compose!")
            },
            confirmButton = {
                Button(text = {
                    Text(text = "Apply")
                }, onClick = {
                    Log.d("Tien", "appState -> state -> false")
                })
            },
            buttonLayout = AlertDialogButtonLayout.SideBySide
        )

}
