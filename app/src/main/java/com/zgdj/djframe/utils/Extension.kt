package com.zgdj.djframe.utils

fun String.getFileName(): String {
    val index = this.lastIndexOf("/")
    if (index != -1) {
        return this.substring(index + 1, this.length)
    }
    return this
}