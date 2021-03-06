package model.pojo;
// Generated 18/09/2015 21:59:49 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Grupos generated by hbm2java
 */
public class Grupos  implements java.io.Serializable {


     private int id;
     private String nome;
     private Set<ContatoGrupo> contatoGrupos = new HashSet<ContatoGrupo>(0);

    public Grupos() {
    }

	
    public Grupos(int id) {
        this.id = id;
    }
    public Grupos(int id, String nome, Set<ContatoGrupo> contatoGrupos) {
       this.id = id;
       this.nome = nome;
       this.contatoGrupos = contatoGrupos;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    public Set<ContatoGrupo> getContatoGrupos() {
        return this.contatoGrupos;
    }
    
    public void setContatoGrupos(Set<ContatoGrupo> contatoGrupos) {
        this.contatoGrupos = contatoGrupos;
    }




}


