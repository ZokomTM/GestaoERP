## Sobre o Sistema

O sistema em desenvolvimento é uma solução de gestão abrangente para estabelecimentos virtuais dentro de jogos. Ele oferece uma plataforma robusta que cobre uma ampla gama de funcionalidades essenciais para o gerenciamento eficiente e eficaz do seu estabelecimento. As principais áreas abrangidas pelo sistema incluem:

- **Vendas:** Controle completo das transações de vendas realizadas, com funcionalidades para registrar, acompanhar e analisar as vendas.
- **Compras:** Gerenciamento das aquisições de produtos e serviços, com capacidade para registrar e monitorar os detalhes das compras.
- **Registros de Despesas:** Registro e acompanhamento das despesas operacionais, proporcionando uma visão clara dos gastos do estabelecimento.
- **Gestão de Baú:** Administração e controle dos itens armazenados no baú do estabelecimento, incluindo a adição e remoção de itens.
- **Configurações de Webhook:** Configuração e gerenciamento de webhooks para integrar o sistema com outras aplicações e serviços.
- **Gestão de Funcionários:** Gerenciamento detalhado dos funcionários, incluindo registros, funções e acompanhamento de atividades.
- **Gestão de Produtos:** Controle dos produtos disponíveis no estabelecimento, com opções para adicionar, atualizar e remover produtos.
- **Gestão de Eventos:** Planejamento e organização de eventos realizados no estabelecimento, com recursos para agendamento e acompanhamento.
- **Gestão de Bar:** Administração das operações do bar, incluindo o gerenciamento de estoque, vendas e registro de atividades.

Este sistema foi projetado para proporcionar uma gestão completa e intuitiva, adaptando-se às necessidades específicas de estabelecimentos virtuais e garantindo uma operação fluida e eficiente.


## Endpoints da API já criados

### Cadastrar Empresa

- **Método:** POST
- **Endpoint:** `/cargos`
- **Descrição:** Cadastra uma nova empresa. O corpo da solicitação deve conter os dados da empresa a ser cadastrada.

### Alterar Empresa

- **Método:** PUT
- **Endpoint:** `/cargos/{id}`
- **Descrição:** Altera uma empresa existente identificada pelo ID. O corpo da solicitação deve conter os dados atualizados da empresa.

### Reativar Empresa

- **Método:** PATCH
- **Endpoint:** `/cargos/{id}`
- **Descrição:** Reativa uma empresa desativada identificada pelo ID. Remove a data de desativação da empresa.

### Efetuar Pagamento

- **Método:** PATCH
- **Endpoint:** `/cargos/pagamento/{id}`
- **Descrição:** Efetua o pagamento relacionado à empresa identificada pelo ID. Atualiza o status de pagamento da empresa.

### Remover Empresa por ID

- **Método:** DELETE
- **Endpoint:** `/cargos/{id}`
- **Descrição:** Remove (ou desativa) uma empresa identificada pelo ID. Define a data de desativação da empresa.

### Listar Todas as Empresas

- **Método:** GET
- **Endpoint:** `/cargos`
- **Descrição:** Retorna uma lista de todas as empresas cadastradas e ativas.

### Listar Empresa por ID

- **Método:** GET
- **Endpoint:** `/cargos/{id}`
- **Descrição:** Retorna os detalhes de uma empresa identificada pelo ID.

### Listar Empresas Desativadas

- **Método:** GET
- **Endpoint:** `/cargos/desativados`
- **Descrição:** Retorna uma lista de todas as empresas desativadas, ou seja, aquelas que possuem uma data de desativação não nula.


### Cadastrar Cargo

- **Método:** POST
- **Endpoint:** `/cargos`
- **Descrição:** Cadastra um novo cargo. O corpo da solicitação deve conter os dados do cargo a ser cadastrado.

### Alterar Cargo

- **Método:** PUT
- **Endpoint:** `/cargos/{id}`
- **Descrição:** Altera um cargo existente identificado pelo ID. O corpo da solicitação deve conter os dados atualizados do cargo.

### Reativar Cargo

- **Método:** PATCH
- **Endpoint:** `/cargos/{id}`
- **Descrição:** Reativa um cargo desativado identificado pelo ID. Remove a data de desativação do cargo.

### Remover Cargo por ID

- **Método:** DELETE
- **Endpoint:** `/cargos/{id}`
- **Descrição:** Remove (ou desativa) um cargo identificado pelo ID. Define a data de desativação do cargo.

### Listar Todos os Cargos

- **Método:** GET
- **Endpoint:** `/cargos`
- **Descrição:** Retorna uma lista de todos os cargos cadastrados.

### Listar Cargo por ID

- **Método:** GET
- **Endpoint:** `/cargos/{id}`
- **Descrição:** Retorna os detalhes de um cargo identificado pelo ID.

### Listar Cargos Desativados

- **Método:** GET
- **Endpoint:** `/cargos/desativados`
- **Descrição:** Retorna uma lista de todos os cargos desativados, ou seja, aqueles que possuem uma data de desativação não nula.


## Entidades do Sistema

### Empresa

- **ID:** `Long`
    - Identificador único da empresa.
- **Nome:** `String`
    - Nome da empresa.
- **Servidor:** `String`
    - Informações do servidor associado à empresa.
- **Responsável:** `Funcionario`
    - Funcionário responsável pela empresa (referência à entidade `Funcionario`).
- **Quantidade de Funcionários:** `Integer`
    - Número total de funcionários na empresa.
- **Último Pagamento:** `Date`
    - Data e hora do último pagamento realizado pela empresa.
- **Contratação:** `Date`
    - Data e hora da contratação da empresa.
- **Desativação:** `Date`
    - Data e hora da desativação da empresa, se aplicável.
- **Alteração:** `Date`
    - Data e hora da última alteração registrada na empresa.

### Cargo

- **ID:** `Long`
    - Identificador único do cargo.
- **Nome:** `String`
    - Nome do cargo.
- **Comissão:** `Integer`
    - Valor da comissão associada ao cargo.
- **Criação:** `Date`
    - Data e hora em que o cargo foi criado.
- **Desativação:** `Date`
    - Data e hora da desativação do cargo, se aplicável.
- **Alteração:** `Date`
    - Data e hora da última alteração registrada no cargo.
