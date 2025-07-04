# 🖋️ SIGNATURE API

API REST desenvolvida em **Java 8 + Spring Boot 2.7.1** com foco em operações de **assinatura digital (padrão CMS .p7s)** e **verificação de assinaturas**, conforme desafio proposto para vaga de Desenvolvedor Back-end Java.

## 📑 Sumário

- [Tecnologias utilizadas](#-tecnologias-utilizadas)
- [Como executar o projeto](#-como-executar-o-projeto)
- [Endpoints](#endpoints)
  - [1. /signature - Assinar um arquivo](#1-signature---assinar-um-arquivo)
  - [2. /verify - Verificar assinatura](#2-verify---verificar-assinatura)
- [Swagger (Documentação OpenAPI)](#swagger-documentacao-openapi)
- [Testes Unitários](#testes-unitarios)
- [Resumo das Etapas do Desafio](#resumo-das-etapas-do-desafio)
- [Dificuldades encontradas](#dificuldades-encontradas)
- [Possíveis melhorias](#possiveis-melhorias)

---

## 🛠️ Tecnologias utilizadas

- Java 8
- Spring Boot 2.7.1
- Maven
- Spring Web
- Springdoc OpenAPI
- JUnit 5
- BouncyCastle (`bcprov`, `bcpkix`)
- Apache Commons IO
- Docker  

---

## ▶️ Como executar o projeto

### Pré-requisitos

- Java 8
- Maven 3.6+
- Docker instalado e rodando  

### Executar localmente

```bash
# Clone o repositório
git clone https://github.com/jhonatanWesterhofen/signature-api.git
cd signature-api

# Build do projeto
mvn clean install

# Executar aplicação
mvn spring-boot:run

```

### Build e geração da imagem Docker (já incluso no mvn clean install/package)
mvn clean package

### Para rodar o container:


```bash
docker run -p 4949:4949 hash-app:latest
```

A API estará disponível em:
📍 http://localhost:4949

## 🔐 Autenticação Básica (Basic Auth)

Para aumentar a segurança da API, é recomendado proteger os endpoints com autenticação básica HTTP (Basic Auth). Essa abordagem exige que o cliente envie um cabeçalho `Authorization` contendo um usuário e senha codificados em Base64 em cada requisição.

### Como funciona?

- O servidor Spring Boot pode ser configurado para exigir autenticação Basic para os endpoints 
`/signature` e `/verify`.
- O cliente deve enviar a seguinte header na requisição:

### Exemplo de uso com curl

```bash
curl -X POST "http://localhost:8080/signature" \
  -H "Authorization: Basic dXNlcjpwYXNz" \
  -F "file=@documento.txt" \
  -F "p12=@certificado.p12" \
  -F "password=minhasenha"

  ```
(Onde dXNlcjpwYXNz é o resultado da codificação Base64 do texto user:pass)

## ⚙️ CI/CD com GitHub Actions

Este projeto utiliza **GitHub Actions** para automatizar o processo de build e publicação do artefato `.jar` na aba de **Releases** do repositório.

Sempre que um push for feito com uma **tag iniciando com `v`** (ex: `v1.0.0`, `v0.1.2`), o GitHub Actions irá:

1. Fazer o checkout do repositório
2. Configurar o Java 8 (Temurin)
3. Executar o build com `mvn clean package`
4. Publicar o `.jar` gerado em `target/` na aba **Releases**

### ✅ Exemplo de uso:

```bash
# Após confirmar que o projeto está funcionando corretamente

# Commit normal
git add .
git commit -m "Finaliza versão 1.0.0"
git push origin master

# Criação da tag de versão
git tag v1.0.0
git push origin v1.0.0


## ✅ Resumo Completo das Etapas do Projeto

### Etapa 1 – Cálculo do Hash SHA-512

- Implementação do cálculo do hash SHA-512 do arquivo de entrada utilizando a classe `MessageDigest` do Java.
- O hash é convertido para uma representação hexadecimal para facilitar a leitura e conferência.
- Esse passo é essencial para garantir a integridade do arquivo antes da assinatura digital.

### Etapa 2 – Geração da Assinatura Digital no padrão CMS Attached

- Utilização da biblioteca **BouncyCastle** para criar assinaturas digitais seguindo o padrão CMS Attached, onde o conteúdo original e a assinatura são encapsulados juntos.
- Extração da chave privada e certificado digital do arquivo `.p12` por meio da API `KeyStore`, garantindo segurança na manipulação das credenciais.
- Assinatura realizada com algoritmo SHA-512 para hashing combinado com RSA para assinatura assimétrica.
- Geração do arquivo `.p7s` resultante, que pode ser transmitido junto com o documento original para validação.

### Etapa 3 – Verificação da Assinatura Digital

- Processo de validação que utiliza novamente o **BouncyCastle** para verificar a integridade do arquivo e autenticidade da assinatura.
- Carregamento da cadeia de certificados confiáveis a partir da pasta `resources/cadeia/`, para validação da cadeia de confiança da assinatura.
- Extração de informações relevantes da assinatura:  
  - Status da assinatura (válida ou inválida)  
  - Nome comum (CN) do signatário  
  - Data e hora da assinatura  
  - Hash do documento  
  - Algoritmo de hash utilizado  
- Resposta estruturada para facilitar o consumo da API por clientes.

### Etapa 4 – Desenvolvimento da API REST com Spring Boot

- Criação de dois endpoints principais para interagir com as funcionalidades de assinatura e verificação:  
  - `POST /signature` para receber arquivos e certificados, retornando a assinatura digital.  
  - `POST /verify` para validar arquivos assinados e retornar o resultado da verificação.  
- Suporte completo a requisições multipart/form-data para upload de arquivos via HTTP.
- Implementação de tratamento de exceções e logs para facilitar manutenção e monitoramento.
- Documentação da API via arquivo OpenAPI YAML customizado e integração com Swagger UI, facilitando testes e uso da API.

### Etapa 5 – Documentação e Relatório Técnico

- Elaboração deste documento `README.md` detalhado como forma de relatório das atividades, justificativas técnicas e resultados alcançados.
- Registro das principais decisões técnicas, dificuldades e soluções adotadas durante o desenvolvimento.

---

## ⚠️ Dificuldades Técnicas Encontradas

- **Complexidade do padrão CMS:** Entender e manipular as estruturas ASN.1 do padrão CMS usando BouncyCastle exigiu estudo aprofundado da documentação e testes para garantir assinaturas compatíveis.
- **Manipulação do arquivo `.p12`:** A extração segura da chave privada e certificado requer manuseio cuidadoso da API `KeyStore` e tratamento correto das senhas, para evitar erros e falhas de segurança.
- **Manter padrão CMS Attached:** Garantir que a assinatura encapsule o conteúdo original corretamente para permitir validação em ferramentas externas foi um desafio delicado.
- **Configuração da documentação Swagger:** A versão do Spring Boot e `springdoc-openapi` exigiram ajustes específicos para funcionar corretamente, especialmente para servir um arquivo OpenAPI YAML customizado e configurar a UI do Swagger.

---

## 🚀 Possíveis Melhorias Futuras

- **Persistência e auditoria:** Implementar armazenamento das assinaturas, resultados de verificação e logs em banco de dados para rastreabilidade e análise futura.
- **Interface web amigável:** Criar uma interface gráfica para facilitar upload, assinatura e verificação de arquivos por usuários finais sem necessidade de ferramentas externas.
- **Configuração dinâmica da cadeia de certificados:** Permitir upload e atualização da cadeia de confiança via API, evitando dependência de arquivos estáticos na aplicação.
- **Suporte a múltiplos algoritmos e formatos:** Ampliar suporte para outros algoritmos criptográficos e formatos de assinatura para maior flexibilidade.
- **Segurança avançada:**  
  - **Vault:** Integrar HashiCorp Vault para gerenciamento seguro de credenciais, chaves privadas e senhas, evitando armazenamento direto no código ou arquivos de configuração.  
  - **Criptografia de dados sensíveis:** Implementar criptografia dos dados sensíveis em trânsito e em repouso para garantir confidencialidade, reduzindo riscos na comunicação da API.  
  - **Keycloak:** Utilizar Keycloak para autenticação e autorização robustas, com suporte a OAuth2/OpenID Connect e gerenciamento centralizado de usuários.  
- **Monitoramento e logging:**  
  - **Graylog:** Integrar Graylog ou solução similar para centralização, análise e monitoramento dos logs gerados pela aplicação, facilitando auditorias e diagnóstico de problemas.
- **Melhorias na arquitetura:**  
  - Implementar mecanismos de retry, circuit breaker e escalabilidade para garantir alta disponibilidade e resiliência da API em ambientes produtivos.


---




