# Projeto Mercado

## Objetivo do Projeto

A aplicação tem como intuito gerir um sistema de carrinho de compras a partir dos usuários

### Funcionalidades do Projeto

## 1 - Autenticação

### As princiapis rotas de uso devem ser protegidas por autenticação.

Para este projeto foi escolhida a autenticação por token, onde, uma vez que o token de autenticação do usuário é passado
pela rota, o mesmo é validado, e, uma vez validado, as informações do usuário ficam disponiveis para concluir seus
passos dentro da aplicação

    ROTAS DA AUTENTICAÇÃO
    -> POST market/user/create-session - Criar token de autenticação do usuário
        Request Body:
            userEmail: string, minima de 2 caracteres e um email válido
            userPassword: string, com pelo menos 6 caracteres
        Response Body:
            authToken: token criado com as informações do usuário

    -> GET market/user/me - Valida o token e retorna as informações do usuário logado
        Response Body:
            userId: UUID do usuário criado
            name: nome do usuário cadastrado
            email: email cadastrado pelo usuário

## 2 - Usuário

### Cadastro de Usuários

A aplicação deve possuir um cadastro de usuários para vincular a criação de seus carrinhos de compras

    ROTA DE CRIAÇÃO DE USUÁRIO
    -> POST market/user/create - Cria um usuário com as informações recebidas
        Request Body:
            email: string, minima de 2 caracteres e um email válido
            name: string, minima de 2 caracteres
            password: string, com pelo menos 6 caracteres
        Response Body:
            userId: UUID do usuário criado
            name: nome do usuário cadastrado
            email: email cadastrado pelo usuário            

## 4 - Produtos

### Criação de produtos

O usuário, logado, deve ser capaz de criar produtos, informando, obrigatóriamente: SKU, nome do produto, quantidade de
produtos em estoque, preço do produto

    ROTA DE CRIAÇÃO DE PRODUTO
    -> POST market/product/create - Criação de produtos com informações obrigatórias
        Request Body:
            sku: string, mínimo de 3 caracteres
            name: string, mínimo de 6 caracteres
            price: BigDecimal, não pode ser menor que 1
            amount: número, não pode ser menor que 0

        Response Body:
            id: UUID do produto,
            sku: String com a skul do produto,
            name: String com o nome do produto
            price: BigDecima com o valor unitário do produto
            amount: Long com a quantidade de produtos em estoque

### Atualização de produtos

O usuário, logado, deve ser capaz de editor os produtos já cadastrados, porém sem a possibilidade de alterar o SKU do
produto

    ROTA DE ATUALIZAÇÃO DE PRODUTOS
    -> PUT market/product/update/{uui do produto} - Atualizção de produtos
        Request Body:
            name: string, mínimo de 6 caracteres
            price: BigDecimal, não pode ser menor que 1
            amount: número, não pode ser menor que 0
        Response Body:
            id: UUID do produto,
            sku: String com a skul do produto,
            name: String com o nome do produto
            price: BigDecima com o valor unitário do produto
            amount: Long com a quantidade de produtos em estoque

### Detalhe de produto

O usuário, logado, deve ser capaz de ver detalhes de cada produto de forma individual

    ROTA DE CONSULTA DE DETALHES DO PRODUTO
    -> GET maket/product/{uuid do produto}
         Response Body:
            id: UUID do produto,
            sku: String com a skul do produto,
            name: String com o nome do produto
            price: BigDecima com o valor unitário do produto
            amount: Long com a quantidade de produtos em estoque

### Listagem de Produtos

O usuário, logado, deve ser capaz de ver todos os produtos cadastrados de forma listada e com a possibilidade de
utilizar um filtro

    ROTA DE CONSULTA DE DETALHES DO PRODUTO
    -> GET maket/product/list
        Request Params:
            page: inteiro informando qual a página da pesquisa
            size: inteiro informando a quantidade de itens por página
            sku: string, opicional, para filtrar produtos por sku
            name: string, opicional, para filtrar produtos por nome
            lowerPrice: bigdecimal, opicional, para filtar produtos a cima do valor informado
            higherPrice bigdecimal, opicional, para filtrar produtos a baixo do valor informado
        Response Body:
            content: lista de produtos
                 id: UUID do produto,
                 sku: String com a skul do produto,
                 name: String com o nome do produto
                 price: BigDecima com o valor unitário do produto
                 amount: Long com a quantidade de produtos em estoque

## 4 - Carrinho de Compras

### Criação de carrinho de compras

O usuário, logado, deve poder criar um carrinho de compras vazio para, posteriormente, adicionar os produtos ao carrinho

    ROTA DE CRIAÇÃO DE CARRINHO
    -> POST market/basket/create
        Response Body:
            basketId: UUID identificador do carrinho
            totalValue: BigDecimal com o valor total dos itens da cesta
            products: list de produtos no carrinho
                 id: UUID do produto,
                 sku: String com a skul do produto,
                 name: String com o nome do produto
                 price: BigDecima com o valor unitário do produto
                 amount: Long com a quantidade de produtos no carrinho

