package modules.rule.persistence.repository
import modules.rule.persistence.entity.Rule
import modules.rule.persistence.entity.RuleDescription
import modules.rule.persistence.entity.RuleType
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RuleRepository : JpaRepository<Rule, Long>{
    fun findByRuleDescriptionAndUserId(ruleDescription: RuleDescription, userId: String): Rule?
    fun findByRuleTypeAndUserId(ruleType: RuleType, userId: String): List<Rule>
    fun findByUserId(userId: String): List<Rule>
}
