package model.dao;

import java.util.List;
import model.pojo.Contato;
import model.pojo.ContatoGrupo;
import model.pojo.Grupos;
import model.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ContatoGrupoDAO {
    
    public List<ContatoGrupo> listaContatoGrupo(){
        List<ContatoGrupo> lst = null;
        
        try {
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from ContatoGrupo as contatoGrupo inner join fetch contatoGrupo.contato inner join fetch contatoGrupo.grupos";
            Query query = session.createQuery(hql);
            lst = query.list();
            
            session.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lst;
    }
    
    public List<ContatoGrupo> listaContatoNoGrupo(Grupos grupo){
        List<ContatoGrupo> lst = null;
        
        try {
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from ContatoGrupo as contatoGrupo inner join fetch contatoGrupo.contato inner join fetch contatoGrupo.grupos WHERE contatoGrupo.grupos = :g";
            Query query = session.createQuery(hql);
            query.setParameter("g", grupo);
            lst = query.list();
            
            session.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lst;
    }
    
    public void incluir(ContatoGrupo c) {
        System.out.println("Adicionando contato ao grupo: " +c.getId());
        
        if (!haveContatoGrupo(c)) {
            Session session = HibernateUtil.getSessionFactory().openSession();

            Transaction t = session.beginTransaction();
            session.save(c);
            t.commit();

            session.close();    
        }
    
        System.out.println("Contato adicionado ao grupo com sucesso!!");
    }
    
    public boolean haveContatoGrupo(ContatoGrupo c) {
        System.out.println("Verificando se existe o contato grupo");
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        String hql = "from ContatoGrupo as contatoGrupo inner join fetch contatoGrupo.contato inner join fetch contatoGrupo.grupos WHERE contatoGrupo.grupos = :g AND contatoGrupo.contato = :c";
        Query query = session.createQuery(hql);
        query.setParameter("g", c.getGrupos());
        query.setParameter("c", c.getContato());
        
        if(query.list().isEmpty()){
            return false;
        }
        
        return true;
    }
    
    public void remove(ContatoGrupo c) {
        System.out.println("ID: "+ c.getId() + " - sendo recebida para remoção");
        Session session = HibernateUtil.getSessionFactory().openSession();
       
        Transaction t = session.beginTransaction();
        session.delete(c);
        t.commit();
        
        session.close();
        System.out.println("ID: "+ c.getId() +" Removido");
    }
    
}
