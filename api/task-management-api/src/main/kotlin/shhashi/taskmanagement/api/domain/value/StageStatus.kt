package shhashi.taskmanagement.api.domain.value

/**
 * ステージ状態
 */
data class StageStatus(
    val value: StageStatusEnum
) {

    enum class StageStatusEnum {
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
