package com.mcssoft.racedaycompose.utility

import java.util.*

class DateUtils {

    /**
     * Get today's date in format "YYYY/MM/DD".
     * @return The formatted string.
     */
    fun getDateToday(): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        val year = calendar.get(Calendar.YEAR).toString()
        val month = ((calendar.get(Calendar.MONTH)) + 1).toString()  // Note (1) below.
        val day = calendar.get(Calendar.DAY_OF_MONTH).toString()

        return  "$year/$month/$day/"
    }

}
/*
Notes:
(1) - https://developer.android.com/reference/java/util/Calendar:
      The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0.
 */

