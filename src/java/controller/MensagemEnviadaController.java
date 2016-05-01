package controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.dao.ContatoDAO;
import model.dao.ContatoGrupoDAO;
import model.dao.GrupoDAO;
import model.dao.MensagemEnviadaDAO;
import model.pojo.Contato;
import model.pojo.ContatoGrupo;
import model.pojo.Grupos;
import model.pojo.MensagensEnviadas;

@ManagedBean(name="mensagemEnviadaController")
@SessionScoped
public class MensagemEnviadaController implements Serializable{
    //private List<Contato> contatos = new ArrayList<>();
    private Contato contato;
    private MensagensEnviadas mensagem;
    private Grupos grupo;

    public MensagemEnviadaController() {
//        mensagem = new MensagensEnviadas();
//        grupo = new Grupos();
//        contato = new Contato();
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
    
    public List<ContatoGrupo> listaContatoNoGrupo(Grupos grupo) {
        return new ContatoGrupoDAO().listaContatoNoGrupo(grupo);
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
        return new MensagemEnviadaDAO().listaContatosComMensagens();
    }
    
    public List<MensagensEnviadas> getMensagens() {
        return new MensagemEnviadaDAO().listaMensagens();
    }

    public MensagensEnviadas getMensagem() {
        return mensagem;
    }

    public void setMensagem(MensagensEnviadas mensagens) {
        this.mensagem = mensagens;
    }
    
    public List<MensagensEnviadas> mensagensByContato(Contato contato) {
        return new MensagemEnviadaDAO().listaMensagensByContato(contato);
    }
    
    public Long mensagensEnviadas(Contato contato) {
        return new MensagemEnviadaDAO().mensagensEnviadas(contato);
    }
    
    public String prepararEnviarMensagem(){
        System.out.println("Preparando para enviar mensagem");
        
        mensagem = new MensagensEnviadas();
        contato = new Contato();
        
        return "/enviarMensagem.xhtml";
    }
    
    public String prepararVisualizarMensagens(Contato contato){
        System.out.println("Preparando para visualizar mensagem");
        
        this.contato = contato;
        
        return "/visualizarMensagem.xhtml";
    }
    
    public String enviarMensagem(){
        System.out.println("Mensagem para "+contato.getId() +": " + mensagem.getConteudo());
        
        mensagem.setContato(contato);
        mensagem.setDataHoraEnvio(new Date());
        
        MensagemEnviadaDAO mDAO = new MensagemEnviadaDAO();
        mDAO.incluir(mensagem);
        
        return "/visualizarMensagensEnviadas.xhtml";
    }
    
    public String prepararEnviarMensagemGrupo(){
        System.out.println("Preparando para enviar mensagem");
        
        mensagem = new MensagensEnviadas();
        grupo = new Grupos();
        
        return "/enviarMensagemGrupo.xhtml";
    }
    
    public String enviarMensagemGrupo(){
        System.out.println("Mensagem para "+grupo.getNome() +": " + mensagem.getConteudo());
        
        MensagemEnviadaDAO mDAO = new MensagemEnviadaDAO();
        
        List<ContatoGrupo> listaContatosNoGrupo = listaContatoNoGrupo(grupo);
        
        Date date = new Date();
        
        for (ContatoGrupo contatoGrupo : listaContatosNoGrupo) {
            mensagem.setContato(contatoGrupo.getContato());
            mensagem.setDataHoraEnvio(date);

            mDAO.incluir(mensagem);
        }
        
        return "/visualizarMensagensEnviadas.xhtml";
    }
    
}
