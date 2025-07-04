package com.java.hash.domain.entities.dto;

import com.java.hash.domain.Enums.EnumStatusVerification;

/**
 *
 * @author Jhonatan
 */
public class VerificationResultResponseDTO {

    private EnumStatusVerification status;

    private ResultResponseDTO infos;

    public VerificationResultResponseDTO(EnumStatusVerification status, ResultResponseDTO infos) {
        this.status = status;
        this.infos = infos;
    }

    public VerificationResultResponseDTO(EnumStatusVerification status) {
        this.status = status;
    }

    public EnumStatusVerification getStatus() {
        return status;
    }

    public void setStatus(EnumStatusVerification status) {
        this.status = status;
    }

    public ResultResponseDTO getInfos() {
        return infos;
    }

    public void setInfos(ResultResponseDTO infos) {
        this.infos = infos;
    }

}