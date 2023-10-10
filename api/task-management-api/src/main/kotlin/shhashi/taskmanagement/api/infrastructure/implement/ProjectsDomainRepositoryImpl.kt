package shhashi.taskmanagement.api.infrastructure.implement

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import shhashi.taskmanagement.api.domain.Project
import shhashi.taskmanagement.api.domain.repository.ProjectsDomainRepository
import shhashi.taskmanagement.api.domain.value.AccountId
import shhashi.taskmanagement.api.domain.value.ProjectId
import shhashi.taskmanagement.api.domain.value.ProjectName
import shhashi.taskmanagement.api.infrastructure.exception.NotExistsValueObjectsException
import shhashi.taskmanagement.api.infrastructure.jdbc.AssignmentsRepository
import shhashi.taskmanagement.api.infrastructure.jdbc.ProjectsRepository
import shhashi.taskmanagement.api.infrastructure.jdbc.entity.AssignmentsEntity
import shhashi.taskmanagement.api.infrastructure.jdbc.entity.ProjectsEntity
import java.time.OffsetDateTime

/**
 * [ProjectsDomainRepository] の実装クラス。
 */
@Component
class ProjectsDomainRepositoryImpl(
    private val projectsRepository: ProjectsRepository,
    private val assignmentsRepository: AssignmentsRepository
) : ProjectsDomainRepository {

    @Transactional
    override fun createProject(project: Project): Project {
        // プロジェクトの保存
        val projectEntity = ProjectsEntity(
            projectName = project.projectName?.value
            // projectName オブジェクトが存在しない場合はエラー
                ?: throw NotExistsValueObjectsException(
                    message = "not exists value object",
                    valueObjectName = ProjectName::class.java.simpleName
                ),
            createdAt = OffsetDateTime.now()
        )
        val savedProjectEntity = projectsRepository.save(projectEntity)

        // アサインメンバオブジェクトのバリデーション
        if (project.assignedMembers.isNullOrEmpty()) {
            throw NotExistsValueObjectsException(
                message = "no assignment members",
                valueObjectName = AccountId::class.java.simpleName
            )
        }

        // アサインメンバの保存
        val assignmentsEntities = project.assignedMembers!!.map {
            // AssignmentsEntity への移し替え
            AssignmentsEntity(
                projectId = savedProjectEntity.projectId,
                accountId = it.accountId.value,
                createdAt = OffsetDateTime.now()
            )
        }
        assignmentsRepository.saveAll(assignmentsEntities)

        // 作成済みのエンティティをプロジェクトドメインクラスに移し替え
        return Project(
            projectId = ProjectId(savedProjectEntity.projectId!!),
            projectName = ProjectName.create(savedProjectEntity.projectName!!),
            isClosed = savedProjectEntity.closedAt != null
        )
    }
}
