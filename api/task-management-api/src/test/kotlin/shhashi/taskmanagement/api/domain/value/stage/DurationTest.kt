package shhashi.taskmanagement.api.domain.value.stage

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import shhashi.taskmanagement.api.domain.value.Duration
import shhashi.taskmanagement.api.domain.value.exception.InvalidValueObjectException
import java.time.OffsetDateTime

class DurationTest {

    @Test
    fun `check invalid dateTime`() {
        val startDateTime = OffsetDateTime.parse("2020-01-01T12:00:00+09:00")
        val endDateTime = OffsetDateTime.parse("2020-01-01T11:00:00+09:00")
        val catchException = assertThrows<InvalidValueObjectException> {
            Duration(
                startDateTime = startDateTime,
                endDateTime = endDateTime
            )
        }
        assertEquals("INVALID_CONTRADICTING_DATETIME", catchException.message)
        assertEquals("Duration", catchException.valueObjectName)
    }

    @Test
    fun `check invalid same dateTime`() {
        val startDateTime = OffsetDateTime.parse("2020-01-01T12:00:00+09:00")
        val endDateTime = OffsetDateTime.parse("2020-01-01T12:00:00+09:00")
        val catchException = assertThrows<InvalidValueObjectException> {
            Duration(
                startDateTime = startDateTime,
                endDateTime = endDateTime
            )
        }
        assertEquals("INVALID_CONTRADICTING_DATETIME", catchException.message)
        assertEquals("Duration", catchException.valueObjectName)
    }
}
