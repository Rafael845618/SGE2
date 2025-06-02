package teste.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import teste.model.Produto;
import teste.model.dao.ProdutoDao;
import teste.util.jpa.Transactional;

public class ManterProdutoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDao produtoDao;

	@Transactional
	public Produto salvar(Produto produto) {
		if (produto.getQuantidade() == null) {
			produto.setQuantidade(0);
		}
		return produtoDao.salvar(produto);
	}

	@Transactional
	public void excluir(Produto produto) {
		produtoDao.excluir(produto);
	}

	public List<Produto> buscarTodos() {
		return produtoDao.buscarTodos();
	}

	@Transactional
	public Produto entrada(Long id, int quantidade) {
	    Produto prod = produtoDao.buscarPeloCodigo(id);   // leitura fresca
	    int atual = prod.getQuantidade() == null ? 0 : prod.getQuantidade();
	    prod.setQuantidade(atual + quantidade);
	    return produtoDao.salvar(prod);
	}

	@Transactional
	public Produto saida(Long id, int quantidade) {
	    Produto prod = produtoDao.buscarPeloCodigo(id);
	    int atual = prod.getQuantidade() == null ? 0 : prod.getQuantidade();
	    if (quantidade > atual) {
	        throw new IllegalArgumentException("Quantidade em estoque insuficiente");
	    }
	    prod.setQuantidade(atual - quantidade);
	    return produtoDao.salvar(prod);
	}

}
