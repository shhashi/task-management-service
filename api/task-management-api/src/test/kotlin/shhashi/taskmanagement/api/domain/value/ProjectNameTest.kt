package shhashi.taskmanagement.api.domain.value

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import shhashi.taskmanagement.api.domain.value.exception.InvalidValueObjectException

class ProjectNameTest {

    @Test
    fun `check success creation`() {
        // 入力可能な文字列
        val input = " 1 あAＡaａアｱ亜 "
        val projectName = ProjectName.create(input)

        assertEquals("1 あAＡaａアｱ亜", projectName.value)
    }

    @Test
    fun `check invalid characters validation`() {
        // 入力文字列と想定されるエラーメッセージのデータセット
        val testProjectNames = mapOf(
            // 空文字 -> エラー
            "INVALID_MIN_LENGTH" to listOf(""),
            // 101 文字 -> エラー
            "INVALID_MAX_LENGTH" to listOf(
                "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstu" +
                        "vwxyzabcdefghijklmnopqrstuvw"
            ),
            // 記号
            "INVALID_CHARACTER" to listOf(
                "!", "\"", "#", "\$", "%", "&", "\'", "(", ")", "*", "+", "-", ".", ",", "/",
                ":", ";", "<", "=", ">", "?", "@", "[", "\\", "]", "^", "_", "`", "{", "|", "}", "~"
            )
        )

        testProjectNames.entries.forEach { (message, inputs) ->
            inputs.forEach { input ->
                val catchException = assertThrows<InvalidValueObjectException> {
                    ProjectName.create(input)
                }
                assertEquals(message, catchException.message)
                assertEquals("ProjectName", catchException.valueObjectName)
            }
        }
    }
}
