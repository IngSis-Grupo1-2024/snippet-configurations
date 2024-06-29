package modules.testCase.repository
import modules.testCase.model.entity.TestCase
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TestCaseRepository : JpaRepository<TestCase, Long> {
    fun findBySnippetId(snippetId: String): List<TestCase>
}
