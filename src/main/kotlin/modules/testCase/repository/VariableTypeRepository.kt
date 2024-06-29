package modules.testCase.repository
import modules.testCase.model.entity.TestCase
import modules.testCase.model.entity.VariableType
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface VariableTypeRepository : JpaRepository<VariableType, Long> {
    fun findByName(name: String): VariableType?
}
