package model.dao;

import java.util.List;
import model.pojo.Grupos;
import model.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class GrupoDAO {
    
    public List<Grupos> listaGrupos(){
        List<Grupos> lst = null;
        
        try {
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Grupos";
            Query query = session.createQuery(hql);
            lst = query.list();
            
            session.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lst;
    }
    
    public void criar(Grupos g) {
        System.out.println("Criando grupo: " +g.getNome());
        Session session = HibernateUtil.getSessionFactory().openSession();
       
        Transaction t = session.beginTransaction();
        session.save(g);
        t.commit();
        
        session.close();
        System.out.println("Grupo criado com sucesso!!");
    }
    
    public void remove(Grupos g) {
        System.out.println("ID: "+ g.getId() +" - " + g.getNome() + " - sendo recebida para remoção");
        Session session = HibernateUtil.getSessionFactory().openSession();
       
        Transaction t = session.beginTransaction();
        session.delete(g);
        t.commit();
        
        session.close();
        System.out.println("ID: "+ g.getId() +" Removido");
    }
    public void update(Grupos g) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Transaction t = session.beginTransaction();
        session.update(g);
        t.commit();
       
        session.close();
        System.out.println("ID: "+ g.getId() +" Atualizado");
    }
    
    
}
