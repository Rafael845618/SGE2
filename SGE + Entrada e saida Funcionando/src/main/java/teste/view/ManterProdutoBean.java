package teste.view;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teste.model.Fabricante;
import teste.model.Fornecedor;
import teste.model.Produto;
import teste.service.ManterFabricanteService;
import teste.service.ManterFornecedorService;
import teste.service.ManterProdutoService;

@Named
@ViewScoped
public class ManterProdutoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(ManterProdutoBean.class);

    @Inject
    private ManterProdutoService manterProdutoService;
    @Inject
    private ManterFornecedorService manterFornecedorService;
    @Inject
    private ManterFabricanteService manterFabricanteService;

    private Produto produto;
    private List<Produto> produtos;
    private List<Fornecedor> fornecedores;
    private List<Fabricante> fabricantes;

    private Integer quantidadeMov;      // quantidade informada no diálogo
    private String tipoMov;             // "entrada" ou "saida"

    @PostConstruct
    public void inicializar() {
        LOGGER.info("Iniciando o bean");
        produto      = new Produto();
        produtos     = manterProdutoService.buscarTodos();
        fornecedores = manterFornecedorService.buscarTodos();
        fabricantes  = manterFabricanteService.buscarTodos();
    }

    // -----------------------------------------------------------------
    // CRUD BÁSICO
    // -----------------------------------------------------------------
    public void salvar() {
        if (produto != null && produto.getNome() != null && !produto.getNome().isEmpty()) {
            produto = manterProdutoService.salvar(produto);   // recebe o objeto salvo
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                                 "Produto salvo com sucesso!", produto.toString()));
            produtos = manterProdutoService.buscarTodos();
            produto  = new Produto();   // limpa formulário
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 "Erro ao salvar produto!",
                                 "Campos obrigatórios não preenchidos."));
        }
    }

    public void excluir() {
        manterProdutoService.excluir(produto);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO,
                             "Produto excluído", produto.getNome()));
        produtos = manterProdutoService.buscarTodos();
        produto  = new Produto();
    }

    public void prepararExclusao(Produto selecionado) {
        this.produto = selecionado;
    }

    // -----------------------------------------------------------------
    // MOVIMENTAÇÃO DE ESTOQUE
    // -----------------------------------------------------------------
    public void movimentar() {
        try {
            if (quantidadeMov == null || quantidadeMov <= 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                     "Quantidade inválida!", null));
                return;
            }

            if ("entrada".equals(tipoMov)) {

                produto = manterProdutoService.entrada(produto.getId(), quantidadeMov);
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                     "Entrada registrada!", null));

            } else if ("saida".equals(tipoMov)) {

                try {
                    produto = manterProdutoService.saida(produto.getId(), quantidadeMov);
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                         "Saída registrada!", null));
                } catch (IllegalArgumentException e) {
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                         e.getMessage(), null));
                    return;   // aborta fluxo
                }
            }

            // Aviso de estoque zerado
            if (produto.getQuantidade() != null && produto.getQuantidade() == 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                     "Estoque zerado para " + produto.getNome() + "!", null));
            }

            produtos = manterProdutoService.buscarTodos();  // tabela atualizada

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                 "Erro ao processar movimentação!", null));
            LOGGER.error("Erro na movimentação", e);
        } finally {
            // limpa campos do diálogo
            quantidadeMov = null;
            tipoMov       = "";
        }
    }

    // -----------------------------------------------------------------
    // Getters / Setters
    // -----------------------------------------------------------------
    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public List<Produto> getProdutos() { return produtos; }

    public List<Fornecedor> getFornecedores() { return fornecedores; }

    public List<Fabricante> getFabricantes() { return fabricantes; }

    public Integer getQuantidadeMov() { return quantidadeMov; }
    public void setQuantidadeMov(Integer quantidadeMov) { this.quantidadeMov = quantidadeMov; }

    public String getTipoMov() { return tipoMov; }
    public void setTipoMov(String tipoMov) { this.tipoMov = tipoMov; }
}
