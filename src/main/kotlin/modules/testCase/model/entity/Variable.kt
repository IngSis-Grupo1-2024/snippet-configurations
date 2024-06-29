package modules.testCase.model.entity

import jakarta.persistence.*


@Entity
@Table(name = "VARIABLES", uniqueConstraints = [
    UniqueConstraint(columnNames = ["test_case_id", "variable_type_id"])
])
data class Variable(

    @ManyToOne
    @JoinColumn(name = "test_case_id")
    val testCaseId: TestCase,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "VARIABLES_VALUES")
    @Column(name = "variables")
    var variables: List<String> = ArrayList(),

    @ManyToOne
    @JoinColumn(name = "variable_type_id")
    var variableType: VariableType = VariableType(),
) {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0

        constructor() : this(TestCase(), listOf(), VariableType())
}
