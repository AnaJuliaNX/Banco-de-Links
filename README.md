# README - Banco de Links

## Descrição do Projeto

O **Banco de Links** é uma aplicação em Java com interface gráfica que permite gerenciar links úteis. Cada link é categorizado em uma das seguintes categorias: **Estudos**, **Receitas**, **Curiosidades**, **Compras** e **Livros**. Os dados são salvos em um arquivo TXT, garantindo persistência entre sessões.

---

## Funcionalidades Principais

- **Adicionar Links**: Inclui novos links com nome, URL e categoria.
- **Editar Links**: Permite modificar os detalhes de um link existente.
- **Remover Links**: Exclui links selecionados.
- **Salvar e Carregar**: Armazena os dados em um arquivo TXT e os recupera ao iniciar a aplicação.

---

## Estrutura do Desenvolvimento

O desenvolvimento foi dividido em **cinco sprints principais** para organizar e implementar as funcionalidades.

### Sprint 1: Configuração Inicial e Estruturação do Projeto

**Objetivo**: Configurar o projeto, criar a estrutura básica e definir as classes principais.

- Criada a classe principal `BancoDeLinks` com:
  - Configuração da janela principal usando **Swing**.
  - Configuração da tabela para exibir links.
  - Criação de botões de ação (**Adicionar**, **Editar**, **Remover**).
- Implementada a enumeração `Categoria` para definir as categorias possíveis.
- Criada a classe `Link` para representar os dados do link (nome, URL e categoria).

---

### Sprint 2: Interface Gráfica e Interação Básica

**Objetivo**: Implementar a interface gráfica e a interação básica com o usuário.

- Adicionado painel de botões para gerenciar os links.
- Configurada a tabela para exibir os nomes dos links e suas categorias.
- Adicionado o método `abrirFormulario()` para exibir formulários de entrada (nome, URL, categoria).

---

### Sprint 3: Funcionalidades CRUD (Adicionar, Editar, Remover)

**Objetivo**: Implementar as operações básicas de CRUD.

- **Adicionar**:
  - Método `adicionarLink()` para incluir novos links à tabela e lista interna.
- **Editar**:
  - Método `editarLink()` para permitir a modificação de um link selecionado.
- **Remover**:
  - Método `removerLink()` para excluir um link da tabela e lista interna.
- Validado que os campos obrigatórios (**nome** e **URL**) não podem estar vazios.

---

### Sprint 4: Persistência de Dados

**Objetivo**: Implementar a funcionalidade de salvar e carregar os dados em um arquivo TXT.

- Criado o método `salvarLinks()`:
  - Escreve os dados dos links no arquivo `links.txt` em formato legível:
    ```
    Nome: Google, URL: https://google.com, Categoria: Estudos
    ```
- Criado o método `carregarLinks()`:
  - Lê os dados do arquivo `links.txt` ao iniciar a aplicação e os carrega na tabela.
- Adicionada a capacidade de recriar objetos `Link` a partir das linhas do arquivo.

---

### Sprint 5: Refinamento e Validação

**Objetivo**: Melhorar a experiência do usuário e refinar a aplicação.

- Adicionado tratamento de erros para operações de leitura e gravação no arquivo.
- Exibidas mensagens claras para o usuário em caso de erros (e.g., campos vazios, seleção inválida).
- Posicionada a janela no centro da tela para melhor usabilidade.
- Garantido que o programa funciona mesmo se o arquivo `links.txt` ainda não existir.

---

## Autor

Ana Julia Negri Xavier
