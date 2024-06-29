package modules.testCase.service

import modules.testCase.model.dto.TestCaseDto
import modules.testCase.model.dto.TestCasesDto
import modules.testCase.model.entity.TestCase
import modules.testCase.model.entity.Variable
import modules.testCase.model.entity.VariableType
import modules.testCase.model.input.TestCaseInput
import modules.testCase.repository.TestCaseRepository
import modules.testCase.repository.VariableRepository
import modules.testCase.repository.VariableTypeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class TestCaseService
    @Autowired
    constructor(
        private val testCaseRepository: TestCaseRepository,
        private val variableRepository: VariableRepository,
        private val variableTypeRepository: VariableTypeRepository
    ) : TestCaseServiceSpec {

    override fun postTestCase(input: TestCaseInput): Pair<TestCaseDto, HttpStatus> {
        val testCase = testCaseRepository.findById(input.id.toLong())
        return if(testCase.isEmpty) Pair(createTestCase(input), HttpStatus.CREATED)
        else Pair(updateTestCase(input, testCase.get()), HttpStatus.OK)
    }


    override fun getTestCaseBySnippetId(snippetId: String): TestCasesDto {
        return TestCasesDto(testCaseRepository.findBySnippetId(snippetId).map { testCase ->
            getTestCaseDto(testCase)
        })
    }

    override fun getSnippetId(testCaseId: String): String {
        val testCase = testCaseRepository.findById(testCaseId.toLong())
        if(testCase.isEmpty) throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return testCase.get().snippetId
    }

    private fun getTestCaseDto(testCase: TestCase) : TestCaseDto {
        val input = variableRepository.findByVariableTypeAndTestCaseId(getVariableType("INPUT"),testCase)
        val output = variableRepository.findByVariableTypeAndTestCaseId(getVariableType("OUTPUT"),testCase)
        val envVar = variableRepository.findByVariableTypeAndTestCaseId(getVariableType("ENVIRONMENT"),testCase)
        return TestCaseDto(
            id = testCase.id.toString(),
            name = testCase.name,
            input = input!!.variables,
            output = output!!.variables,
            envVars = envVar!!.variables.first(),
        )
    }

    private fun getVariableType(str: String): VariableType {
        return variableTypeRepository.findByName(str)!!
    }

    override fun deleteTestCase(testCaseId: String) {
        val testCase = testCaseRepository.findById(testCaseId.toLong())
        val variables = variableRepository.findByTestCaseId(testCase.get())
        variables.forEach { variable ->
            variableRepository.delete(variable)
        }
        testCaseRepository.deleteById(testCaseId.toLong())
    }


    private fun createTestCase(input: TestCaseInput): TestCaseDto {
        val testCase = testCaseRepository.save(TestCase(input.snippetId, input.name))
        addVariablesInTestCase(input.input, testCase, "INPUT")
        addVariablesInTestCase(input.output, testCase, "OUTPUT")
        addVariablesInTestCase(listOf(input.envVars), testCase, "ENVIRONMENT")
        return TestCaseDto(
            id = testCase.id.toString(),
            name = testCase.name,
            input = input.input,
            output = input.output,
            envVars = input.envVars,
        )
    }

    private fun addVariablesInTestCase(inputs: List<String>?, testCase: TestCase, variableTypeStr: String) {
        val listVars = getListVars(inputs)
        val variableType = variableTypeRepository.findByName(variableTypeStr)!!
        variableRepository.save(Variable(testCase, listVars, variableType))
    }

    private fun getListVars(inputs: List<String>?): List<String> {
        return if(inputs.isNullOrEmpty()) emptyList()
        else inputs
    }

    private fun updateTestCase(input: TestCaseInput, testCase: TestCase): TestCaseDto {
        testCase.variables.forEach { vars ->
            updateInput(vars, input.input, "INPUT")
            updateInput(vars, input.output, "OUTPUT")
            updateInput(vars, input.output, "OUTPUT")
        }
        testCaseRepository.save(testCase)
        return TestCaseDto(
            id = testCase.id.toString(),
            name = testCase.name,
            input = input.input,
            output = input.output,
            envVars = input.envVars,
        )
    }

    private fun updateInput(
        vars: Variable,
        list: List<String>?,
        variableTypeStr: String
    ) {
        val variableType = variableTypeRepository.findByName(variableTypeStr)!!
        if (vars.variableType == variableType) {
            vars.variables = getListVars(list)
            variableRepository.save(vars)
        }
    }
}