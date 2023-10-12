package shhashi.taskmanagement.api.application.dto

/**
 * プロジェクト作成結果のための DTO クラス。
 *
 * @property projectId プロジェクト ID。
 * @property projectName プロジェクト名。
 */
data class ProjectCreateResultDto(
    val projectId: Int,
    val projectName: String
)
