package modules.rule.persistence.repository
import modules.rule.persistence.entity.RuleType
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RuleTypeRepository : JpaRepository<RuleType, Long>{
    fun findByType(type: String): RuleType?
}
