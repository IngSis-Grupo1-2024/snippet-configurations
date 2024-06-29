package modules.testCase.service

import modules.testCase.model.dto.TestCaseDto
import modules.testCase.model.dto.TestCasesDto
import modules.testCase.model.input.TestCaseInput
import org.springframework.http.HttpStatus

interface TestCaseServiceSpec {
    fun postTestCase(input: TestCaseInput): Pair<TestCaseDto, HttpStatus>

    fun getTestCaseBySnippetId(snippetId: String) : TestCasesDto

    fun deleteTestCase(testCaseId: String)
    fun getSnippetId(testCaseId: String): String
}