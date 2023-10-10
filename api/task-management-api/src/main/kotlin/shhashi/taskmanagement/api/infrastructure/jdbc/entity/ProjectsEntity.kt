package shhashi.taskmanagement.api.infrastructure.jdbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.OffsetDateTime

/**
 * projects テーブルのエンティティクラス
 *
 * @property projectId プロジェクトID
 * @property projectName プロジェクト名
 * @property createdAt 作成日
 * @property closedAt クローズ日
 */
@Table("projects")
data class ProjectsEntity(
    @Id
    val projectId: Int? = null,
    val projectName: String? = null,
    val createdAt: OffsetDateTime? = null,
    val closedAt: LocalDate? = null
)
