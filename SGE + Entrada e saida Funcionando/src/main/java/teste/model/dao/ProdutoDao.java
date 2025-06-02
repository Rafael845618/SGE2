package teste.model.dao;

import java.io.Serializable;
import java.util.List;


import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teste.model.Fabricante;
import teste.model.Fornecedor;
import teste.model.Produto;
import teste.service.ManterFabricanteService;
import teste.service.ManterFornecedorService;
import teste.util.jpa.Transactional;

public class ProdutoDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoDao.class);

	@Transactional
	public Produto salvar(Produto produto) throws PersistenceException {
		LOGGER.info("salvar DAO... produto = " + produto);

		try {
			if (produto.getId() == null) {
				manager.persist(produto); // Usar persist se o produto for novo
			} else {
				produto = manager.merge(produto); // Usar merge se o produto já existir
			}
			return produto;
		} catch (PersistenceException e) {	
			e.printStackTrace();
			throw e;
		}
	}

	public Produto buscarPeloCodigo(Long id) {
		return manager.find(Produto.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Produto> buscarTodos() {
		String query = "select a from Produto a";
		Query q = manager.createQuery(query);
		return q.getResultList();
	}

	@Transactional
	public void excluir(Produto produto) {
		LOGGER.info("excluir DAO... usuario = " + produto);
		try {
			Produto produtoGerenciado = manager.merge(produto);
			manager.remove(produtoGerenciado);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private ManterFabricanteService getManterFabricanteService() {
		return CDI.current().select(ManterFabricanteService.class).get();
	}

	public Fabricante getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		try {
			Long id = Long.valueOf(value);
			return getManterFabricanteService().buscarTodos().stream().filter(f -> f.getId().equals(id)).findFirst()
					.orElse(null);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Fabricante value) {
		if (value == null || value.getId() == null) {
			return "";
		}
		return String.valueOf(value.getId());
	}

	private ManterFornecedorService getManterFornecedorService() {
		return CDI.current().select(ManterFornecedorService.class).get();
	}

	public Fornecedor getAsObject1(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}
		try {
			Long id = Long.valueOf(value);
			return getManterFornecedorService().buscarTodos().stream().filter(f -> f.getId().equals(id)).findFirst()
					.orElse(null);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Fornecedor value) {
		if (value == null || value.getId() == null) {
			return "";
		}
		return String.valueOf(value.getId());
	}
}