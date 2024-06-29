package modules.testCase.model.entity

import jakarta.persistence.*


@Entity
@Table(name = "TESTCASE")
data class TestCase (

    @Column(length = 512)
    val snippetId: String = "",

    @Column(length = 512)
    val name: String = "",

    @OneToMany(mappedBy = "testCaseId")
    val variables: List<Variable> = ArrayList(),

    ) {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    constructor() : this("","")
}
