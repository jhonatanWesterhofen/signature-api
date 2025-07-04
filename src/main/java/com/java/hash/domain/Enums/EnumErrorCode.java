package com.java.hash.domain.Enums;

import com.java.hash.domain.utils.EnumUtils;

/**
 *
 * @author Jhonatan
 */
public enum EnumErrorCode implements IEnum {

    FALHA_AO_PARSAR("FALHA_AO_PARSAR", "Falha ao ao processar certificado ", 400),
    FALHA_GERACAO_CERTIFICADO("VALIDO", "Não foi possível gerar a assinatura digital com o certificado informado ",
            400),
    FALHA_VERIFICACAO_CERTIFICADO("FALHA_VERIFICACAO", "Não foi possível verificar assinatura digital", 400),
    ERRO_CERTIFICADO("ERRO_CERTIFICADO", "Erro ao carregar ou validar certificado", 400),
    ERRO_KEYSTORE("ERRO_KEYSTORE", "Erro ao acessar o KeyStore", 400),
    ERRO_IO("ERRO_IO", "Erro de entrada/saída durante o processamento", 400),
    ERRO_ALGORITMO("ERRO_ALGORITMO", "Algoritmo de assinatura não suportado ou inválido", 400),
    ERRO_COMUNICACAO("ERRO_COMUNICACAO", "Erro na comunicação com serviço externo ou componente", 400),
    ERRO_ARQUIVO_NAO_ENCONTRADO("ERRO_ARQUIVO_NAO_ENCONTRADO", "Arquivo necessário para assinatura não encontrado",
            400),
    ERRO_SALVAR_ARQUIVO("ERRO_SALVAR_ARQUIVO", "Erro ao salvar arquivo de assinatura", 400),
    ERRO_CONFIGURACAO_CERTIFICADO("ERRO_CONFIGURACAO_CERTIFICADO", "Configuração inválida do certificado", 400),
    ERRO_TEMPO_CERTIFICADO("ERRO_TEMPO_CERTIFICADO", "Certificado expirado ou não válido no tempo atual", 400);

    private final String key, error;
    private final int httpStatus;

    private EnumErrorCode(String key, String value, int httpStatus) {
        this.key = key;
        this.error = value;
        this.httpStatus = httpStatus;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public String getError() {
        return this.error;
    }

    @Override
    public boolean containsInEnum(String key) {
        for (EnumErrorCode en : EnumErrorCode.values()) {
            if (key.equals(en.getKey())) {
                return true;
            }
        }
        return false;
    }

    public static EnumErrorCode parseByKey(String key) {
        return (EnumErrorCode) EnumUtils.parseByKey(EnumErrorCode.class, key);
    }
}
