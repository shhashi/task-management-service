package shhashi.taskmanagement.api.application

import org.springframework.stereotype.Component
import shhashi.taskmanagement.api.application.dto.ProjectCreateDto
import shhashi.taskmanagement.api.application.dto.ProjectCreateResultDto
import shhashi.taskmanagement.api.domain.Member
import shhashi.taskmanagement.api.domain.Project
import shhashi.taskmanagement.api.domain.repository.ProjectsDomainRepository
import shhashi.taskmanagement.api.domain.value.AccountId
import shhashi.taskmanagement.api.domain.value.ProjectId
import shhashi.taskmanagement.api.domain.value.ProjectName
import shhashi.taskmanagement.api.infrastructure.exception.NotExistsValueObjectsException

/**
 * プロジェクトのためのユースケースクラス
 */
@Component
class ProjectsUsecase(
    private val projectsDomainRepository: ProjectsDomainRepository
) {

    /**
     * プロジェクトを新規に作成する。
     */
    fun createProject(projectCreateDto: ProjectCreateDto): ProjectCreateResultDto {
        // 新規プロジェクトの作成
        val creator = Member(accountId = AccountId(projectCreateDto.createdBy))
        val newProject = Project(
            projectId = ProjectId(-1), // 仮発行 ID
            projectName = ProjectName.create(projectCreateDto.projectName),
            assignedMembers = listOf(creator)
        )

        // プロジェクトの永続化
        val createdProjects = projectsDomainRepository.createProject(newProject)

        return ProjectCreateResultDto(
            projectId = createdProjects.projectId.value,
            projectName = createdProjects.projectName?.value
            // プロジェクト名が null の場合はエラー
                ?: throw NotExistsValueObjectsException(
                    message = "not exists value object",
                    valueObjectName = ProjectName::class.java.simpleName
                )
        )
    }
}
