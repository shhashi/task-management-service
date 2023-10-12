package shhashi.taskmanagement.api.presentation

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import shhashi.taskmanagement.api.application.ProjectsUsecase
import shhashi.taskmanagement.api.application.dto.ProjectCreateDto
import shhashi.taskmanagement.api.presentation.response.ProjectCreateResponse

/**
 * プロジェクト情報を操作するためのコントローラクラス。
 */
@RestController
class ProjectsController(
    private val projectsUsecase: ProjectsUsecase
) {

    /**
     * 新しくプロジェクトを作成する。
     *
     * @param projectName プロジェクト名
     * @param createdBy プロジェクト作成者
     */
    @PostMapping("/projects")
    fun createNewProject(
        @RequestParam("projectName") projectName: String,
        @RequestParam("createdBy") createdBy: Int,
    ): ProjectCreateResponse {
        val projectCreateDto = ProjectCreateDto(projectName = projectName, createdBy = createdBy)
        val projectCreateResultDto = projectsUsecase.createProject(projectCreateDto)
        return ProjectCreateResponse(
            projectID = projectCreateResultDto.projectId,
            projectName = projectCreateResultDto.projectName
        )
    }
}
