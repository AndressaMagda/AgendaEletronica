package controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.dao.ContatoDAO;
import model.dao.ContatoGrupoDAO;
import model.dao.GrupoDAO;
import model.dao.MensagemDAO;
import model.pojo.Contato;
import model.pojo.ContatoGrupo;
import model.pojo.Grupos;
import model.pojo.Mensagens;

@ManagedBean(name="mensagemController")
@SessionScoped
public class MensagemController implements Serializable{
    
    private Contato contato;
    private Mensagens mensagem;
    private Grupos grupo;

    public MensagemController() {
        
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
    
    public List<Contato> getContatosComMensagens() {
        return new MensagemDAO().listaContatosComMensagens();
    }
    
    public List<Mensagens> getMensagensRecebidas() {
        return new MensagemDAO().listaMensagensRecebidas();
    }
    
    public Mensagens getMensagem() {
        return mensagem;
    }

    public void setMensagem(Mensagens mensagens) {
        this.mensagem = mensagens;
    }
    
    public List<Mensagens> mensagensByContato(Contato contato) {
        return new MensagemDAO().listaMensagensByContato(contato);
    }
    
    public Long mensagensNaoLidas(Contato contato) {
        return new MensagemDAO().mensagensNaoLidas(contato);
    }
    
    public String prepararVisualizarMensagens(Contato contato){
        System.out.println("Preparando para visualizar mensagem");
        
        this.contato = contato;
        
        MensagemDAO mDAO = new MensagemDAO();
        mDAO.ler(contato);
        
        return "/visualizarMensagens.xhtml";
    }
    
}
