package com.java.hash.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.java.hash.domain.Enums.EnumStatusVerification;
import com.java.hash.domain.entities.dto.VerificationResultResponseDTO;
import com.java.hash.domain.usecase.VerifySignature;
import com.java.hash.infra.InMemorySignDataWithRepository;

public class VerifySignatureTest {

    InMemorySignDataWithRepository inMemorySingRepository = new InMemorySignDataWithRepository();

    @Test
    @DisplayName("Deve ser possível retornar status inválido")
    void mustBePossibleReturStatusInvalid() {

        VerifySignature verify = new VerifySignature(inMemorySingRepository);

        String fakeBase64 = "MIAGCSqGSIb3DQEHAqCAMIACAQExDTAkgBZQMEAgEwCwYJKoZIhvcNAQEB";
        byte[] fakeInput = fakeBase64.getBytes(StandardCharsets.UTF_8);

        VerificationResultResponseDTO response = verify.execute(fakeInput);

        assertEquals(response.getStatus(), EnumStatusVerification.INVALIDO);

    }

    @Test
    @DisplayName("Deve ser possível retornar status válido")
    void mustBePossibleReturStatusValid() {

        VerifySignature verify = new VerifySignature(inMemorySingRepository);

        String fakeBase64 = "MIAGCSqGSIb3DQEHAqCAMIACAQExDTAkAssinaturagBZQMEAgEwCwYJKoZIhvcNAQEB";
        byte[] fakeInput = fakeBase64.getBytes(StandardCharsets.UTF_8);

        VerificationResultResponseDTO response = verify.execute(fakeInput);

        assertEquals(response.getStatus(), EnumStatusVerification.VALIDO);

    }
}