### Listar Carrinhos do usuário

O usuário, logado, deve ser capaz de listar seus carrinhos de compras

    ROTA DE EDIÇÃO DE ITENS DO CARRINHO
    -> POST market/basket/
         Response Body: lista com os carrinhos do usuário
            basketId: UUID identificador do carrinho
            totalValue: BigDecimal com o valor total dos itens da cesta
            checkout:
                paymentMethod: String,informano o método de pagamento utilizado no momento do checkou
                checkoutDate: TimeStamp, informando a data e hora do checkout
            products: list de produtos no carrinho
                 id: UUID do produto,
                 sku: String com a skul do produto,
                 name: String com o nome do produto
                 price: BigDecima com o valor unitário do produto
                 amount: Long com a quantidade de produtos no carrinho

### Consultar Único Carrinho de compras

O usuário, logado, deve ser capaz de visualizar um carrinho específico

    ROTA DE EDIÇÃO DE ITENS DO CARRINHO
    -> POST market/basket/{UUID do carrinho}
         Response Body:
            basketId: UUID identificador do carrinho
            totalValue: BigDecimal com o valor total dos itens da cesta
            checkout:
                paymentMethod: String,informano o método de pagamento utilizado no momento do checkou
                checkoutDate: TimeStamp, informando a data e hora do checkout
            products: list de produtos no carrinho
                 id: UUID do produto,
                 sku: String com a skul do produto,
                 name: String com o nome do produto
                 price: BigDecima com o valor unitário do produto
                 amount: Long com a quantidade de produtos no carrinho

### Edição dos itens do carrinho de compras

O usuário, logado, deve ser capaz de definir a lista de produtos em determinado carrinho de compras, a cada alteração o
valor total do carrinho é recalculado

    ROTA DE EDIÇÃO DE ITENS DO CARRINHO
    -> POST market/basket/{UUID DO CARRINHO}/add-products
        Request Body: lista de itens a serem adicionados no carrinho
            productId: UUID do produto a ser adicionado no carrinho
            productAmount: Long informando a quantidade de itens a serem adicionados

        Response Body:
            basketId: UUID identificador do carrinho
            totalValue: BigDecimal com o valor total dos itens da cesta
            products: list de produtos no carrinho
                 id: UUID do produto,
                 sku: String com a skul do produto,
                 name: String com o nome do produto
                 price: BigDecima com o valor unitário do produto
                 amount: Long com a quantidade de produtos no carrinho

### Checkout do carrinho de compras

O usuário, logado, deve ser capaz de efetuar o checkou de seu carrinho de compras, no momento do checkou o carrinho é
fechado para a edição e a quantidade dos itens informados é retirado do estoque

    ROTA DE CHECKOUT DO CARRINHO
    -> POST market/basket/{UUID DO CARRINHO}/checkout
        Request Body: 
            paymentMethod: String informando o método de pagamento utilizado, deve ser um dos :
                            - CREDIT_CARD
                            - DEBIT_CARD
                            - PAYPAL
                            - CASH
                            - BILLET
        Response Body:
            basketId: UUID identificador do carrinho
            totalValue: BigDecimal com o valor total dos itens da cesta
            checkout:
                paymentMethod: String,informano o método de pagamento utilizado no momento do checkou
                checkoutDate: TimeStamp, informando a data e hora do checkout
            products: list de produtos no carrinho
                 id: UUID do produto,
                 sku: String com a skul do produto,
                 name: String com o nome do produto
                 price: BigDecima com o valor unitário do produto
                 amount: Long com a quantidade de produtos no carrinho

## Sobre a Aplicação

### Tecnologias

- Java 17
- SpringBoot Framework
- H2 - Banco de dados em memória, o mesmo é instanciado toda vez que a aplicação é iniciada, desta forma, falicitando o
  uso
- Flyway - Ferramenta de versionamento de banco, a qual cria as tabelas sempre o que o banco de dados é inicializado
- Groovy - Linguagem de Programação baseada e compativel com Java, voltada para criação de scripts, a mesma pode ser
  utilizada para rodar o script de instanciar o usuário base na aplicação

### Como utilizar

- Certificar de possuir o JAVA 17 instalado no equipamento
- Certificar de possuir o Groovy instalado no equipamento

A aplicação conta com o uso do banco de dados H2, o que permite que o mesmo rode em memória e sem a necessidade de um
banco de dados instalado no equipamento.

A aplicação conta com um script para criar o usuário Admin na aplicaçao, para utiliza-lo, acesse a pasta root do projeto
pelo cmd e rode o comando

        groovy seed_data.groovy

Ele irá criar o usuário e retornar um token de autenticação para o mesmo