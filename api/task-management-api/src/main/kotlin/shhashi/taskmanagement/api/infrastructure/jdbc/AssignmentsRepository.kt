package shhashi.taskmanagement.api.infrastructure.jdbc

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import shhashi.taskmanagement.api.infrastructure.jdbc.entity.AssignmentsEntity
import java.math.BigInteger

@Repository
interface AssignmentsRepository : CrudRepository<AssignmentsEntity, BigInteger>
