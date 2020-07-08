package com.tinyapps.presentation.mapper

import com.tinyapps.common_jvm.mapper.Mapper
import com.tinyapps.presentation.features.transactions.model.Transaction

class TagListMapper : Mapper<List<Transaction>, List<String>>() {
    override fun map(input: List<Transaction>): List<String> {
        var result: List<String> = arrayListOf()
        input.forEach {
            val unions = result.union(it.tags)
            result = unions.toList()
        }
        return result
    }
}