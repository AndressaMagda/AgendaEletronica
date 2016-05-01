package controller;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.dao.ContatoDAO;
import model.dao.LigacoesDAO;
import model.pojo.Contato;
import model.pojo.Ligacoes;
import org.primefaces.context.RequestContext;

@ManagedBean(name="ligacaoController")
@SessionScoped
public class LigacaoController implements Serializable{
    //private List<Contato> contatos = new ArrayList<>();
    private Contato contato;
    private Ligacoes ligacao;
    private int tempo;

    public LigacaoController() {
        //cDao = new ContatoDAO();
        //contatos = cDao.listaContatos();
        contato = new Contato();
        ligacao = new Ligacoes();
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

    public Ligacoes getLigacao() {
        return ligacao;
    }

    public void setLigacao(Ligacoes ligacao) {
        this.ligacao = ligacao;
    }

    public String ligandoPara(){
        int id = contato.getId();
        ContatoDAO cDao = new ContatoDAO();
        contato = cDao.getContato(id);
        System.out.println("Iniciando chamada para "+contato.getNome());
        return "/ligando.xhtml";
    }
    
    public int getTempo() {
        return tempo;
    }
 
    public void increment() {
        tempo++;
    }
    
    public String desliga(){
        System.out.println("Duração da chamada para "+contato.getId() +" = " + tempo + " segundos");
        
        RequestContext reqCtx = RequestContext.getCurrentInstance();
        reqCtx.execute("poll.stop();");
        
        ligacao.setContato(contato);
        ligacao.setDuracao(tempo);
        ligacao.setQtdChamadas(1);
        
        LigacoesDAO lDAO = new LigacoesDAO();
        lDAO.SaveOrUpdate(ligacao);
        
        tempo = 0;
        
        return "/efetuarLigacao.xhtml";
    }
    
}
