package com.java.hash.infra;

import java.nio.charset.StandardCharsets;

import com.java.hash.domain.Enums.EnumErrorCode;
import com.java.hash.domain.Enums.EnumStatusVerification;
import com.java.hash.domain.entities.dto.VerificationResultResponseDTO;
import com.java.hash.domain.repositories.ISignatureGeneratorRepository;
import com.java.hash.domain.repositories.ISignatureVerifyRepository;
import com.java.hash.domain.utils.HashException;

public class InMemorySignDataWithRepository implements ISignatureGeneratorRepository, ISignatureVerifyRepository {

    String passwordAuth;
    String base64 = "MIAGCSqGSIb3DQEHAqCAMIACAQExDTALBglghkgBZQMEAgMwgAYJKoZIhvcNAQcBo";

    @Override
    public String sign(byte[] data, byte[] p12Bytes, String password) {
        if (password == null || !password.equals(passwordAuth)) {
            throw new HashException(EnumErrorCode.ERRO_KEYSTORE);
        }
        if (p12Bytes == null || p12Bytes.length == 0) {
            throw new HashException(EnumErrorCode.ERRO_KEYSTORE);
        }
        return "assinaturaFakeBase64==";

    }

    public void setPassword(String password) {
        passwordAuth = password;
    }

    @Override
    public VerificationResultResponseDTO verify(byte[] signedDataBase64) {
        String decodedString;

        try {
            decodedString = new String(signedDataBase64, StandardCharsets.UTF_8);
            java.util.Base64.getDecoder().decode(signedDataBase64);
        } catch (IllegalArgumentException e) {
            throw new HashException(EnumErrorCode.ERRO_IO, e);
        }

        if (decodedString.contains("Assinatura")) {
            return new VerificationResultResponseDTO(EnumStatusVerification.VALIDO);
        } else {
            return new VerificationResultResponseDTO(EnumStatusVerification.INVALIDO);
        }

    }
}