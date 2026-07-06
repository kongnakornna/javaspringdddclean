package com.icmon.module.inventory.domain;

import com.icmon.module.inventory.domain.enums.PickingStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static org.assertj.core.api.Assertions.*;

@DisplayName("Part Picking Request Domain Tests")
class TPartPickingRequestTest {

    @Test
    @DisplayName("should confirm picking request")
    void shouldConfirmPicking() {
        TPartPickingRequest request = new TPartPickingRequest();
        request.setStatus(PickingStatus.DRAFT);
        UUID userId = UUID.randomUUID();
        request.confirm(userId);
        assertThat(request.getStatus()).isEqualTo(PickingStatus.CONFIRMED);
        assertThat(request.getConfirmedBy()).isEqualTo(userId);
    }

    @Test
    @DisplayName("should throw exception when confirming cancelled request")
    void shouldThrowExceptionWhenConfirmingCancelled() {
        TPartPickingRequest request = new TPartPickingRequest();
        request.setStatus(PickingStatus.CANCELLED);
        assertThatThrownBy(() -> request.confirm(UUID.randomUUID()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cannot confirm a cancelled picking request");
    }
}
