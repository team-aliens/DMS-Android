package team.aliens.dms.android.domain._legacy.model._common


/**
 * An enum class expresses days of week
 * copied from Java 1.8 DayOfWeek API.
 */
enum class DayOfWeek {
    /**
     * The singleton instance for the day-of-week of Monday.
     * This has the numeric value of `1`.
     */
    MONDAY,

    /**
     * The singleton instance for the day-of-week of Tuesday.
     * This has the numeric value of `2`.
     */
    TUESDAY,

    /**
     * The singleton instance for the day-of-week of Wednesday.
     * This has the numeric value of `3`.
     */
    WEDNESDAY,

    /**
     * The singleton instance for the day-of-week of Thursday.
     * This has the numeric value of `4`.
     */
    THURSDAY,

    /**
     * The singleton instance for the day-of-week of Friday.
     * This has the numeric value of `5`.
     */
    FRIDAY,

    /**
     * The singleton instance for the day-of-week of Saturday.
     * This has the numeric value of `6`.
     */
    SATURDAY,

    /**
     * The singleton instance for the day-of-week of Sunday.
     * This has the numeric value of `7`.
     */
    SUNDAY;

    //-----------------------------------------------------------------------
    val value: Int
        /**
         * Gets the day-of-week `int` value.
         *
         *
         * The values are numbered following the ISO-8601 standard, from 1 (Monday) to 7 (Sunday).
         * See [java.time.temporal.WeekFields.dayOfWeek] for localized week-numbering.
         *
         * @return the day-of-week, from 1 (Monday) to 7 (Sunday)
         */
        get() = ordinal + 1
}
