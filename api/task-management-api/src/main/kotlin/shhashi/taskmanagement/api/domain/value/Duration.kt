package shhashi.taskmanagement.api.domain.value

import shhashi.taskmanagement.api.domain.value.exception.InvalidValueObjectException
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
            throw InvalidValueObjectException(
                message = "INVALID_CONTRADICTING_DATETIME",
                valueObjectName = Duration::class.java.simpleName
            )
        }
    }
}
