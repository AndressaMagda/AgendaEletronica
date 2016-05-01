package controller;

import agenda.Agendador;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.dao.ContatoDAO;
import model.pojo.Contato;

@ManagedBean(name="contatoController")
@SessionScoped
public class ContatoController implements Serializable{
    
    private Contato contato;
    private ContatoDAO cDao;
    
    private static String novaMensagem;
    
    @PostConstruct
    public void init() {
         try {
            Agendador.inicia();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public ContatoController() {
       
    }
    
    public static void setNovaMensagem(String nome) {
        ContatoController.novaMensagem = "Nova mensagem de " + nome;
    }
    
    public static void limparNovaMensagem() {
        ContatoController.novaMensagem = "";
    }
    
    public static String getNovaMensagem() {
        String mensagem = ContatoController.novaMensagem;
        ContatoController.limparNovaMensagem();
        return mensagem;
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
    
    public String visualizarContatos(){
        return "/index.xhtml";
    }
    
    
    public String prepararAdicionarContato(){
        System.out.println("Preparando para cadastrar um contato");
        contato = new Contato();
        return "/cadastro.xhtml";
    }
    
    public String adicionarContato(){
        System.out.println("Pronto para cadastrar um contato!!");
        cDao = new ContatoDAO();
        
        contato.setTelefoneNum(limpa_telefone(contato.getTelefoneNum()));
        
        cDao.save(this.contato);
        
        return "/index.xhtml";
    }
    
    public String prepararAlterarContato(Contato contato){
        this.contato = contato;
        System.out.println("Mandando " +this.contato.getNome()+" para ser alterado" +" ID:"+ this.contato.getId());
        return "/editarContato.xhtml";
    }
    
    public void alterarContato(){
        System.out.println("Recebendo alterações para " + this.contato.getNome() +" ID:"+ this.contato.getId());
        cDao = new ContatoDAO();
        contato.setTelefoneNum(limpa_telefone(contato.getTelefoneNum()));
        cDao.update(this.contato);
       String uri = "/faces/index.xhtml";
        
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + uri);
        } catch (IOException ex) {
            Logger.getLogger(ContatoController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void excluirContato(Contato c){
        System.out.println("Mandando " +c.getNome() +" para ser excluido - " +"ID:"+ c.getId());
        cDao = new ContatoDAO();
        cDao.remove(c);
        
        String uri = "/faces/index.xhtml";
        
        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("faces/index.xhtml");
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + uri);
        } catch (IOException ex) {
            Logger.getLogger(ContatoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String limpa_telefone(String numero){
        numero = numero.replace("(", "");
        numero = numero.replace(")", "");
        numero = numero.replace("-", "");
        numero = numero.replace(" ", "");
        
        return numero;
    }
    
}