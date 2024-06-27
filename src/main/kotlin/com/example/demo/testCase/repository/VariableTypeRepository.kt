package com.example.demo.testCase.repository
import com.example.demo.testCase.model.entity.TestCase
import com.example.demo.testCase.model.entity.VariableType
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface VariableTypeRepository : JpaRepository<VariableType, Long> {
    fun findByName(name: String): VariableType?
}
