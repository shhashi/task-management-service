package shhashi.taskmanagement.api.presentation.response

/**
 * /projects エンドポイント。
 * プロジェクト作成の API レスポンス。
 */
data class ProjectCreateResponse(
    val projectID: Int,
    val projectName: String
)
