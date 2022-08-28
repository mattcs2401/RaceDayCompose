package com.mcssoft.racedaycompose.ui.summary

import com.mcssoft.racedaycompose.domain.model.Summary

data class SummaryState(
    val exception: Exception?,
    val status: Status,
    val loading: Boolean = false,
    val summaries: List<Summary> = emptyList(),
    val group: Map<String, List<Summary>> = mapOf()
) {
    companion object {
        fun initialise(): SummaryState {
            return SummaryState(
                exception = null,
                status = Status.Initialise
            )
        }
    }

    sealed class Status {
        object Initialise: Status()
        object Loading : Status()
        object Success : Status()
        object Failure : Status()
    }

    val count = summaries.size
    val grouped by lazy { summaries.groupBy { it.meetingCode } }     // TBA ?
}