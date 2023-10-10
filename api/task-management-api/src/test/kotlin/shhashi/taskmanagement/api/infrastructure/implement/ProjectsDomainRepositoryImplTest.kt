package shhashi.taskmanagement.api.infrastructure.implement

import com.github.dockerjava.api.model.ExposedPort
import com.github.dockerjava.api.model.HostConfig
import com.github.dockerjava.api.model.PortBinding
import com.github.dockerjava.api.model.Ports
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import shhashi.taskmanagement.api.domain.Member
import shhashi.taskmanagement.api.domain.Project
import shhashi.taskmanagement.api.domain.value.AccountId
import shhashi.taskmanagement.api.domain.value.ProjectId
import shhashi.taskmanagement.api.domain.value.ProjectName
import shhashi.taskmanagement.api.infrastructure.jdbc.AccountsRepository
import shhashi.taskmanagement.api.infrastructure.jdbc.AssignmentsRepository
import shhashi.taskmanagement.api.infrastructure.jdbc.ProjectsRepository
import shhashi.taskmanagement.api.infrastructure.jdbc.entity.AccountsEntity
import java.time.OffsetDateTime

/**
 * [ProjectsDomainRepositoryImpl] の単体テストクラス
 */
@SpringBootTest
@TestPropertySource(
    properties = [
        "spring.datasource.url=jdbc:postgresql://127.0.0.1:5432/task-management-db",
        "spring.datasource.username=testuser",
        "spring.datasource.password=testpassword",
        "spring.datasource.driver-class-name=org.postgresql.Driver"
    ]
)
@Testcontainers
class ProjectsDomainRepositoryImplTest @Autowired constructor(
    private val projectsDomainRepositoryImpl: ProjectsDomainRepositoryImpl,
    private val accountsRepository: AccountsRepository,
    private val projectsRepository: ProjectsRepository,
    private val assignmentsRepository: AssignmentsRepository
) {

    companion object {
        @Container
        val postgreSqlContainer = PostgreSQLContainer<Nothing>("postgres:15.4").apply {
            withDatabaseName("task-management-db")
            withUsername("testuser")
            withPassword("testpassword")
            withEnv("TZ", "Asia/Tokyo")
            withInitScript("task-management-db/ddl.sql")
            withExposedPorts(5432)
            withCreateContainerCmdModifier {
                val hostConfig = HostConfig().apply {
                    val portBinding = PortBinding(Ports.Binding.bindPort(5432), ExposedPort(5432))
                    withPortBindings(portBinding)
                }
                it.withHostConfig(hostConfig)
            }
        }
    }

    @Test
    fun `check ProjectsDomainRepositoryImpl#createProject for creating new project`() {
        // テストデータの作成
        val accountsEntity = AccountsEntity(
            accountName = "test-account",
            createdAt = OffsetDateTime.now()
        )
        // - アカウントデータの挿入
        val insertedAccount = accountsRepository.save(accountsEntity)

        // 入力データ
        // - プロジェクト作成者
        val account = Member(
            accountId = AccountId(insertedAccount.accountId!!)
        )
        // - 新規作成対象のプロジェクト
        val input = Project(
            projectId = ProjectId(0), // <- 仮識別ID
            projectName = ProjectName.create("テストプロジェクト"),
            assignedMembers = listOf(account)
        )

        // 実行時間
        val executedTime = OffsetDateTime.now()

        // [ProjectsDomainRepositoryImpl.createProject] の実行
        val actualCreatedProject = projectsDomainRepositoryImpl.createProject(input)

        // 検証
        // - [ProjectsDomainRepositoryImpl.createProject] のレスポンスの検証
        assertEquals(1, actualCreatedProject.projectId.value)
        assertNotNull(actualCreatedProject.projectName)
        assertEquals("テストプロジェクト", actualCreatedProject.projectName!!.value)
        assertNotNull(actualCreatedProject.isClosed)
        assertFalse(actualCreatedProject.isClosed!!)

        // - projects
        val fetchedProjectsEntities = projectsRepository.findAll()
        assertEquals(1, fetchedProjectsEntities.count())
        assertEquals(1, fetchedProjectsEntities.first().projectId)
        assertEquals("テストプロジェクト", fetchedProjectsEntities.first().projectName)
        assertTrue(executedTime.isBefore(fetchedProjectsEntities.first().createdAt))
        assertNull(fetchedProjectsEntities.first().closedAt)

        // - assignments
        val fetchedAssignmentsEntities = assignmentsRepository.findAll()
        assertEquals(1, fetchedProjectsEntities.count())
        assertEquals(1, fetchedAssignmentsEntities.first().assignmentId)
        assertEquals(1, fetchedAssignmentsEntities.first().projectId)
        assertEquals(1, fetchedAssignmentsEntities.first().accountId)
        assertTrue(executedTime.isBefore(fetchedAssignmentsEntities.first().createdAt))
        assertNull(fetchedAssignmentsEntities.first().deletedAt)
    }
}
