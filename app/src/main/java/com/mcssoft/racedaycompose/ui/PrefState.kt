package com.mcssoft.racedaycompose.ui

data class PrefState(
    var byAuNzPref: Boolean,           // the OnlAuNz preference is enabled/selected.
) {
    companion object {
        /**
         * A StateFlow initialize value.
         */
        fun initialise(): PrefState {
            return PrefState(byAuNzPref = true)
        }
    }

    sealed class Status {
        object Initialise : Status()
        /** TODO - other PrefState statuses ? **/
    }

}