package com.tinyapps.presentation.features.transactions.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tinyapps.data.features.transactions.mapper.TransactionMapper
import com.tinyapps.domain.base.usecase.UseCaseParams
import com.tinyapps.domain.features.transactions.usecase.GetTransactionsUseCase
import com.tinyapps.presentation.base.AppDispatchers
import com.tinyapps.presentation.base.BaseViewModel
import com.tinyapps.presentation.features.transactions.model.Transaction
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TransactionViewModel(
    val transactionsUseCase: GetTransactionsUseCase,
    val appDispatchers: AppDispatchers,
    val transactionListMapper: TransactionMapper
) : BaseViewModel() {
    val transactionsLiveData: MutableLiveData<List<Transaction>> = MutableLiveData()

    fun getTransactions() =
        viewModelScope.launch(appDispatchers.main) {
            val transactionResults = transactionsUseCase.execute(UseCaseParams.Empty)
            transactionResults.either({
                transactionsLiveData.value = listOf()
                Log.d("Tien","transactionResults Failure ${transactionsLiveData.value}")
            }, { result ->
//                transactionsLiveData.value = transactionListMapper.mapList(result.transactions)
                transactionsLiveData.value = listOf(
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
                )
                Log.d("Tien","transactionResults result ${transactionsLiveData.value}")
            })
        }

}