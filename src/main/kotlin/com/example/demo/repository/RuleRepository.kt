package com.example.demo.repository
import com.example.demo.model.Configuration
import com.example.demo.model.Rule
import com.example.demo.model.RuleDescription
import com.example.demo.model.RuleType
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RuleRepository : JpaRepository<Rule, Long>{
    fun findByRuleDescriptionAndUserId(ruleDescription: RuleDescription, userId: String): Rule?
    fun findByRuleTypeAndUserId(ruleType: RuleType, userId: String): List<Rule>
}
