package it.intesys.academy.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.intesys.academy.web.rest.TestUtil;

public class ExaminationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExaminationDTO.class);
        ExaminationDTO examinationDTO1 = new ExaminationDTO();
        examinationDTO1.setId(1L);
        ExaminationDTO examinationDTO2 = new ExaminationDTO();
        assertThat(examinationDTO1).isNotEqualTo(examinationDTO2);
        examinationDTO2.setId(examinationDTO1.getId());
        assertThat(examinationDTO1).isEqualTo(examinationDTO2);
        examinationDTO2.setId(2L);
        assertThat(examinationDTO1).isNotEqualTo(examinationDTO2);
        examinationDTO1.setId(null);
        assertThat(examinationDTO1).isNotEqualTo(examinationDTO2);
    }
}
