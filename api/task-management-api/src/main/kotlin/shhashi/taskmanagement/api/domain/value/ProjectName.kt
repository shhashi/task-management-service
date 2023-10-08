package shhashi.taskmanagement.api.domain.value

import io.konform.validation.Validation
import io.konform.validation.ValidationError
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.pattern
import shhashi.taskmanagement.api.domain.value.exception.InvalidValueObjectException

/**
 * プロジェクト名
 *
 * 制約については [ProjectNameImpl.validation] を参照。
 */
interface ProjectName {

    val value: String

    companion object {
        fun create(projectName: String): ProjectName {
            val trimmedProjectName = projectName.trim()
            return ProjectNameImpl(trimmedProjectName)
        }
    }
}

/**
 * [ProjectName] の実装クラス
 */
private data class ProjectNameImpl(override val value: String) : ProjectName {

    /**
     * 以下の制約に違反していないかを検証するバリデーション。
     * 1. 文字列は(空白を除く)最低 1 文字以上で構成されていること。
     * 2. 文字列は 100 文字以下であること。
     * 3. 以下に指定された記号が含まれていないこと。
     *     - !"#$%&'()*+-.,/:;<=>?@[\]^_[`]{|}~
     */
    private val validation = Validation {
        ProjectName::value {
            // 1. 文字列は最低 1 文字以上で構成されていること。
            minLength(1) hint "INVALID_MIN_LENGTH"

            // 2. 文字列は 100 文字以下であること。
            maxLength(100) hint "INVALID_MAX_LENGTH"

            // 3. 以下に指定された記号が含まれていないこと。
            //     - !"#$%&'()*+-.,/:;<=>?@[\]^_[`]{|}~
            pattern("[^!\"#\$%&'()*+\\-.,/:;<=>?@\\[\\\\\\]^_`{|}~]*".toRegex()) hint "INVALID_CHARACTER"
        }
    }

    init {
        // バリデーションエラーを取得
        val validationErrors = validation.validate(this).errors
        // TODO 複数の異常が存在していても、最初のバリデーションエラーのみを Exception で返すがこれで正しいか要検討
        if (validationErrors.size > 0) convertToException(validationErrors.first())
    }

    private fun convertToException(validationError: ValidationError) {
        when (validationError.message) {
            "INVALID_MIN_LENGTH" -> throw InvalidValueObjectException(
                message = "INVALID_MIN_LENGTH",
                valueObjectName = ProjectName::class.java.simpleName
            )

            "INVALID_MAX_LENGTH" -> throw InvalidValueObjectException(
                message = "INVALID_MAX_LENGTH",
                valueObjectName = ProjectName::class.java.simpleName
            )

            "INVALID_CHARACTER" -> throw InvalidValueObjectException(
                message = "INVALID_CHARACTER",
                valueObjectName = ProjectName::class.java.simpleName
            )
        }
    }
}

