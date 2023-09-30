package shhashi.taskmanagement.api.domain.value.stage

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import shhashi.taskmanagement.api.domain.value.Duration
import shhashi.taskmanagement.api.domain.value.exception.InvalidValueException
import java.time.OffsetDateTime

class DurationTest {

    @Test
    fun `check invalid dateTime`() {
        val startDateTime = OffsetDateTime.parse("2020-01-01T12:00:00+09:00")
        val endDateTime = OffsetDateTime.parse("2020-01-01T11:00:00+09:00")
        assertThrows<InvalidValueException> {
            Duration(
                startDateTime = startDateTime,
                endDateTime = endDateTime
            )
        }
    }

    @Test
    fun `check invalid same dateTime`() {
        val startDateTime = OffsetDateTime.parse("2020-01-01T12:00:00+09:00")
        val endDateTime = OffsetDateTime.parse("2020-01-01T12:00:00+09:00")
        assertThrows<InvalidValueException> {
            Duration(
                startDateTime = startDateTime,
                endDateTime = endDateTime
            )
        }
    }
}
