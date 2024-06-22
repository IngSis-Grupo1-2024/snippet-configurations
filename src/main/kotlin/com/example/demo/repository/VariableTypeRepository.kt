package com.example.demo.repository

import com.example.demo.model.VariableType
import org.springframework.data.jpa.repository.JpaRepository

interface VariableTypeRepository : JpaRepository<VariableType, Long>
