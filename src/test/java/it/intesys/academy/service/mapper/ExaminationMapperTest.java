package it.intesys.academy.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ExaminationMapperTest {

    private ExaminationMapper examinationMapper;

    @BeforeEach
    public void setUp() {
        examinationMapper = new ExaminationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(examinationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(examinationMapper.fromId(null)).isNull();
    }
}
