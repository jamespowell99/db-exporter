package com.dryhome.dbexporter

import org.springframework.jdbc.core.JdbcTemplate
import spock.lang.Specification

class ExporterServiceTest extends Specification {
    def "test"() {
        when:
        new ExporterService(Mock(JdbcTemplate)).export()

        then:
        true
    }
}
