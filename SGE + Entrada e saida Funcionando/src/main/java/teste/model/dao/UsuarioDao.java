package teste.model.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import teste.model.Usuario;
import teste.util.jpa.Transactional;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioDao.class);

	@Transactional
	public Usuario salvar(Usuario usuario) throws PersistenceException {

		LOGGER.info("salvar DAO... usuario = " + usuario);

		try {
			return manager.merge(usuario);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Usuario buscarPeloCodigo(Long id) {
		return manager.find(Usuario.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> buscarTodos() {
		String query = "select a from Usuario a";
		Query q = manager.createQuery(query);
		return q.getResultList();
	}

	@Transactional
	public void excluir(Usuario usuario) {
		LOGGER.info("excluir DAO... usuario = " + usuario);
		try {
			Usuario usuarioGerenciado = manager.merge(usuario);
			manager.remove(usuarioGerenciado);
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Transactional
	public Usuario buscarPorLoginESenha(String nome, String senha) {
	    // Log para debug
	    System.out.println(">> buscarPorLoginESenha() chamado com nome = '" + nome + "', senha = '" + senha + "'");

	    try {
	        Usuario u = manager.createQuery(
	                "SELECT u FROM Usuario u WHERE u.nome = :nome AND u.senha = :senha", Usuario.class)
	            .setParameter("nome", nome)
	            .setParameter("senha", senha)
	            .getSingleResult();

	        System.out.println(">> Usuário encontrado: " + u);
	        return u;

	    } catch (NoResultException e) {
	        System.out.println(">> Nenhum usuário encontrado para essas credenciais.");
	        return null;
	    }
	}
}
