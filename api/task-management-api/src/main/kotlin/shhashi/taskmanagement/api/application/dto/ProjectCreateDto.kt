package shhashi.taskmanagement.api.application.dto

/**
 * プロジェクト作成のための DTO クラス。
 *
 * @property projectName プロジェクト名。
 * @property createdBy プロジェクト作成者のアカウント ID
 */
data class ProjectCreateDto(
    val projectName: String,
    val createdBy: Int
)
