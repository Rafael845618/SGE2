package teste.model.dao;

import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teste.model.Fabricante;
import teste.util.jpa.Transactional;

public class FabricanteDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private static final Logger LOGGER = LoggerFactory.getLogger(FabricanteDao.class);

	@Transactional
	public Fabricante salvar(Fabricante fabricante) throws PersistenceException {

		LOGGER.info("salvar DAO... fabricante = " + fabricante);

		try {
			return manager.merge(fabricante);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Fabricante buscarPeloCodigo(Long id) {
		return manager.find(Fabricante.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Fabricante> buscarTodos() {
		String query = "select a from Fabricante a";
		Query q = manager.createQuery(query);
		return q.getResultList();
	}

	@Transactional
	public void excluir(Fabricante fabricante) {
		LOGGER.info("excluir DAO... fabricante = " + fabricante);
		try {
			Fabricante fabricanteGerenciado = manager.merge(fabricante);
			manager.remove(fabricanteGerenciado);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
}