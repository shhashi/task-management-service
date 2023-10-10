package shhashi.taskmanagement.api.infrastructure.exception

/**
 * 値オブジェクトが存在しない場合の Exception クラス
 *
 * @property message エラーメッセージ
 * @property valueObjectName バリデーションエラーが発生した値オブジェクト
 */
class NotExistsValueObjectsException(
    message: String,
    val valueObjectName: String
) : RuntimeException(message)
