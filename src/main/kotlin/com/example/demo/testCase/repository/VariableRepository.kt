package com.example.demo.testCase.repository
import com.example.demo.testCase.model.entity.TestCase
import com.example.demo.testCase.model.entity.Variable
import com.example.demo.testCase.model.entity.VariableType
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*

@Repository
interface VariableRepository : JpaRepository<Variable, Long> {
    fun findByVariableTypeAndTestCaseId(variableType: VariableType, testCase: TestCase): Variable?
    fun findByTestCaseId(testCase: TestCase): List<Variable>
}
