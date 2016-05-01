package controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.dao.ContatoGrupoDAO;
import model.dao.GrupoDAO;
import model.pojo.ContatoGrupo;
import model.pojo.Grupos;

@ManagedBean(name="grupoController")
@SessionScoped
public class GrupoController implements Serializable{
    private Grupos grupo;

    public GrupoController() {
        grupo = new Grupos();
    }
    
    public List<Grupos> getGrupos() {
        return new GrupoDAO().listaGrupos();
    }

    public Grupos getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupos grupo) {
        this.grupo = grupo;
    }
    
    public List<ContatoGrupo> contatosNoGrupo(Grupos grupo) {
        return new ContatoGrupoDAO().listaContatoNoGrupo(grupo);
    }
    
    public String prepararCriarGrupo(){
        System.out.println("Preparando para criar grupo");
        
        grupo = new Grupos();
        
        return "/criarGrupo.xhtml";
    }
    
    public String criarGrupo(){
        System.out.println("Criando Grupo "+grupo.getNome());
        
        GrupoDAO gDAO = new GrupoDAO();
        gDAO.criar(grupo);
        
        return "/visualizarGrupos.xhtml";
    }
    
    public String prepararAlterarGrupo(Grupos grupo){
        this.grupo = grupo;
        System.out.println("Mandando " +this.grupo.getNome()+" para ser alterado" +" ID:"+ this.grupo.getId());
        return "/editarGrupo.xhtml";
    }
    
    public void alterarGrupo(){
        System.out.println("Recebendo alterações para " + this.grupo.getNome() +" ID:"+ this.grupo.getId());
        GrupoDAO gDao = new GrupoDAO();
        gDao.update(this.grupo);
        
        String uri = "/faces/visualizarGrupos.xhtml";
        
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + uri);
        } catch (IOException ex) {
            Logger.getLogger(ContatoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void excluirGrupo(Grupos g){
        System.out.println("Mandando " +g.getNome() +" para ser excluido - " +"ID:"+ g.getId());
        GrupoDAO gDao = new GrupoDAO();
        gDao.remove(g);
        
        String uri = "/faces/visualizarGrupos.xhtml";
        
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + uri);
        } catch (IOException ex) {
            Logger.getLogger(ContatoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
