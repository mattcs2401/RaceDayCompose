package com.mcssoft.racedaycompose.domain.dto

data class PoolDto(
    val Abandoned: Boolean,
    val Available: Boolean,
    val Dividends: List<Any>,
    val JackpotIn: Double,
    val JackpotOut: Double,
    val Legs: List<LegDto>,
    val MinPoolSize: Double,
    val MinPoolTopUp: Double,
    val PoolTotal: Double,
    val PoolType: String,
    val Status: String
)