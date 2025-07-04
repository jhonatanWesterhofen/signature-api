package com.java.hash.infra.repository;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collections;

import org.bouncycastle.cert.jcajce.JcaCertStore;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.CMSProcessableByteArray;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.CMSSignedDataGenerator;
import org.bouncycastle.cms.jcajce.JcaSignerInfoGeneratorBuilder;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.operator.jcajce.JcaDigestCalculatorProviderBuilder;
import org.springframework.stereotype.Repository;

import com.java.hash.domain.repositories.ISignatureGeneratorRepository;
import com.java.hash.utils.exception.HandleExceptionUtil;

/**
 *
 * @author Jhonatan
 */
@Repository
public class BouncyCastleSignatureRepository implements ISignatureGeneratorRepository {

    protected static final String KEY_STORE_TYPE = "PKCS12";

    protected static final String SECURITY_PROVIDER = "BC";

    protected static final String SIGNATURE_ALGORITHM = "SHA512withRSA";

    private HandleExceptionUtil handleException;

    public BouncyCastleSignatureRepository(HandleExceptionUtil handleException) {
        this.handleException = handleException;
    }

    @Override
    public String sign(byte[] data, byte[] p12Bytes, String password) {

        try {

            KeyStore keystore = KeyStore.getInstance(KEY_STORE_TYPE);
            keystore.load(new ByteArrayInputStream(p12Bytes), password.toCharArray());

            String alias = keystore.aliases().nextElement();
            PrivateKey privateKey = (PrivateKey) keystore.getKey(alias, password.toCharArray());
            Certificate cert = keystore.getCertificate(alias);
            X509Certificate x509Cert = (X509Certificate) cert;

            CMSSignedDataGenerator generator = resolveSignerInfoGeneratorBuilder(x509Cert, privateKey);

            CMSProcessableByteArray msg = new CMSProcessableByteArray(data);
            CMSSignedData signedData = generator.generate(msg, true);
            byte[] signedBytes = signedData.getEncoded();

            Path path = Paths.get("assinaturas/assinatura.p7s");
            Files.write(path, signedBytes);

            return Base64.getEncoder().encodeToString(signedBytes);

        } catch (Exception e) {
            throw handleException.handleException(e);
        }
    }

    public void validateDirectories() {

        try {

            Path outputDir = Paths.get("assinaturas");

            if (!Files.exists(outputDir)) {
                Files.createDirectories(outputDir);
            }
        } catch (Exception e) {
            throw handleException.handleException(e);
        }

    }

    private CMSSignedDataGenerator resolveSignerInfoGeneratorBuilder(X509Certificate x509Cert, PrivateKey privateKey) {

        try {

            JcaContentSignerBuilder signerBuilder = new JcaContentSignerBuilder(SIGNATURE_ALGORITHM)
                    .setProvider(SECURITY_PROVIDER);

            CMSSignedDataGenerator generator = new CMSSignedDataGenerator();

            generator.addSignerInfoGenerator(new JcaSignerInfoGeneratorBuilder(
                    new JcaDigestCalculatorProviderBuilder().setProvider(SECURITY_PROVIDER).build())
                    .build(signerBuilder.build(privateKey), new JcaX509CertificateHolder(x509Cert)));

            generator.addCertificates(
                    new JcaCertStore(Collections.singletonList(x509Cert)));

            return generator;

        } catch (Exception e) {
            throw handleException.handleException(e);
        }
    }
}