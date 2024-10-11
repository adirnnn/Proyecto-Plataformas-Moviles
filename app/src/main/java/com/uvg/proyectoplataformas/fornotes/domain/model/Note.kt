package com.uvg.proyectoplataformas.fornotes.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.uvg.proyectoplataformas.ui.theme.BabyBlue
import com.uvg.proyectoplataformas.ui.theme.LightGreen
import com.uvg.proyectoplataformas.ui.theme.RedOrange
import com.uvg.proyectoplataformas.ui.theme.RedPink
import com.uvg.proyectoplataformas.ui.theme.Violet

@Entity(tableName = Note.NOTE_TABLE)
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val color: Int,
    val timeStamp: Long
) {
    companion object {
        val noteColors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
        const val NOTE_TABLE = "note"
    }
}