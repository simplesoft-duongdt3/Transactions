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
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.graphics.HorizontalGradient
import androidx.ui.graphics.TileMode
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp
import com.tinyapps.common_jvm.extension.number.format
import com.tinyapps.transactions.model.Transaction
import com.tinyapps.transactions.model.Wallet
import com.tinyapps.transactions.ui.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var openDialog = state { false }
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = {

                        },
                        backgroundColor = filter,
                        contentColor = Color.White
                    ) {
                        Icon(asset = Icons.Filled.Add)
                    }
                },
                bodyContent = {
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
                            WalletsComponent(
                                listOf(
                                    Wallet("Account X", 93993),
                                    Wallet("Account Y", 33443),
                                    Wallet("Account Z", 43993)
                                )
                            )

                            TransactionsComponent(
                                listOf(
                                    Transaction("SELL GU", 323, 12321421, "End Of Trend Up")
                                    , Transaction("BUY UCAD", 151, 12321421, "Block Trend DOWN")
                                    , Transaction("BUY UCAD", -151, 12321421, "Block Trend DOWN")
                                    , Transaction("BUY UCAD", 151, 12321421, "Block Trend DOWN")
                                    , Transaction("BUY UCAD", -151, 12321421, "Block Trend DOWN")
                                    , Transaction("BUY UCAD", 151, 12321421, "Block Trend DOWN")
                                    , Transaction("SELL EF", -131, 12321421, "End Trend Up")
                                    , Transaction("SELL EF", 131, 12321421, "End Trend Up")
                                    , Transaction("SELL EF", 131, 12321421, "End Trend Up")
                                    , Transaction("SELL EF", -131, 12321421, "End Trend Up")
                                    , Transaction("SELL EF", 131, 12321421, "End Trend Up")
                                    , Transaction("SELL EF", -131, 12321421, "End Trend Up")
                                    , Transaction("BUY EN", -152, 12321421, "Block Trend DOWN")
                                )
                            )
                            FilterOptionComponent()
                        }


                    }
                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
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


@Preview("Dialog Filter")
@Composable
fun DialogFilterPreview() {
    FilterOptionComponent()
}