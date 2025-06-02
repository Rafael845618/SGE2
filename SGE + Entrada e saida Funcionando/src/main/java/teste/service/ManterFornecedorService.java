package teste.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import teste.model.Fornecedor;
import teste.model.dao.FornecedorDao;
import teste.util.jpa.Transactional;

public class ManterFornecedorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FornecedorDao fornecedorDao;

	@Transactional
	public Fornecedor salvar(Fornecedor fornecedor) {
		return fornecedorDao.salvar(fornecedor);
	}

	@Transactional
	public void excluir(Fornecedor fornecedor) {
		fornecedorDao.excluir(fornecedor);
	}
	
	public List<Fornecedor> buscarTodos() {
		return fornecedorDao.buscarTodos();
	}
	public Fornecedor buscarPeloCodigo(Long id) {
	    return fornecedorDao.buscarPeloCodigo(id);
	}
}
