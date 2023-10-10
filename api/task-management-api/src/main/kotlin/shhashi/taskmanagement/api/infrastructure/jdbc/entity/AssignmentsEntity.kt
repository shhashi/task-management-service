package shhashi.taskmanagement.api.infrastructure.jdbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime

/**
 * assignments テーブルのエンティティクラス
 *
 * @property assignmentId アサインID
 * @property projectId プロジェクトID
 * @property accountId アカウントID
 * @property createdAt レコード作成日時
 * @property deletedAt レコード論理削除日時
 */
@Table("assignments")
data class AssignmentsEntity(
    @Id
    val assignmentId: Int? = null,
    val projectId: Int? = null,
    val accountId: Int? = null,
    val createdAt: OffsetDateTime? = null,
    val deletedAt: OffsetDateTime? = null
)
