package shhashi.taskmanagement.api.domain.repository

import shhashi.taskmanagement.api.domain.Project

/**
 * [Project] ドメインクラスのための Repository インターフェースクラス
 */
interface ProjectsDomainRepository {

    /**
     * [Project] クラスを保存する。
     *
     * @param project プロジェクト。
     * この [project] オブジェクトに含まれたアサインメンバーもすべて保存される。
     *
     * @return 保存されたプロジェクト。
     */
    fun createProject(project: Project): Project
}
