package modules.rule.persistence.repository

import modules.rule.persistence.entity.RuleParent
import org.springframework.data.jpa.repository.JpaRepository

interface RuleParentRepository: JpaRepository<RuleParent, Long>{
    fun findByName(name: String): RuleParent?
}