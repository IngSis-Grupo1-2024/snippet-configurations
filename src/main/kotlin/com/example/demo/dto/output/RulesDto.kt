package com.example.demo.dto.output

import com.example.demo.dto.rule.GetRulesDTO
import lombok.Getter

@Getter
class RulesDto(
    val rules: List<GetRulesDTO>
)