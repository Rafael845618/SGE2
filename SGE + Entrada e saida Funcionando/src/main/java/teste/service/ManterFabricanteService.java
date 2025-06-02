package teste.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import teste.model.Fabricante;
import teste.model.dao.FabricanteDao;
import teste.util.jpa.Transactional;

public class ManterFabricanteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FabricanteDao fabricanteDao;

	@Transactional
	public Fabricante salvar(Fabricante fabricante) {
		return fabricanteDao.salvar(fabricante);
	}

	@Transactional
	public void excluir(Fabricante fabricante) {
		fabricanteDao.excluir(fabricante);
	}
	
	public List<Fabricante> buscarTodos() {
		return fabricanteDao.buscarTodos();
	}
	public Fabricante buscarPeloCodigo(Long id) {
	    return fabricanteDao.buscarPeloCodigo(id);
	}
}
