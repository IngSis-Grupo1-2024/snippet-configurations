package modules.testCase.controller

import modules.testCase.model.dto.Output
import modules.testCase.model.input.TestCaseInput
import modules.testCase.service.TestCaseServiceSpec
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class TestCaseController @Autowired constructor(private val service: TestCaseServiceSpec) {
    private val log = LoggerFactory.getLogger(TestCaseController::class.java)

    @PostMapping("/test_case")
    fun postTestCase(snippetContent: TestCaseInput): ResponseEntity<Output> {
        log.info("Posting ${snippetContent.name} test case")
        val testCase = service.postTestCase(snippetContent)
        return ResponseEntity.status(testCase.second).body(testCase.first)
    }

    @GetMapping("/test_case/snippet/{testCaseId}")
    fun getSnippetId(@PathVariable testCaseId: String): ResponseEntity<String> {
        log.info("Getting the snippet id from $testCaseId test case")
        val testCase = service.getSnippetId(testCaseId)
        return ResponseEntity.status(HttpStatus.OK).body(testCase)
    }

    @GetMapping("/test_case/{snippetId}")
    fun getTestCaseBySnippet(@PathVariable snippetId: String): ResponseEntity<Output> {
        log.info("Get test cases for snippet $snippetId")
        val testCase = service.getTestCaseBySnippetId(snippetId)
        return ResponseEntity.status(HttpStatus.OK).body(testCase)
    }

    @DeleteMapping("/test_case/{testCaseId}")
    fun deleteTestCase(@PathVariable testCaseId: String): ResponseEntity<String> {
        log.info("Deleting test case: $testCaseId")
        service.deleteTestCase(testCaseId)
        return ResponseEntity.status(HttpStatus.OK).body("")
    }
}