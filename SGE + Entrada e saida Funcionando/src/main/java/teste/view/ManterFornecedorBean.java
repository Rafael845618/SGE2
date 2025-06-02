package teste.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teste.model.Fornecedor;
import teste.service.ManterFornecedorService;

@Named
@ViewScoped
public class ManterFornecedorBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ManterFornecedorBean.class);

	@Inject
	private ManterFornecedorService manterFornecedorService;

	private Fornecedor fornecedor = new Fornecedor();
	private List<Fornecedor> fornecedores = new ArrayList<>();

	@PostConstruct
	public void inicializar() {
		LOGGER.info("init");
		fornecedores = manterFornecedorService.buscarTodos();
		fornecedor = new Fornecedor();
	}

	public void salvar() {
		manterFornecedorService.salvar(fornecedor);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Fornecedor salvo com sucesso!", fornecedor.toString()));
		LOGGER.info("salvo: " + fornecedor);
		fornecedores = manterFornecedorService.buscarTodos();
	}

	public void excluir() {
		String nome = fornecedor.getNome();
		manterFornecedorService.excluir(fornecedor);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Fornecedor excluído", nome));

		fornecedores = manterFornecedorService.buscarTodos();
		fornecedor = new Fornecedor();
	}

	public void prepararExclusao(Fornecedor fornecedorSelecionado) {
		this.fornecedor = fornecedorSelecionado;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}
}