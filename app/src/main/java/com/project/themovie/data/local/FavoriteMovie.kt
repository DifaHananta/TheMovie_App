package com.project.themovie.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_movie")
@Parcelize
data class FavoriteMovie(
    @PrimaryKey
    val id: Int,
    val poster: String
) : Parcelable
