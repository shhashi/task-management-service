package shhashi.taskmanagement.api.domain.value

/**
 * タスク状態
 */
data class TaskStatus(
    val value: TaskStatusEnum
) {

    enum class TaskStatusEnum {
        // 未着手
        WAITING,

        // 着手中
        WORKING,

        // 完了済み
        COMPLETE,

        // 中止
        PENDING
    }
}
