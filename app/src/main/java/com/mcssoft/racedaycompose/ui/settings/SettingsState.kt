package com.mcssoft.racedaycompose.ui.settings

data class FromDbState(
    var loading: Boolean = false,
    var exception: Exception?,
    var currValue: Boolean = false
) {
    companion object {
        // Simply a starting state.
        fun initialise(): FromDbState {
            return FromDbState(
                loading = false,
                exception = null,
                currValue = false
            )
        }
        fun loading(): FromDbState {
            return FromDbState(
                loading = true,
                exception = null,
                currValue = false
            )
        }
        fun failure(exception: Exception): FromDbState {
            return FromDbState(
                loading = false,
                exception = exception,
                currValue = false
            )
        }
        // Couldn't seem to get this to work in a Success case.
//        fun success(value: Boolean): FromDbState {
//            return FromDbState(
//                loading = false,
//                exception = null,
//                currValue = value
//            )
//        }
    }
}

data class OnlyAuNzState(
    var loading: Boolean = false,
    var exception: Exception?,
    var currValue: Boolean = false
) {

    companion object {
        // Simply a starting state.
        fun initialise(): OnlyAuNzState {
            return OnlyAuNzState(
                loading = false,
                exception = null,
                currValue = false
            )
        }
        fun loading(): OnlyAuNzState {
            return OnlyAuNzState(
                loading = true,
                exception = null,
                currValue = false
            )
        }
        fun failure(exception: Exception): OnlyAuNzState {
            return OnlyAuNzState(
                loading = false,
                exception = exception,
                currValue = false
            )
        }
        // Couldn't seem to get this to work in a Success case.
//        fun success(value: Boolean): OnlyAuNzState {
//            return OnlyAuNzState(
//                loading = false,
//                exception = null,
//                currValue = value
//            )
//        }
    }
}