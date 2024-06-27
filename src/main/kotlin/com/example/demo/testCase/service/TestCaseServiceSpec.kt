package com.example.demo.testCase.service

import com.example.demo.testCase.model.dto.TestCaseDto
import com.example.demo.testCase.model.dto.TestCasesDto
import com.example.demo.testCase.model.input.TestCaseInput
import org.springframework.http.HttpStatus

interface TestCaseServiceSpec {
    fun postTestCase(input: TestCaseInput): Pair<TestCaseDto, HttpStatus>

    fun getTestCaseBySnippetId(snippetId: String) : TestCasesDto

    fun deleteTestCase(testCaseId: String)
    fun getSnippetId(testCaseId: String): String
}