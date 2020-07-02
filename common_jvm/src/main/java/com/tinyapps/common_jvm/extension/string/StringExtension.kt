package com.tinyapps.common_jvm.extension.string

/**
 * Created by ChuTien on ${1/25/2017}.
 */

/**
 * Get list tags from json String from Transaction API
 */
fun String.toListTags() : List<String>{
    return this.split(", ")
}