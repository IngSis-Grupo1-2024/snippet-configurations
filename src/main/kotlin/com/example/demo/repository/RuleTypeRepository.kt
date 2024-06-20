package com.example.demo.repository
import com.example.demo.model.RuleType
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RuleTypeRepository : JpaRepository<RuleType, Long>{
    fun findByType(type: String): RuleType?
}
