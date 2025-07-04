package com.java.hash.domain.repositories;

import com.java.hash.domain.entities.dto.VerificationResultResponseDTO;

/**
 *
 * @author Jhonatan
 */
public interface ISignatureVerifyRepository {

    VerificationResultResponseDTO verify(byte[] signedDataBase64);
}