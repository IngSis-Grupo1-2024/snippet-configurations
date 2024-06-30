package modules.testCase.controller

import modules.rule.controller.RuleController
import modules.testCase.model.dto.Output
import modules.testCase.model.input.TestCaseInput
import modules.testCase.service.TestCaseServiceSpec
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class TestCaseController @Autowired constructor(private val service: TestCaseServiceSpec): TestCaseControllerSpec {
    private val log = LoggerFactory.getLogger(TestCaseController::class.java)

    override fun postTestCase(snippetContent: TestCaseInput): ResponseEntity<Output> {
        log.info("Posting ${snippetContent.name} test case")
        val testCase = service.postTestCase(snippetContent)
        return ResponseEntity.status(testCase.second).body(testCase.first)
    }

    override fun getSnippetId(testCaseId: String): ResponseEntity<String> {
        log.info("Getting the snippet id from $testCaseId test case")
        val testCase = service.getSnippetId(testCaseId)
        return ResponseEntity.status(HttpStatus.OK).body(testCase)
    }

    override fun getTestCaseBySnippet(snippetId: String): ResponseEntity<Output> {
        log.info("Get test cases for snippet $snippetId")
        val testCase = service.getTestCaseBySnippetId(snippetId)
        return ResponseEntity.status(HttpStatus.OK).body(testCase)
    }

    override fun deleteTestCase(testCaseId: String): ResponseEntity<String> {
        log.info("Deleting test case: $testCaseId")
        service.deleteTestCase(testCaseId)
        return ResponseEntity.status(HttpStatus.OK).body("")
    }
}