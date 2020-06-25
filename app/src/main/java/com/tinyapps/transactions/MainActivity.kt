package com.tinyapps.transactions

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.remember
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
import com.tinyapps.transactions.ui.listener.IFilter
import com.tinyapps.transactions.ui.state.*

class MainActivity : AppCompatActivity() {
    private val backPressHandler =
        BackPressHandler(true)

    override fun onBackPressed() {
        if (backPressHandler.onBackPress()) {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = remember { AppState(backPressHandler = backPressHandler) }
            val tagState = remember { TagState() }
            val typeState = remember { TypeState() }
            var amountState = remember { AmountState() }
            Scaffold(
                floatingActionButton = {
                    if (!appState.openDialog) {
                        FloatingActionButton(
                            onClick = {
                            //todo open input form
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
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction(),
                                        Transaction()
                                    ),
                                    tagState = tagState,
                                    typeState = typeState,
                                    amountState = amountState,
                                    appState = appState
                                )
                            }
                            if (appState.openDialog) {
                                FilterOptionComponent(iFilter = object : IFilter {
                                    override fun fillerResults(
                                        amountFilterState: AmountState,
                                        tagFilterState: TagFilterState,
                                        typeFilterState: TypeFilterState
                                    ) {
                                        Log.d(
                                            "Tien",
                                            "fillerResults ${appState.openDialog} ${typeFilterState.selectedOption}"
                                        )
                                        amountState = amountFilterState
                                        tagState.selectedOption = tagFilterState.selectedOption
                                        typeState.selectedOption =
                                            typeFilterState.selectedOption
                                        appState.updateOpenDialogFlag(false)
                                    }

                                    override fun fillerCancel() {
                                        Log.d("Tien", "fillerCancel ${appState.openDialog}")
                                        appState.updateOpenDialogFlag(false)
                                    }

                                }
                                )
                            }

                        }
                    }
                }
            )

        }
    }
//
//    override fun onBackPressed() {
//        var appState by state { AppState() }
//        if (appState.openDialog) {
//            appState.openDialog = false
//        } else
//            super.onBackPressed()
//    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val appState = remember{AppState(false,backPressHandler = BackPressHandler(false))}
    FilterComponent(appState = appState)
}

