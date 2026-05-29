# 🏦 Digital Wallet API (Carteira Digital)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2-Database-blue?style=for-the-badge)

## 🎯 Por que este projeto existe?

### O Problema e a Dor Resolvida
A construção de sistemas financeiros exige um rigor técnico extremo. Vazamento de dados, lentidão na busca de históricos e transferências sem validação de saldo são falhas que podem custar milhões a uma empresa. Desenvolvedores frequentemente lutam para criar APIs transacionais que sejam seguras, consistentes e escaláveis desde o primeiro dia.

Esta API foi desenvolvida para resolver essa dor. Ela abstrai a complexidade de um motor transacional, entregando uma solução de **Carteira Digital** blindada. O projeto utiliza o padrão DTO (Data Transfer Object) para evitar o vazamento de dados sensíveis da base de dados para a web, e implementa paginação nativa no nível do banco de dados para garantir que a consulta de extratos não sobrecarregue o servidor, mesmo com milhões de registros.

### Para quem é útil?
* **Empresas e Startups** que precisam integrar um sistema de contas pré-pagas, *cashback* ou carteiras virtuais em suas plataformas.
* **Desenvolvedores** que buscam um modelo de referência de arquitetura em camadas (Controller, Service, Repository) usando o ecossistema Spring Boot.

## 🔄 O Fluxo Principal (Como funciona?)

1. O usuário tem seu perfil registrado e uma carteira digital zerada vinculada obrigatoriamente a ele.
2. Com a carteira ativa, o dinheiro circula através de endpoints protegidos por regras de negócio (depósitos, saques e transferências com validação de saldo).
3. Todas as operações formam uma trilha de auditoria, consultada através de um extrato paginado de alta performance.

---

## ✨ Funcionalidades Principais

* **Criação de Carteira:** Geração de carteiras atreladas a usuários existentes.
* **Depósitos e Saques:** Movimentação de entrada e saída com validação rigorosa de fundos.
* **Transferência P2P:** Envio de valores entre carteiras diferentes, bloqueando auto-transferências e saldos negativos.
* **Consulta de Saldo Blindada:** Endpoint otimizado que retorna apenas os dados essenciais da conta via DTO.
* **Extrato Inteligente:** Busca histórica de transações (entradas e saídas) com suporte nativo a paginação (`page` e `size`).

---

## 🛠️ Stack de Tecnologias

* **Linguagem:** Java (Orientação a Objetos moderna)
* **Framework:** Spring Boot (Web, Data JPA)
* **Banco de Dados:** H2 Database (In-Memory para desenvolvimento rápido e testes)
* **Gerenciador de Dependências:** Maven
* **Boas Práticas:** Padrão DTO, Tratamento Transacional (`@Transactional`), Injeção de Dependências, RESTful.

---

## 🚀 Como Rodar Localmente

Siga o passo a passo abaixo para executar a API na sua máquina e testar os fluxos.

### 1. Clonar e Iniciar
```bash
# Clone o repositório
git clone https://github.com/Francieverton/Carteira-Digital.git

# Entre na pasta do projeto
cd Carteira-Digital
```
Abra o projeto na sua IDE favorita (IntelliJ, VS Code, Eclipse) e execute a classe principal `DigitalWalletApplication.java`. A aplicação iniciará na porta **8080**.

### 2. Configurando o Banco de Dados (Pré-requisito Crítico)
Como a nossa regra de negócio impede a criação de carteiras "órfãs", **você precisa criar um Usuário no banco de dados primeiro**.

1. Com a aplicação rodando, acesse no seu navegador: `http://localhost:8080/h2-console`
2. No campo **JDBC URL**, garanta que o valor seja exatamente: `jdbc:h2:mem:wallet`
3. Clique em **Connect** (deixe a senha em branco).
4. No painel de SQL, execute o comando abaixo para criar seu primeiro usuário:

```sql
INSERT INTO usuario (nome, email, senha) VALUES ('Desenvolvedor', 'dev@email.com', '123456');
```

### 3. Testando as Rotas (Via Postman/Insomnia)

Com o usuário de **ID 1** criado, você pode executar o fluxo completo:

**A. Criar a Carteira:**
* **POST** `http://localhost:8080/carteiras/usuario/1`
> *(Retornará a carteira gerada com saldo 0.0).*

**B. Fazer um Depósito:**
* **POST** `http://localhost:8080/carteiras/1/deposito`
* **Body (JSON):**
```json
{ 
  "valor": 150.00 
}
```

**C. Consultar o Extrato Paginado:**
* **GET** `http://localhost:8080/carteiras/1/extrato?page=0&size=10`
> *(Retornará todas as movimentações da carteira, fatiadas pela página e tamanho solicitados).*

**D. Fazer um Saque:**
* **POST** `http://localhost:8080/carteiras/1/saque`
* **Body (JSON):**
```json
{ 
  "valor": 50.00 
}
```
> *(Dica: Tente sacar um valor maior que o seu saldo para ver a regra de negócio de bloqueio em ação!)*

**E. Realizar uma Transferência (P2P):**
Para testar a transferência, crie um segundo usuário no banco (ID 2) e gere uma carteira para ele usando a rota **A**. Depois, use a carteira 1 para enviar dinheiro para a carteira 2:

* **POST** `http://localhost:8080/carteiras/1/transferencia`
* **Body (JSON):**
```json
{ 
  "carteiraDestino": 2,
  "valor": 50.00 
}
```
> *(Nota: Verifique se o nome do campo `carteiraDestino` corresponde exatamente ao nome que você definiu no seu DTO de transferência).*