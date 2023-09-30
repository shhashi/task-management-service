package shhashi.taskmanagement.api.domain.value

import shhashi.taskmanagement.api.domain.value.exception.InvalidValueException
import java.time.OffsetDateTime

/**
 * 期間
 */
data class Duration(
    val startDateTime: OffsetDateTime,
    val endDateTime: OffsetDateTime?
) {

    init {
        if (endDateTime != null && !startDateTime.isBefore(endDateTime)) {
            throw InvalidValueException("`endDateTime` must come after `startDateTime`.")
        }
    }
}
