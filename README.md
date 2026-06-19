# 🏥 ClinicaGeral (CSplash) - API de Gestão e Agendamento Clínico Multidisciplinar

O **ClinicaGeral** é uma API REST robusta desenvolvida em Java com Spring Boot para gerenciar o ecossistema de uma clínica de atendimento geral. O sistema organiza de forma dinâmica o cadastro de especialidades médicas (como Fisioterapia, Cardiologia, Clínica Médica, etc.), vincula essas especialidades aos médicos e gerencia o fluxo completo de agendamento de consultas para os pacientes.

---

## 🛠️ Tecnologias e Ferramentas Utilizadas

* **Linguagem:** Java 17
* **Framework Principal:** Spring Boot 3.x
* **Persistência de Dados:** Spring Data JPA
* **Banco de Dados:** MySQL
* **Validação de Dados:** Jakarta Bean Validation
* **Ferramenta de Testes/Documentação:** Swagger UI (OpenAPI)
* **Gerenciador de Dependências:** Maven

---

## 🎯 Diferenciais Técnicos Aplicados (Padrões de Mercado)

Este projeto foi construído seguindo as melhores práticas de desenvolvimento backend exigidas pelo mercado de trabalho:

### 1. Relacionamentos Dinâmicos no Banco de Dados
Diferente de sistemas engessados, as especialidades da clínica não são fixas (Enums). O projeto conta com um cadastro dinâmico de **Especialidades**, permitindo que a administração da clínica adicione novas áreas de atendimento a qualquer momento, associando-as diretamente ao cadastro de cada **Médico**.

### 2. Arquitetura Defensiva e Tratamento Global de Erros
Implementação de um `GlobalExceptionHandler` utilizando `@RestControllerAdvice`. A API foi blindada para que erros internos do Java ou do Banco de Dados nunca vazem para o cliente. O sistema intercepta falhas e responde com status HTTP semanticamente corretos e payloads JSON limpos.

### 3. Validação e Sanitização de Dados (Bean Validation)
Garantia do princípio *"nunca confie nos dados que vêm do cliente"*. Uso de anotações como `@NotBlank`, `@Email`, `@Pattern` (para validação estrita de 11 dígitos do CPF) e `@FutureOrPresent` nos Records/DTOs. Se os dados estiverem inconsistentes, a requisição é barrada na porta de entrada (Status 400).

### 4. Regras de Negócio Avançadas de Agendamento
O sistema possui inteligência para validar as seguintes regras no `ConsultaService`:
* Impede que um médico ou paciente tenha mais de uma consulta agendada no mesmo horário.
* Restringe agendamentos apenas para o horário comercial da clínica (**08:00 às 18:00**).
* Bloqueia marcações de consultas aos finais de semana (**Sábados e Domingos**).

### 5. Paginação e Ordenação de Alta Performance
Todas as listagens principais da API (Consultas, Especialidades) utilizam `Pageable`. Isso impede a sobrecarga do banco de dados MySQL, permitindo carregar os dados em lotes (ex: 5 elementos por página) ordenados de forma nativa (ex: ordem alfabética por nome da especialidade).

---

## 🚧 Próximos Passos (Roadmap do Projeto)

O projeto está em constante evolução. As próximas implementações programadas são:
* [ ] **Segurança da Informação:** Integração com o Spring Security.
* [ ] **Autenticação e Autorização:** Implementação de login seguro com Tokens JWT.
* [ ] **Controle de Perfis:** Restrição de rotas por nível de acesso administrativo e usuário (`ADMIN`, `MEDICO` e `PACIENTE`).

---

## 🚀 Como Executar o Projeto

1. Clone o repositório:
   ```bash
   git clone [https://github.com/SEU_USUARIO/clinica-geral-csplash.git](https://github.com/SEU_USUARIO/clinica-geral-csplash.git)
   Configure a conexão com o seu banco de dados MySQL no arquivo src/main/resources/application.properties.

Execute a aplicação através da sua IDE ou via terminal com o comando:

Bash
mvn spring-boot:run
Acesse a documentação das rotas e teste os endpoints pelo Swagger UI em: http://localhost:8080/swagger-ui.html
