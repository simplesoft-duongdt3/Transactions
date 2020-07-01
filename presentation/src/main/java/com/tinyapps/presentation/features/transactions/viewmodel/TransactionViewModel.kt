package com.tinyapps.presentation.features.transactions.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tinyapps.data.features.transactions.mapper.TransactionMapper
import com.tinyapps.domain.base.usecase.UseCaseParams
import com.tinyapps.domain.features.transactions.usecase.GetTransactionsUseCase
import com.tinyapps.domain.features.transactions.usecase.GetTransactionsUseCaseParams
import com.tinyapps.presentation.base.AppDispatchers
import com.tinyapps.presentation.base.BaseViewModel
import com.tinyapps.presentation.features.transactions.model.Transaction
import kotlinx.coroutines.launch
import kotlin.math.abs

class TransactionViewModel(
    val transactionsUseCase: GetTransactionsUseCase,
    val appDispatchers: AppDispatchers,
    val transactionListMapper: TransactionMapper
) : BaseViewModel() {
    val transactionsLiveData: MutableLiveData<List<Transaction>> = MutableLiveData()

    fun getTransactions(type: String, tags: List<String>, maxAmount: Double) =
        viewModelScope.launch(appDispatchers.main) {
            val transactionResults = transactionsUseCase.execute(
                GetTransactionsUseCaseParams(
                    tags = tags,
                    type = type,
                    limitAmount = maxAmount
                )
            )
            transactionResults.either({
                transactionsLiveData.value = listOf()
                Log.d("Tien", "transactionResults Failure ${it}")
            }, { result ->
                val transactions = transactionListMapper.mapList(result.transactions)
                Log.d("Tien", "transactionResults Success ${transactions}")
//                val transactions = listOf(
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction(),
//                    Transaction()
//                )
                val listTransactions =
                    when (type) {
                        "Expenses" -> {
                            transactions.filter { transaction ->
                                (transaction.amount < 0 && abs(
                                    transaction.amount
                                ) < maxAmount)
                            }
                        }
                        "Revenue" -> {
                            transactions.filter { transaction ->
                                (transaction.amount >= 0 && abs(
                                    transaction.amount
                                ) < maxAmount)
                            }
                        }
                        else -> {
                            transactions.filter { transaction -> abs(transaction.amount) < maxAmount }
                        }
                    }
                transactionsLiveData.value = listTransactions
            })
        }

}