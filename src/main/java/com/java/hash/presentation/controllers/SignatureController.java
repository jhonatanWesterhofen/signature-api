package com.java.hash.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.java.hash.domain.entities.dto.VerificationResultResponseDTO;
import com.java.hash.service.DigitalSignatureService;
import com.java.hash.service.SignatureVerificationService;

@RestController
@RequestMapping("/")
public class SignatureController {

    @Autowired
    private DigitalSignatureService digitalSignatureService;

    @Autowired
    private SignatureVerificationService signatureVerificationService;

    @PostMapping(value = "/signature", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String signFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("p12") MultipartFile p12,
            @RequestParam("password") String password) {

        return digitalSignatureService.signFile(file, p12, password);
    }

    @PostMapping(value = "/verify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VerificationResultResponseDTO> verifySignature(
            @RequestParam("signedFile") MultipartFile signedFile) {

        return signatureVerificationService.verifySignature(signedFile);
    }
}