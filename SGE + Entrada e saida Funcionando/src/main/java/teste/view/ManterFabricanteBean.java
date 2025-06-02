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

import teste.model.Fabricante;
import teste.service.ManterFabricanteService;

@Named
@ViewScoped
public class ManterFabricanteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ManterFornecedorBean.class);

	@Inject
	private ManterFabricanteService manterFabricanteService;

	private Fabricante fabricante = new Fabricante();
	private List<Fabricante> fabricantes = new ArrayList<>();

	@PostConstruct
	public void inicializar() {
		LOGGER.info("init");
		fabricantes = manterFabricanteService.buscarTodos();
		fabricante = new Fabricante();
	}

	public void salvar() {
		manterFabricanteService.salvar(fabricante);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Fornecedor salvo com sucesso!", fabricante.toString()));
		LOGGER.info("salvo: " + fabricante);
		fabricantes = manterFabricanteService.buscarTodos();
	}

	public void excluir() {
		String nome = fabricante.getNome();
		manterFabricanteService.excluir(fabricante);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Fabricante excluído", nome));

		fabricantes = manterFabricanteService.buscarTodos();
		fabricante = new Fabricante();
	}

	public void prepararExclusao(Fabricante fabricanteSelecionado) {
		this.fabricante = fabricanteSelecionado;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}
}