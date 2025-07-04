package com.java.hash.service;

import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.java.hash.domain.Enums.EnumErrorCode;
import com.java.hash.domain.usecase.SignDataWithCertificate;
import com.java.hash.infra.repository.BouncyCastleSignatureRepository;
import com.java.hash.utils.HashUtil;

/**
 *
 * @author Jhonatan
 */
@Service
public class DigitalSignatureService {

    private BouncyCastleSignatureRepository bouncyCastleSignatureRepository;

    public DigitalSignatureService(BouncyCastleSignatureRepository bouncyCastleSignatureRepository) {
        this.bouncyCastleSignatureRepository = bouncyCastleSignatureRepository;
    }

    public String signFile(MultipartFile file, MultipartFile p12, String password) {

        try {

            InputStream fileStream = file.getInputStream();
            InputStream p12Stream = p12.getInputStream();

            byte[] fileBytes = HashUtil.toByteArray(fileStream);
            byte[] p12Bytes = HashUtil.toByteArray(p12Stream);

            SignDataWithCertificate dataWithCertificate = new SignDataWithCertificate(bouncyCastleSignatureRepository);

            String response = dataWithCertificate.execute(fileBytes, p12Bytes, password);

            return response;
        } catch (Exception e) {
            throw new IllegalArgumentException(EnumErrorCode.FALHA_AO_PARSAR.getError());
        }
    }
}