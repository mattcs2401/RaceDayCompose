package com.mcssoft.racedaycompose.ui

data class AppState(
    var date: String = "",
    var byDbPref: Boolean = false,                    // the FromDb preference is enabled/selected.
    var byAuNzPref: Boolean = false,                  // the OnlAuNz preference is enabled/selected.
    var isRefreshing: Boolean = false,                // All data being refreshed from the Api.
    var meetingsDownloaded: Boolean = false,          // Meeting/Race info downloaded from the Api.
    var runnersDownloaded: Boolean = false,           // Runner info downloaded from the Api.
    var downloadError: Status = Status.Initialise     // An error occurred in the download.
) {
    companion object {
        /**
         * A StateFlow initializer.
         */
        fun initialise(): AppState {
            return AppState(
                date = "",
                byDbPref = false,
                byAuNzPref = false,
                isRefreshing = false,
                meetingsDownloaded = false,
                runnersDownloaded = false,
                downloadError = Status.Initialise
            )
        }
    }

    sealed class Status {
        object Initialise : Status()
        data class DownloadError(val msg: String = "") : Status()
    }

}