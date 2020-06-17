package com.tinyapps.transactions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Switch
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.tinyapps.transactions.model.Transaction
import com.tinyapps.transactions.model.Wallet
import com.tinyapps.transactions.ui.TransactionsTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val enableDarkMode = state { false }
            TransactionsTheme(enableDarkMode) {
                Column {
                    val onCheckChanged = { _: Boolean ->
                        enableDarkMode.value = !enableDarkMode.value
                    }
                    HeaderComponent(
                        enableDarkMode = enableDarkMode.value,
                        onCheckChanged = onCheckChanged
                    )
                    WalletsComponent(listOf(Wallet("Account X", 93993)))
                    TransactionsComponent(
                        listOf(
                            Transaction("SELL GU", 323, 12321421, "End Of Trend Up")
                            , Transaction("BUY UCAD", 151, 12321421, "Block Trend DOWN")
                            , Transaction("SELL EF", -131, 12321421, "End Trend Up")
                            , Transaction("BUY EN", -152, 12321421, "Block Trend DOWN")
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderComponent(enableDarkMode: Boolean, onCheckChanged: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colors.surface
    ) {
        // Row is a composable that places its children in a horizontal sequence. You
        // can think of it similar to a LinearLayout with the horizontal orientation.
        Row(
            modifier = Modifier.padding(16.dp), verticalGravity = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // A pre-defined composable that's capable of rendering a switch. It honors the Material
            // Design specification.
            Text(
                text = "Accounts",
                style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onSurface),
                modifier = Modifier.padding(start = 8.dp)
            )
            Switch(checked = enableDarkMode, onCheckedChange = onCheckChanged)
        }
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
                                fontWeight = FontWeight.W100
                            )
                        )
                        Text(
                            "${wallet.balance}$",
                            style = TextStyle(
                                fontSize = 30.sp,
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

            for ((index, transaction) in transactions.withIndex()) {
                val isProfit = transaction.amount > 0
                Card(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier.fillMaxWidth().height(120.dp).padding(8.dp)
                ) {
                    Row(verticalGravity = Alignment.CenterVertically) {

                        Divider(
                            modifier = Modifier.width(4.dp).fillMaxHeight(),
                            color = if (isProfit) Color.Green else Color.Red
                        )

                        DateComponent(date = transaction.date)

                        Column(modifier = Modifier.weight(0.7f)) {
                            Text(
                                transaction.name,
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W700
                                )
                            )
                            Text(
                                transaction.comment,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.W100
                                )
                            )
                        }


                        Text(
                            "${(if (isProfit) "+" else "")} ${transaction.amount}$",
                            modifier = Modifier.weight(0.3f),
                            style = TextStyle(
                                fontSize = 18.sp,
                                color = if (isProfit) Color.Green else Color.Red,
                                fontWeight = FontWeight.W300
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
                    fontWeight = FontWeight.W700
                )
            )
            Text(
                "May",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W200
                )
            )
        }
    }
}

@Composable
fun FilterComponent() {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement
            .SpaceBetween
    ) {
        Text(
            "Transaction history", style = TextStyle(
                fontWeight = FontWeight.W200, fontSize = 16.sp
            )
        )
        Text(
            "Filter", style = TextStyle(
                fontWeight = FontWeight.W100, fontSize = 14.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val enableDarkMode = state { false }
    TransactionsTheme(darkTheme = enableDarkMode) {
        WalletsComponent(listOf(Wallet("Account X", 93993)))
    }
}