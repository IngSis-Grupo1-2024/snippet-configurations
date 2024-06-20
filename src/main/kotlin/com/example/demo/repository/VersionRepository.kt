package com.example.demo.repository
import com.example.demo.model.Version
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface VersionRepository : JpaRepository<Version, Long>{
    fun findByNumber(number: String): Version?
}
