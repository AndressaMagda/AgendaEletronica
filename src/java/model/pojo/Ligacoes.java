package model.pojo;
// Generated 18/09/2015 21:59:49 by Hibernate Tools 4.3.1



/**
 * Ligacoes generated by hbm2java
 */
public class Ligacoes  implements java.io.Serializable {


     private Integer id;
     private Contato contato;
     private Integer duracao;
     private Integer qtdChamadas;
     
    public Ligacoes() {
    }

	
    public Ligacoes(Integer id) {
        this.id = id;
    }
    public Ligacoes(Integer id, Contato contato, Integer duracao) {
       this.id = id;
       this.contato = contato;
       this.duracao = duracao;
    }
   
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    public Contato getContato() {
        return this.contato;
    }
    
    public void setContato(Contato contato) {
        this.contato = contato;
    }
    public Integer getDuracao() {
        return this.duracao;
    }
    
    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Integer getQtdChamadas() {
        return qtdChamadas;
    }

    public void setQtdChamadas(Integer qtdChamadas) {
        this.qtdChamadas = qtdChamadas;
    }

}


