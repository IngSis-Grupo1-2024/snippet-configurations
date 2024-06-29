package modules.rule.persistence.repository

import modules.rule.persistence.entity.RuleDescription
import modules.rule.persistence.entity.RuleParent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RuleDescriptionRepository: JpaRepository<RuleDescription, Long> {
    fun findByDescriptionAndRuleParent(description: String, parent: RuleParent): RuleDescription?
}
