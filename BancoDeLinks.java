// Importações necessárias
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Categorias disponíveis para os links
enum Categoria {
    ESTUDOS, RECEITAS, CURIOSIDADES, COMPRAS, LIVROS
}

// Classe com os campos para os links 
class Link {
    private String nome;
    private String url;
    private Categoria categoria;

    // Implementação do construtor
    public Link(String nome, String url, Categoria categoria) {
        this.nome = nome;
        this.url = url;
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    // Sobreescrita do método toString para salvar os links no arquivo
    @Override
    public String toString() {
        return "Nome: " + nome + ", URL: " + url + ", Categoria: " + categoria;
    }

    // Método para criar um objeto Link a partir de uma string
    public static Link fromString(String line) {
        String[] parts = line.split(", ");
        String nome = parts[0].split(": ")[1];
        String url = parts[1].split(": ")[1];
        Categoria categoria = Categoria.valueOf(parts[2].split(": ")[1]);
        return new Link(nome, url, categoria);
    }
}

// Classe principal com a interface gráfica
public class BancoDeLinks extends JFrame {
    private final List<Link> links = new ArrayList<>();
    private final DefaultTableModel tableModel;
    private final String FILE_NAME = "links.txt";

    public BancoDeLinks() {
        // Configurações da janela principal
        setTitle("Gerenciador de Links Úteis");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabela para exibir os links
        String[] colunas = {"Nome", "Categoria"};
        tableModel = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabela);

        // Painel com os botões de ação
        JPanel painelBotoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnRemover);

        // Adicionando componentes à janela
        add(scrollPane, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);

        // Ação do botão "Adicionar"
        btnAdicionar.addActionListener(e -> adicionarLink());

        // Ação do botão "Editar"
        btnEditar.addActionListener(e -> editarLink(tabela.getSelectedRow()));

        // Ação do botão "Remover"
        btnRemover.addActionListener(e -> removerLink(tabela.getSelectedRow()));

        // Carregar links ao iniciar o programa
        carregarLinks();

        // Exibir a janela
        setLocationRelativeTo(null);
        setVisible(true);
    }

        // Métodos para adicionar links
    private void adicionarLink() {
        Link link = abrirFormulario(null);
        if (link != null) {
            links.add(link);
            tableModel.addRow(new Object[]{link.getNome(), link.getCategoria()});
            salvarLinks();
        }
    }

    // Métodos para editar links
    private void editarLink(int linhaSelecionada) {
        if (linhaSelecionada >= 0) {
            Link linkAtual = links.get(linhaSelecionada);
            Link linkEditado = abrirFormulario(linkAtual);
            if (linkEditado != null) {
                links.set(linhaSelecionada, linkEditado);
                tableModel.setValueAt(linkEditado.getNome(), linhaSelecionada, 0);
                tableModel.setValueAt(linkEditado.getCategoria(), linhaSelecionada, 1);
                salvarLinks();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um link para editar.");
        }
    }

    // Métodos para remover links
    private void removerLink(int linhaSelecionada) {
        if (linhaSelecionada >= 0) {
            links.remove(linhaSelecionada);
            tableModel.removeRow(linhaSelecionada);
            salvarLinks();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um link para remover.");
        }
    }

    // Método para abrir o formulário de adição ou edição de links
    private Link abrirFormulario(Link link) {
        JTextField campoNome = new JTextField(link != null ? link.getNome() : "");
        JTextField campoUrl = new JTextField(link != null ? link.getUrl() : "");
        JComboBox<Categoria> comboCategoria = new JComboBox<>(Categoria.values());
        if (link != null) {
            comboCategoria.setSelectedItem(link.getCategoria());
        }

        // Exibir o formulário
        Object[] message = {
                "Nome:", campoNome,
                "URL:", campoUrl,
                "Categoria:", comboCategoria
        };

        // Opções de confirmação
        int option = JOptionPane.showConfirmDialog(
                this,
                message,
                link == null ? "Adicionar Link" : "Editar Link",
                JOptionPane.OK_CANCEL_OPTION
        );

        // Verificar se o usuário confirmou a ação
        if (option == JOptionPane.OK_OPTION) {
            String nome = campoNome.getText().trim();
            String url = campoUrl.getText().trim();
            Categoria categoria = (Categoria) comboCategoria.getSelectedItem();

            // Verificar se os campos obrigatórios foram preenchidos
            if (nome.isEmpty() || url.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e URL não podem estar vazios.");
                return null;
            }

            return new Link(nome, url, categoria);
        }
        return null;
    }

    // Métodos para salvar e carregar os links
    private void salvarLinks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Link link : links) {
                writer.write(link.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os links: " + e.getMessage());
        }
    }

    // Método para carregar os links
    private void carregarLinks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Link link = Link.fromString(line);
                links.add(link);
                tableModel.addRow(new Object[]{link.getNome(), link.getCategoria()});
            }
        } catch (FileNotFoundException e) {
            // Arquivo não encontrado, inicializar vazio
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os links: " + e.getMessage());
        }
    }

    // Método principal para iniciar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(BancoDeLinks::new);
    }
}