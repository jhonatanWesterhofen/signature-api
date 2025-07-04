package com.java.hash.domain.usecase;

import com.java.hash.domain.entities.dto.VerificationResultResponseDTO;
import com.java.hash.domain.repositories.ISignatureVerifyRepository;

/**
 *
 * @author Jhonatan
 */
public class VerifySignature {

    private ISignatureVerifyRepository iSignatureVerifyRepository;

    public VerifySignature(ISignatureVerifyRepository iSignatureVerifyRepository) {
        this.iSignatureVerifyRepository = iSignatureVerifyRepository;
    }

    public VerificationResultResponseDTO execute(byte[] signedDataBase64) {
        return iSignatureVerifyRepository.verify(signedDataBase64);
    }
}