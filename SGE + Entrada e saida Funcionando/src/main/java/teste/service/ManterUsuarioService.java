package teste.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import teste.model.Usuario;
import teste.model.dao.UsuarioDao;
import teste.util.jpa.Transactional;

public class ManterUsuarioService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioDao usuarioDao;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioDao.salvar(usuario);
	}

	@Transactional
	public void excluir(Usuario usuario) {
		usuarioDao.excluir(usuario);
	}

	public List<Usuario> buscarTodos() {
		return usuarioDao.buscarTodos();
	}
}
