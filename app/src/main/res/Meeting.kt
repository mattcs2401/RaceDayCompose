data class Meeting(
    val Abandoned: Boolean,
    val MeetingCode: String,
    val MeetingId: Int,
    val MeetingType: String,
    val Pools: List<Pool>,
    val Races: List<Race>,
    val TrackChanged: Boolean,
    val TrackCondition: String,
    val TrackConditionLevel: Int,
    val TrackRating: Int,
    val TrackRatingChanged: Boolean,
    val VenueName: String,
    val WeatherChanged: Boolean,
    val WeatherCondition: String,
    val WeatherConditionLevel: Int
)