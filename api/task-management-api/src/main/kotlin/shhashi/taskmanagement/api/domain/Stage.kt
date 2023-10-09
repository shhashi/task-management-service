package shhashi.taskmanagement.api.domain

import shhashi.taskmanagement.api.domain.value.Duration
import shhashi.taskmanagement.api.domain.value.StageId
import shhashi.taskmanagement.api.domain.value.StageName
import shhashi.taskmanagement.api.domain.value.StageStatus

/**
 * ステージ
 *
 * @property stageId ステージ ID
 * @property stageName ステージ名
 * @property duration 期間
 * @property status ステージ状態
 */
data class Stage(
    var stageId: StageId,
    var stageName: StageName? = null,
    var duration: Duration? = null,
    var status: StageStatus? = null
)
