package shhashi.taskmanagement.api.domain

import shhashi.taskmanagement.api.domain.value.ProjectId
import shhashi.taskmanagement.api.domain.value.ProjectName

/**
 * プロジェクト
 *
 * @property projectId プロジェクト ID
 * @property projectName プロジェクト名
 * @property stages ステージ一覧
 * @property isClosed クローズ済みかどうか
 */
data class Project(
    var projectId: ProjectId,
    var projectName: ProjectName?,
    var stages: List<Stage>?,
    var isClosed: Boolean?
)
