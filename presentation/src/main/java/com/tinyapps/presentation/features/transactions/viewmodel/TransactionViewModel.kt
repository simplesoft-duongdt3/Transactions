package com.tinyapps.presentation.features.transactions.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tinyapps.common_jvm.date.toDate
import com.tinyapps.presentation.mapper.TagListMapper
import com.tinyapps.presentation.mapper.TransactionMapper
import com.tinyapps.domain.features.transactions.usecase.CreateTransactionUseCase
import com.tinyapps.domain.features.transactions.usecase.CreateTransactionUseCaseParams
import com.tinyapps.domain.features.transactions.usecase.GetTransactionsUseCase
import com.tinyapps.domain.features.transactions.usecase.GetTransactionsUseCaseParams
import com.tinyapps.presentation.base.AppDispatchers
import com.tinyapps.presentation.base.BaseViewModel
import com.tinyapps.presentation.features.transactions.model.Transaction
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.abs

class TransactionViewModel(
    val transactionsUseCase: GetTransactionsUseCase,
    val createTransactionUseCase: CreateTransactionUseCase,
    val appDispatchers: AppDispatchers,
    val tagListMapper: TagListMapper,
    val transactionListMapper: TransactionMapper
) : BaseViewModel() {
    val transactionsLiveData: MutableLiveData<List<Transaction>> = MutableLiveData()

    fun createTransaction(transaction: Transaction, result : (Boolean) -> Unit) =
        viewModelScope.launch(appDispatchers.main) {
            val createTransactionResult = withContext(appDispatchers.io) {
                createTransactionUseCase.execute(
                    CreateTransactionUseCaseParams(
                        tags = transaction.tags,
                        date = transaction.date.toDate()?:Date(),
                        amount = transaction.amount,
                        description = transaction.comment,
                        name = transaction.name
                    )
                )
            }

            createTransactionResult.either({
                result(false)
            }, {
                result(true)
            })
        }
    val tagsLiveData: MutableLiveData<List<String>> = MutableLiveData()


    fun getTransactions(type: String, tags: List<String>, maxAmount: Double) =
        viewModelScope.launch(appDispatchers.main) {
            val transactionResults = withContext(appDispatchers.io) {
                transactionsUseCase.execute(
                    GetTransactionsUseCaseParams(
                        tags = tags,
                        type = type,
                        limitAmount = maxAmount
                    )
                )
            }
            transactionResults.either({
                transactionsLiveData.value = listOf()
                tagsLiveData.value = listOf()
                Log.d("Tien", "transactionResults Failure ${it}")
            }) { result ->
                val transactions = transactionListMapper.mapList(result.transactions)
                tagsLiveData.value = tagListMapper.map(transactions)
                Log.d("Tien", "transactionResults Success ${transactions}")
                val resultFilterByType =
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
                // filter by tag
                var resultFilterByTag: MutableList<Transaction> = mutableListOf()
                if (tags.isNotEmpty()) {
                    tags.forEach {
                        resultFilterByTag.addAll(transactions.filter { transaction ->
                            transaction.tags.contains(
                                it
                            )
                        })
                    }
                } else {
                    resultFilterByTag = resultFilterByType.toMutableList()
                }
                transactionsLiveData.value = resultFilterByTag
            }
        }

}