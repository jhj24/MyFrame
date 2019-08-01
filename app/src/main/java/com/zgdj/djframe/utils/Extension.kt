package com.zgdj.djframe.utils

import android.content.Context

fun String.getFileName(): String {
    val index = this.lastIndexOf("/")
    if (index != -1) {
        return this.substring(index + 1, this.length)
    }
    return this
}

fun <T> List<T>?.toArrayList(): ArrayList<T> {
    return ArrayList(this.orEmpty())
}

fun Context.toast(msg: String) {
    ToastUtils.showShort(msg)
}

fun Context.download(fileName: String, path: String) {
    DownloadUtils.download(this, fileName, path)
}

fun Context.delete(path: String, msg: String, vararg params: Pair<String, String>, body: () -> Unit) {
    DownloadUtils.delete(this, path, msg, *params, body = body)
}