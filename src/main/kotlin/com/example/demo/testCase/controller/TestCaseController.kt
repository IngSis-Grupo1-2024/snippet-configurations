package com.example.demo.testCase.controller

import com.example.demo.testCase.model.dto.Output
import com.example.demo.testCase.model.input.TestCaseInput
import com.example.demo.testCase.service.TestCaseServiceSpec
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class TestCaseController @Autowired constructor(private val service: TestCaseServiceSpec): TestCaseControllerSpec {
    override fun postTestCase(snippetContent: TestCaseInput): ResponseEntity<Output> {
        val testCase = service.postTestCase(snippetContent)
        return ResponseEntity.status(testCase.second).body(testCase.first)
    }

    override fun getSnippetId(testCaseId: String): ResponseEntity<String> {
        val testCase = service.getSnippetId(testCaseId)
        return ResponseEntity.status(HttpStatus.OK).body(testCase)
    }

    override fun getTestCaseBySnippet(snippetId: String): ResponseEntity<Output> {
        val testCase = service.getTestCaseBySnippetId(snippetId)
        return ResponseEntity.status(HttpStatus.OK).body(testCase)
    }

    override fun deleteTestCase(testCaseId: String): ResponseEntity<String> {
        service.deleteTestCase(testCaseId)
        return ResponseEntity.status(HttpStatus.OK).body("")
    }
}