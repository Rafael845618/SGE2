package teste.model.dao;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teste.model.Fornecedor;
import teste.util.jpa.Transactional;

public class FornecedorDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private static final Logger LOGGER = LoggerFactory.getLogger(FornecedorDao.class);

	@Transactional
	public Fornecedor salvar(Fornecedor fornecedor) throws PersistenceException {

		LOGGER.info("salvar DAO... fornecedor = " + fornecedor);

		try {
			return manager.merge(fornecedor);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Fornecedor buscarPeloCodigo(Long id) {
		return manager.find(Fornecedor.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Fornecedor> buscarTodos() {
		String query = "select a from Fornecedor a";
		Query q = manager.createQuery(query);
		return q.getResultList();
	}

	@Transactional
	public void excluir(Fornecedor fornecedor) {
		LOGGER.info("excluir DAO... fornecedor = " + fornecedor);
		try {
			Fornecedor fornecedorGerenciado = manager.merge(fornecedor);
			manager.remove(fornecedorGerenciado);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
}