package com.example.demo.testCase.model.dto

data class TestCaseDto(
    val id: String,
    val name: String,
    val input: List<String>?,
    val output: List<String>?,
    val envVars: String
) : Output