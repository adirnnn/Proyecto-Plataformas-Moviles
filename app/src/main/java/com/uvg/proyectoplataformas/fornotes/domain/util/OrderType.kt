package com.uvg.proyectoplataformas.fornotes.domain.util

sealed class OrderType {
    object Ascending : OrderType()
    object Descending : OrderType()
}