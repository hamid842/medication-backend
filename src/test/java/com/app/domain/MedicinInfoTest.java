package com.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.app.web.rest.TestUtil;

public class MedicinInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicinInfo.class);
        MedicinInfo medicinInfo1 = new MedicinInfo();
        medicinInfo1.setId(1L);
        MedicinInfo medicinInfo2 = new MedicinInfo();
        medicinInfo2.setId(medicinInfo1.getId());
        assertThat(medicinInfo1).isEqualTo(medicinInfo2);
        medicinInfo2.setId(2L);
        assertThat(medicinInfo1).isNotEqualTo(medicinInfo2);
        medicinInfo1.setId(null);
        assertThat(medicinInfo1).isNotEqualTo(medicinInfo2);
    }
}
