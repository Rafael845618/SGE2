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

import teste.model.Usuario;
import teste.service.ManterUsuarioService;

@Named
@ViewScoped
public class ManterUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ManterUsuarioBean.class);

	@Inject
	private ManterUsuarioService manterUsuarioService;

	private Usuario usuario = new Usuario();
	private List<Usuario> usuarios = new ArrayList<>();

	@PostConstruct
	public void inicializar() {
		LOGGER.info("init");
		usuarios = manterUsuarioService.buscarTodos();
		usuario = new Usuario();
	}

	public void salvar() {
		manterUsuarioService.salvar(usuario);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário salvo com sucesso!", usuario.toString()));
		LOGGER.info("salvo: " + usuario);
		usuarios = manterUsuarioService.buscarTodos();
	}

	public void excluir() {
		String nome = usuario.getNome();
		manterUsuarioService.excluir(usuario);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário excluído", nome));

		usuarios = manterUsuarioService.buscarTodos();
		usuario = new Usuario();
	}

	public void prepararExclusao(Usuario usuarioSelecionado) {
		this.usuario = usuarioSelecionado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
