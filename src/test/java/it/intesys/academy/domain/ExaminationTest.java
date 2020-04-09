package it.intesys.academy.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import it.intesys.academy.web.rest.TestUtil;

public class ExaminationTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Examination.class);
        Examination examination1 = new Examination();
        examination1.setId(1L);
        Examination examination2 = new Examination();
        examination2.setId(examination1.getId());
        assertThat(examination1).isEqualTo(examination2);
        examination2.setId(2L);
        assertThat(examination1).isNotEqualTo(examination2);
        examination1.setId(null);
        assertThat(examination1).isNotEqualTo(examination2);
    }
}
