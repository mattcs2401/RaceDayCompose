package com.mcssoft.racedaycompose.domain.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Admin",
    indices = [Index(value = ["_id"])]
)
data class Admin(
    @PrimaryKey(autoGenerate = true)
    var _id: Long = 0L,
) {
    //TBA.
}