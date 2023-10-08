package shhashi.taskmanagement.api.domain.value.exception

/**
 * バリューオブジェクト生成時のバリデーションエラー
 *
 * @property message エラーメッセージ
 * @property valueObjectName バリデーションエラーが発生した値オブジェクト
 */
class InvalidValueObjectException(
    message: String,
    val valueObjectName: String
) : RuntimeException(message)
