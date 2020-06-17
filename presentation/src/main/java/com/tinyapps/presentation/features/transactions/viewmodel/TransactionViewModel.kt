package com.tinyapps.presentation.features.transactions.viewmodel

import com.tinyapps.domain.features.transactions.usecase.GetTransactionsUseCase
import com.tinyapps.presentation.base.AppDispatchers
import com.tinyapps.presentation.base.BaseViewModel

class TransactionViewModel(val transactionsUseCase: GetTransactionsUseCase, val appDispatchers: AppDispatchers) : BaseViewModel()