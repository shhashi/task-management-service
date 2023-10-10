package shhashi.taskmanagement.api.infrastructure.jdbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime

/**
 * accounts テーブルのエンティティクラス
 *
 * @property accountId アカウントID
 * @property accountName アカウント名
 * @property createdAt レコード作成日時
 */
@Table("accounts")
data class AccountsEntity(
    @Id
    val accountId: Int? = null,
    val accountName: String? = null,
    val createdAt: OffsetDateTime? = null
)
