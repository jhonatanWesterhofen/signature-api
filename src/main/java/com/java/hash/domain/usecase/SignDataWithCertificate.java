package com.java.hash.domain.usecase;

import com.java.hash.domain.repositories.ISignatureGeneratorRepository;

/**
 *
 * @author Jhonatan
 */
public class SignDataWithCertificate {

    private ISignatureGeneratorRepository iSignatureGenerator;

    public SignDataWithCertificate(ISignatureGeneratorRepository iSignatureGenerator) {
        this.iSignatureGenerator = iSignatureGenerator;
    }

    public String execute(byte[] data, byte[] p12Bytes, String password) {
        return iSignatureGenerator.sign(data, p12Bytes, password);
    }
}