package com.java.hash.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.java.hash.domain.Enums.EnumStatusVerification;
import com.java.hash.domain.entities.dto.VerificationResultResponseDTO;
import com.java.hash.domain.usecase.SignDataWithCertificate;
import com.java.hash.domain.usecase.VerifySignature;
import com.java.hash.domain.utils.HashException;
import com.java.hash.infra.InMemorySignDataWithRepository;

public class SignDataWithCertificateTest {

    InMemorySignDataWithRepository inMemorySingRepository = new InMemorySignDataWithRepository();

    @Test
    @DisplayName("Deve ser possível lançar exceção para senha diferente")
    public void mustBePossibleThrowExecptionForPasswordDifferent() {

        SignDataWithCertificate signData = new SignDataWithCertificate(inMemorySingRepository);

        byte[] data = "dados".getBytes();
        byte[] p12 = "certificado".getBytes();
        String password = "senha";

        inMemorySingRepository.setPassword("bryTech");

        assertThrows(HashException.class, () -> {
            signData.execute(data, p12, password);
        });
    }

    @Test
    @DisplayName("Deve ser possível passar senha correta")
    public void mustBePossiblePasswordCorrect() {

        SignDataWithCertificate signData = new SignDataWithCertificate(inMemorySingRepository);

        byte[] data = "dados".getBytes();
        byte[] p12 = "certificado".getBytes();
        String password = "bryTech";

        inMemorySingRepository.setPassword("bryTech");

        String response = signData.execute(data, p12, password);

        assertEquals(22, response.length());

    }

    @Test
    @DisplayName("Deve lançar HashException para p12 inválido no sign()")
    public void shouldThrowHashExceptionWhenP12IsInvalid() {
        inMemorySingRepository.setPassword("qualquer");

        SignDataWithCertificate signData = new SignDataWithCertificate(inMemorySingRepository);

        byte[] data = "dados".getBytes();
        byte[] p12Invalido = "p12invalido".getBytes();
        String senha = "senha";

        assertThrows(HashException.class, () -> {
            signData.execute(data, p12Invalido, senha);
        });
    }

    @Test
    @DisplayName("Deve lançar HashException para Base64 inválido no verify()")
    public void shouldThrowHashExceptionWhenBase64IsInvalidOnVerify() {
        VerifySignature verify = new VerifySignature(inMemorySingRepository);
        byte[] base64Invalido = "###INVALID==BASE64==###".getBytes();

        assertThrows(HashException.class, () -> {
            verify.execute(base64Invalido);
        });
    }

    @Test
    @DisplayName("Deve retornar status INVALIDO para assinatura inválida no verify()")
    public void shouldReturnInvalidStatusForInvalidSignatureOnVerify() {
        VerifySignature verify = new VerifySignature(inMemorySingRepository);
        String fakeSignature = java.util.Base64.getEncoder().encodeToString("assinatura falsa".getBytes());
        byte[] fakeSignatureBytes = fakeSignature.getBytes();

        VerificationResultResponseDTO resultado = verify.execute(fakeSignatureBytes);

        assertEquals(EnumStatusVerification.INVALIDO, resultado.getStatus());
    }
}