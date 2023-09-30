package shhashi.taskmanagement.api.domain

import shhashi.taskmanagement.api.domain.value.AccountId
import shhashi.taskmanagement.api.domain.value.AccountName
import shhashi.taskmanagement.api.domain.value.LoginId

/**
 * アカウント
 *
 * @property accountId アカウント ID
 * @property loginId ログイン ID
 * @property accountName アカウント名
 */
data class Account (
    var accountId: AccountId,
    var loginId: LoginId,
    var accountName: AccountName
)
