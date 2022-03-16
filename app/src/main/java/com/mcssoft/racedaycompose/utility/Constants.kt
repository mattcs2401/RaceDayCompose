package com.mcssoft.racedaycompose.utility

object Constants {

    // The "key" for the parameter passed from the MeetingsScreen to the RacesScreen.
    const val PARAM_MEETING_ID: String = "meetingId"

    const val PARAM_PREF_ID: String = "prefChange"

    // The Api end point, without date (and meeting code) qualification.
    const val BASE_URL: String = "https://api.tatts.com/sales/vmax/web/data/racing/"

    // Base meeting type. Only Meetings of this type will be displayed (ATT).
    const val MEETING_TYPE: String = "R"

    // Base set of meeting codes. Only a Meeting with one of these codes will be displayed (ATT).
    val MEETING_CODES = listOf(
        "AR","BR","CR","ER","IR","MR","NR","OR","PR","QR","SR","TR","VR","WR","OS","ZS"
    )
}