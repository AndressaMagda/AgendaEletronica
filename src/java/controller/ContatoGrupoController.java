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
import model.dao.ContatoDAO;
import model.dao.ContatoGrupoDAO;
import model.dao.GrupoDAO;
import model.pojo.Contato;
import model.pojo.ContatoGrupo;
import model.pojo.Grupos;

@ManagedBean(name="contatoGrupoController")
@SessionScoped
public class ContatoGrupoController implements Serializable{
    //private List<Contato> contatos = new ArrayList<>();
    private Contato contato;
    private Grupos grupo;
    private ContatoGrupo contatoGrupo;

    public ContatoGrupoController() {
        //cDao = new ContatoDAO();
        //contatos = cDao.listaContatos();
    }
    
    public List<Contato> getContatos() {
        return new ContatoDAO().listaContatos();
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
    
    public List<Grupos> getGrupos() {
        return new GrupoDAO().listaGrupos();
    }

    public Grupos getGrupo() {
        return grupo;
    }

    public void setGrupos(Grupos grupo) {
        this.grupo = grupo;
    }
    
    public List<ContatoGrupo> getContatoGrupos() {
        return new ContatoGrupoDAO().listaContatoGrupo();
    }
    
    public ContatoGrupo getContatoGrupo() {
        return contatoGrupo;
    }

    public void setContatoGrupo(ContatoGrupo contatoGrupo) {
        this.contatoGrupo = contatoGrupo;
    }
    
    public List<ContatoGrupo> contatosNoGrupo(Grupos grupo) {
        return new ContatoGrupoDAO().listaContatoNoGrupo(grupo);
    }
    
    public String prepararAdicionarAoGrupo(){
        System.out.println("Preparando para adicionar contato a um grupo");
        
        contato = new Contato();
        grupo = new Grupos();
        
        return "/inserirContatoGrupo.xhtml";
    }

    public String adicionarAoGrupo(){
        System.out.println("Contato "+contato.getNome() +" Grupo: " + grupo.getNome());
        
        ContatoGrupo contatoGrupo = new ContatoGrupo();
        contatoGrupo.setContato(contato);
        contatoGrupo.setGrupos(grupo);
        
        ContatoGrupoDAO cDAO = new ContatoGrupoDAO();
        cDAO.incluir(contatoGrupo);
        
        return "/visualizarGrupos.xhtml";
    }
    
    public void excluirDoGrupo(ContatoGrupo c){
        System.out.println("Retirando contato do grupo - " +"ID:"+ c.getId());
        
        ContatoGrupoDAO cDao = new ContatoGrupoDAO();
        cDao.remove(c);
        
        String uri = "/faces/visualizarGrupos.xhtml";
        
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + uri);
        } catch (IOException ex) {
            Logger.getLogger(ContatoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
