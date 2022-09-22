package com.vall.mynoteapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "desctiption")
    var description: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null
) : Parcelable