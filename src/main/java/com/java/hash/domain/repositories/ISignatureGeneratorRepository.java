package com.java.hash.domain.repositories;

/**
 *
 * @author Jhonatan
 */
public interface ISignatureGeneratorRepository {

    String sign(byte[] data, byte[] p12Bytes, String password);

}