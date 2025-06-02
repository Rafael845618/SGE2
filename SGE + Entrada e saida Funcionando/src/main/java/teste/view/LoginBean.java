package teste.view;


import org.omnifaces.cdi.ViewScoped;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import teste.model.Usuario;
import teste.model.dao.UsuarioDao;

@Named
@ViewScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;

    private Usuario usuarioLogado;

    @Inject
    private UsuarioDao usuarioDao;

    // Método de login
    public String logar() {
        // Teste de injeção do DAO
        System.out.println(">> LoginBean.usuarioDao injetado? " + (usuarioDao != null));

        System.out.println(">> Tentando logar com: login = '" + login + "', senha = '" + senha + "'");
        Usuario usuario = usuarioDao.buscarPorLoginESenha(login, senha);

        if (usuario != null) {
            System.out.println(">> Login bem-sucedido para: " + usuario.getNome());
            this.usuarioLogado = usuario;
            return "/Opções/opcoes.xhtml?faces-redirect=true";
        } else {
            System.out.println(">> Login falhou!");
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login ou senha inválidos", null));
            return null;
        }
    }

    // Método de logout
    public String logout() {
        // Invalidar a sessão
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate(); // Invalida a sessão
        }

        // Limpar o usuário logado
        this.usuarioLogado = null;

        // Redirecionar para a página inicial (ou login)
        return "/index.xhtml?faces-redirect=true";
    }

    public boolean isLogado() {
        return usuarioLogado != null;
    }

    // Getters e Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }
}
