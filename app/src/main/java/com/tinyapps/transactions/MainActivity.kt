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
import com.tinyapps.presentation.features.transactions.model.Wallet
import com.tinyapps.presentation.features.transactions.viewmodel.TransactionViewModel
import com.tinyapps.transactions.ui.*
import com.tinyapps.transactions.ui.listener.IFilter
import com.tinyapps.transactions.ui.state.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val backPressHandler =
        BackPressHandler(true)

    private val mTransactionViewModel: TransactionViewModel by viewModel()
    override fun onBackPressed() {
        if (backPressHandler.onBackPress()) {
            super.onBackPressed()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mTransactionViewModel.getTransactions(
            type = getString(R.string.all),
            tags = listOf(),
            maxAmount = 2000.0
        )

        setContent {
            val appState = remember { AppState(backPressHandler = backPressHandler) }
            val tagState = remember { TagState() }
            val typeState = remember { TypeState() }
            val amountState = remember { AmountState() }
            val transactionInputState = remember { TransactionInputState() }

            Scaffold(
                floatingActionButton = {
                    if (!appState.isShowDialog && !appState.isShowTransactionInput) {
                        FloatingActionButton(
                            onClick = {
                                appState.updateTransactionInputFlag(true)
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
                                        Wallet(
                                            "Account X",
                                            93993
                                        ),
                                        Wallet(
                                            "Account Y",
                                            33443
                                        ),
                                        Wallet(
                                            "Account Z",
                                            43993
                                        )
                                    )
                                )
                                FilterComponent(appState)

                                TransactionsComponent(
                                    mTransactionViewModel.transactionsLiveData,
                                    appState = appState
                                )
                            }
                            if (appState.isShowDialog) {
                                FilterOptionComponent(
                                    tagsLiveData = mTransactionViewModel.tagsLiveData,
                                    iFilter = object : IFilter {
                                        override fun fillerResults(
                                            amountState: AmountFilterState,
                                            tagFilterState: TagFilterState,
                                            typeFilterState: TypeFilterState
                                        ) {
                                            appState.updateOpenDialogFlag(false)
                                            tagState.selectedOption = tagFilterState.selectedOption
                                            typeState.selectedOption =
                                                typeFilterState.selectedOption
                                            amountState.value = amountState.value
                                            mTransactionViewModel.getTransactions(
                                                type = typeFilterState.selectedOption,
                                                tags = tagFilterState.selectedOption,
                                                maxAmount = amountState.value
                                                    ?: amountState.max
                                            )
                                        }

                                        override fun fillerCancel() {
                                            Log.d("Tien", "fillerCancel ${appState.isShowDialog}")
                                            appState.updateOpenDialogFlag(false)
                                        }

                                    },
                                    tagState = tagState,
                                    typeState = typeState,
                                    amountState = amountState
                                )
                            }

                            if (appState.isShowTransactionInput) {
                                TransactionInputBox(
                                    supportFragmentManager = supportFragmentManager,
                                    transactionInputState = transactionInputState,
                                    appState = appState
                                )
                            }

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
}

