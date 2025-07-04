package com.java.hash.utils.exception;

import java.io.IOException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.java.hash.domain.Enums.EnumErrorCode;
import com.java.hash.domain.utils.HashException;

@Component
public class HandleExceptionUtil {

    private static final Logger logger = LoggerFactory.getLogger(HandleExceptionUtil.class);

    public HashException handleException(Exception ex) {
        if (ex instanceof HashException) {
            return (HashException) ex;
        }

        if (ex instanceof CertificateException)

        {
            logger.error("Erro de certificado: " + ex.getMessage(), ex);
            return new HashException(EnumErrorCode.ERRO_CERTIFICADO);
        }

        if (ex instanceof KeyStoreException) {
            logger.error("Erro no KeyStore: " + ex.getMessage(), ex);
            return new HashException(EnumErrorCode.ERRO_KEYSTORE);
        }

        if (ex instanceof IOException) {
            logger.error("Erro de I/O: " + ex.getMessage(), ex);
            return new HashException(EnumErrorCode.ERRO_IO);
        }

        if (ex instanceof NoSuchAlgorithmException) {
            logger.error("Algoritmo não encontrado: " + ex.getMessage(), ex);
            return new HashException(EnumErrorCode.ERRO_ALGORITMO);
        }

        logger.error(EnumErrorCode.ERRO_COMUNICACAO.getError(), ex);
        return new HashException(EnumErrorCode.ERRO_COMUNICACAO);
    }

}
