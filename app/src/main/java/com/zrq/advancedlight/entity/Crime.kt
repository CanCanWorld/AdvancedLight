package com.zrq.advancedlight.entity

import java.io.File
import java.util.Date
import java.util.UUID

/**
 * @Description:
 * @author zhangruiqian
 * @date 2023/5/18 15:27
 */
data class Crime(
    val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var suspect: String = ""
) {
    val photoFileName
        get() = "IMG_$id.jpg"

    fun getPhotoFile(filesDir: File) = File(filesDir, photoFileName)
}