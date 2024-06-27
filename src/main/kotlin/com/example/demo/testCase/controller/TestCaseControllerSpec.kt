package com.example.demo.testCase.controller

import com.example.demo.testCase.model.dto.Output
import com.example.demo.testCase.model.input.TestCaseInput
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/test_case")
interface TestCaseControllerSpec {
    @PostMapping
    fun postTestCase(@RequestBody snippetContent: TestCaseInput): ResponseEntity<Output>

    @GetMapping("/snippet/{testCaseId}")
    fun getSnippetId(@PathVariable testCaseId: String) : ResponseEntity<String>

    @GetMapping("{snippetId}")
    fun getTestCaseBySnippet(@PathVariable snippetId: String) : ResponseEntity<Output>

    @DeleteMapping("{testCaseId}")
    fun deleteTestCase(@PathVariable testCaseId: String) : ResponseEntity<String>
}