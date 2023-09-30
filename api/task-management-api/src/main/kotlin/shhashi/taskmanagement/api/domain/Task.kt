package shhashi.taskmanagement.api.domain

import shhashi.taskmanagement.api.domain.value.*

/**
 * タスク
 *
 * @property taskId タスク ID
 * @property taskName タスク名
 * @property assignedAccountId アサインされたアカウント ID
 * @property description 説明
 * @property taskStatus タスク状態
 * @property duration 期間
 */
data class Task(
    var taskId: TaskId,
    var taskName: TaskName?,
    var assignedAccountId: AccountId?,
    var description: Description?,
    var taskStatus: TaskStatus?,
    var duration: Duration?
)
