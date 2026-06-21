# 🏥 ClinicaGeral (CSplash) — API de Gestão e Agendamento Clínico Multidisciplinar

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/mysql-%2300f.svg?style=for-the-badge&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](https://img.shields.io/badge/-Swagger-%23C1E1C1?style=for-the-badge&logo=swagger&logoColor=black)

O **ClinicaGeral** é uma API REST robusta desenvolvida para gerenciar de forma dinâmica e segura o ecossistema de uma clínica de atendimento multidisciplinar. O sistema organiza o cadastro de especialidades médicas, vincula-as aos profissionais de saúde e gerencia com inteligência o fluxo completo de agendamento de consultas, tudo protegido por camadas modernas de segurança de software.

---

## 🎯 Diferenciais Técnicos Aplicados (Padrões de Mercado)

### 1. Segurança Avançada com Spring Security & JWT
A API conta com uma arquitetura de segurança **Stateless**. O sistema implementa autenticação via tokens **JWT (JSON Web Tokens)** criptografados com o algoritmo HMAC256. Uma vez autenticado, o usuário tem suas permissões interceptadas por um filtro customizado (`JwtFilter`) que valida sua sessão a cada requisição.

### 2. Controle de Acesso Baseado em Cargos (RBAC)
Os endpoints da aplicação são protegidos de forma granular usando controle de autoridades (`hasAuthority`):
* **`ADMIN`:** Controle total da clínica (pode cadastrar especialidades, gerenciar médicos e realizar exclusões).
* **`USER`:** Acesso focado no paciente e recepção (pode listar especialidades disponíveis e agendar/consultar marcações).

### 3. Relacionamentos Dinâmicos no Banco de Dados
Diferente de sistemas engessados onde as áreas médicas são fixas (Enums), este projeto conta com um cadastro dinâmico de **Especialidades** no MySQL. Isso permite que a administração expanda a clínica para novas áreas a qualquer momento, associando-as dinamicamente a múltiplos médicos.

### 4. Validação Estrita de Dados (Bean Validation) & CORS
Garantia do princípio *"nunca confie nos dados que vêm do cliente"*. Uso de anotações como `@NotBlank`, `@Email`, `@Pattern` (para validação estrita de CPF) e `@FutureOrPresent`. Além disso, a segurança global do **CORS** está configurada para permitir comunicações seguras e livres de bloqueios de cabeçalhos (`Authorization`) entre o front-end/ferramentas de teste e a API.

### 5. Regras de Negócio de Alta Performance
* **Regras de Agendamento:** Impede sobreposição de horários para médicos e pacientes, limitando os agendamentos ao horário comercial (08:00 às 18:00) de segunda a sexta-feira.
* **Paginação Nativa:** Todas as listagens principais utilizam `Pageable`, impedindo sobrecarga no banco de dados ao carregar dados em lotes e ordenados nativamente.
* **Tratamento Global de Erros:** Um manipulador de exceções (`@RestControllerAdvice`) intercepta falhas internas, erros de validação e violações de chaves estrangeiras, respondendo com payloads JSON limpos e padronizados.

---

## 🔒 Permissões dos Endpoints

| Método HTTP | Endpoint | Descrição | Nível de Acesso |
| :--- | :--- | :--- | :--- |
| **POST** | `/api/auth/cadastrar` | Criação de novas contas | Público |
| **POST** | `/api/auth/login` | Autenticação e geração do token JWT | Público |
| **POST** | `/api/especialidades` | Cadastro de novas especialidades médicas | `ADMIN` |
| **GET** | `/api/especialidades` | Listagem paginada de especialidades | `ADMIN`, `USER` |
| **POST** | `/api/medicos` | Cadastro de novos profissionais de saúde | `ADMIN` |
| **ANY** | `/api/consultas/**` | Agendamento e gestão de consultas | `ADMIN`, `USER` |

---

## 📖 Como Executar o Projeto

### Pré-requisitos
* Java 17+ instalado
* Maven instalado
* Servidor MySQL ativo

### 1. Clonar o Repositório
```bash
git clone [https://github.com/FranciscoSplash/cSplash.git](https://github.com/FranciscoSplash/cSplash.git)
cd cSplash

🗺️ Documentação da API (Swagger)
Com a aplicação rodando, você pode explorar, visualizar e testar todos os endpoints de forma interativa através da URL:
👉 http://localhost:8080/swagger-ui.html

💡 Nota de Teste: Para acessar as rotas protegidas no Swagger, realize o login, copie o token gerado, clique no botão Authorize (cadeado) no topo da página do Swagger, cole o token (sem aspas e sem a palavra "Bearer") e clique em autorizar.
