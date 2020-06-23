package com.tinyapps.transactions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.getValue
import androidx.compose.setValue
import androidx.compose.state
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.Stack
import androidx.ui.material.FloatingActionButton
import androidx.ui.material.Scaffold
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.tooling.preview.Preview
import com.tinyapps.transactions.model.Transaction
import com.tinyapps.transactions.model.Wallet
import com.tinyapps.transactions.ui.*
import com.tinyapps.transactions.ui.state.AppState

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var appState by state { AppState() }
            Scaffold(
                floatingActionButton = {
                    if (!appState.openDialog) {
                        FloatingActionButton(
                            onClick = {

                            },
                            backgroundColor = filter,
                            contentColor = Color.White
                        ) {
                            Icon(asset = Icons.Filled.Add)
                        }
                    }

                },
                bodyContent = {
                    val enableDarkMode = state { false }

                    TransactionsTheme(enableDarkMode) {
                        Stack {
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
                                FilterComponent(appState)
                                TransactionsComponent(
                                    listOf(
                                        Transaction("SELL GU", 323, 12321421, "End Of Trend Up")
                                        ,
                                        Transaction("BUY UCAD", 151, 12321421, "Block Trend DOWN")
                                        ,
                                        Transaction("BUY UCAD", -151, 12321421, "Block Trend DOWN")
                                        ,
                                        Transaction("BUY UCAD", 151, 12321421, "Block Trend DOWN")
                                        ,
                                        Transaction("BUY UCAD", -151, 12321421, "Block Trend DOWN")
                                        ,
                                        Transaction("BUY UCAD", 151, 12321421, "Block Trend DOWN")
                                        ,
                                        Transaction("SELL EF", -131, 12321421, "End Trend Up")
                                        ,
                                        Transaction("SELL EF", 131, 12321421, "End Trend Up")
                                        ,
                                        Transaction("SELL EF", 131, 12321421, "End Trend Up")
                                        ,
                                        Transaction("SELL EF", -131, 12321421, "End Trend Up")
                                        ,
                                        Transaction("SELL EF", 131, 12321421, "End Trend Up")
                                        ,
                                        Transaction("SELL EF", -131, 12321421, "End Trend Up")
                                        ,
                                        Transaction("BUY EN", -152, 12321421, "Block Trend DOWN")
                                    )
                                )
                            }
                            FilterOptionComponent(appState)

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
                    Transaction("SELL GU", 323, 123121421, "End Of Trend Up")
                    , Transaction("BUY UCAD", 151, 13214121, "Block Trend DOWN")
                    , Transaction("SELL EF", -131, 17232141, "End Trend Up")
                    , Transaction("BUY EN", -152, 12321421, "Block Trend DOWN")
                )
            )
        }
    }
}


@Preview("Dialog Filter")
@Composable
fun DialogFilterPreview() {
    var appState by state { AppState() }
    FilterOptionComponent(appState)
}