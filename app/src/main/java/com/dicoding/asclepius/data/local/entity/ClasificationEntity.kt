package com.dicoding.asclepius.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clasification_entity")
data class ClasificationEntity(
    @PrimaryKey
    @ColumnInfo(name = "clasification_id")
    val clasificationId: Int,

    @ColumnInfo(name = "imageUriClassification")
    val imageUriClassification: String,

    @ColumnInfo(name = "clasificationResult")
    val clasificationResult: String
)