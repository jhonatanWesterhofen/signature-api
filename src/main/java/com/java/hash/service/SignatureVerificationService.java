package com.java.hash.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java.hash.domain.Enums.EnumErrorCode;
import com.java.hash.domain.entities.dto.VerificationResultResponseDTO;
import com.java.hash.domain.usecase.VerifySignature;
import com.java.hash.infra.repository.SignatureVerifierRepository;

/**
 *
 * @author Jhonatan
 */
@Service
public class SignatureVerificationService {

    private SignatureVerifierRepository signatureVerifierRepository;

    public SignatureVerificationService(SignatureVerifierRepository signatureVerifierRepository) {
        this.signatureVerifierRepository = signatureVerifierRepository;
    }

    public ResponseEntity<VerificationResultResponseDTO> verifySignature(MultipartFile signedFile) {

        try {

            byte[] signedBytes = signedFile.getBytes();

            VerifySignature verifySignature = new VerifySignature(signatureVerifierRepository);
            VerificationResultResponseDTO response = verifySignature.execute(signedBytes);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            throw new IllegalArgumentException(EnumErrorCode.FALHA_VERIFICACAO_CERTIFICADO.getError(), e);
        }
    }
}