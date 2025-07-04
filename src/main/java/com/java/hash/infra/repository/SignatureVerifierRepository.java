package com.java.hash.infra.repository;

import java.io.ByteArrayInputStream;
import java.security.Security;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1UTCTime;
import org.bouncycastle.asn1.DERUTCTime;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.CMSAttributes;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.CMSException;
import org.bouncycastle.cms.CMSSignedData;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.cms.SignerInformationStore;
import org.bouncycastle.cms.jcajce.JcaSimpleSignerInfoVerifierBuilder;
import org.bouncycastle.util.Store;
import org.springframework.stereotype.Repository;

import com.java.hash.domain.Enums.EnumStatusVerification;
import com.java.hash.domain.entities.dto.ResultResponseDTO;
import com.java.hash.domain.entities.dto.VerificationResultResponseDTO;
import com.java.hash.domain.repositories.ISignatureVerifyRepository;
import com.java.hash.domain.utils.Utils;
import com.java.hash.utils.DateUtil;
import com.java.hash.utils.HashUtil;
import com.java.hash.utils.exception.HandleExceptionUtil;

/**
 *
 * @author Jhonatan
 */
@Repository
public class SignatureVerifierRepository implements ISignatureVerifyRepository {

    protected static final String INVALIDO = "INVALIDO";

    protected static final String VALIDO = "VALIDO";

    protected static final String SIGNATURE_ALGORITHM = "SHA-512";

    protected static final String CERTIFICATE_TYPE = "X.509";

    private HandleExceptionUtil handleException;

    public SignatureVerifierRepository(HandleExceptionUtil handleException) {
        this.handleException = handleException;
    }

    @Override
    public VerificationResultResponseDTO verify(byte[] signedDataBase64) {

        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            CMSSignedData signedData = parseSignedData(signedDataBase64);
            byte[] contentBytes = extractContentBytes(signedData);
            String hashHex = HashUtil.calculateSHA512Hex(contentBytes);

            SignerInformationStore signers = signedData.getSignerInfos();
            Store<X509CertificateHolder> certStore = signedData.getCertificates();

            for (SignerInformation signer : signers.getSigners()) {
                Optional<X509Certificate> certificateOpt = extractCertificate(certStore, signer);

                if (Utils.isNull(certificateOpt)) {
                    return buildInvalidResult();
                }

                X509Certificate certificate = certificateOpt.get();
                if (signer.verify(new JcaSimpleSignerInfoVerifierBuilder().build(certificate))) {

                    String signerName = certificate.getSubjectX500Principal().getName();
                    String signingDate = extractSigningDate(signer).orElse(DateUtil.toIso8601String(new Date(0)));

                    return resolveVerificationResult(VALIDO, resultResponse(signerName, signingDate, hashHex, hashHex));
                }
            }

            return buildInvalidResult();

        } catch (Exception e) {
            throw handleException.handleException(e);
        }
    }

    private VerificationResultResponseDTO buildInvalidResult() {
        return new VerificationResultResponseDTO(resolveStatus(INVALIDO));
    }

    private CMSSignedData parseSignedData(byte[] base64Data) throws CMSException {
        byte[] signedDataBytes = Base64.getDecoder().decode(base64Data);
        return new CMSSignedData(signedDataBytes);
    }

    private byte[] extractContentBytes(CMSSignedData signedData) {
        return (byte[]) signedData.getSignedContent().getContent();
    }

    private Optional<X509Certificate> extractCertificate(Store<X509CertificateHolder> certStore,
            SignerInformation signer) throws Exception {
        Collection<X509CertificateHolder> certCollection = certStore.getMatches(signer.getSID());

        if (certCollection.isEmpty()) {
            return Optional.empty();
        }

        X509CertificateHolder certHolder = certCollection.iterator().next();
        CertificateFactory certFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE);

        X509Certificate cert = (X509Certificate) certFactory.generateCertificate(
                new ByteArrayInputStream(certHolder.getEncoded()));

        return Optional.of(cert);
    }

    private Optional<String> extractSigningDate(SignerInformation signer) {

        try {

            AttributeTable signedAttributes = signer.getSignedAttributes();

            if (signedAttributes == null)
                return Optional.empty();

            Attribute signingTimeAttr = signedAttributes.get(CMSAttributes.signingTime);
            if (signingTimeAttr == null)
                return Optional.empty();

            ASN1Encodable timeValue = signingTimeAttr.getAttrValues().getObjectAt(0);
            Date date = null;

            if (timeValue instanceof ASN1UTCTime) {
                date = ((ASN1UTCTime) timeValue).getDate();
            } else if (timeValue instanceof DERUTCTime) {
                date = ((DERUTCTime) timeValue).getDate();
            }

            return date != null ? Optional.of(DateUtil.toIso8601String(date)) : Optional.empty();
        } catch (Exception e) {
            throw handleException.handleException(e);
        }
    }

    private EnumStatusVerification resolveStatus(String status) {
        return EnumStatusVerification.findByKey(status);
    }

    private ResultResponseDTO resultResponse(String name, String data, String hash, String hashKey) {
        return new ResultResponseDTO(name, data, hash, hashKey);
    }

    private VerificationResultResponseDTO resolveVerificationResult(String status,
            ResultResponseDTO resultResponseDTO) {
        return new VerificationResultResponseDTO(resolveStatus(VALIDO), resultResponseDTO);
    }
}