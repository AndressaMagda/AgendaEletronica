package model.dao;

import java.util.List;
import model.pojo.Contato;
import model.pojo.Grupos;
import model.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ContatoDAO {
    
    public void save(Contato c) {
        System.out.println("Abrindo a sessao.");
        Session session = HibernateUtil.getSessionFactory().openSession();
       
        Transaction t = session.beginTransaction();
        session.save(c);
        t.commit();
        
        session.close();
        System.out.println("Cadastrado");
    }
    
    
    public Contato getContato(int id) {
        System.out.println("Preparando para buscar contato.");
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Contato c = (Contato) session.load(Contato.class, id);
        System.out.println("Sucesso: "+c.getNome()+" encontrado!!");
        session.close();
        return c;
    }
    
    public List<Contato> listaContatos(){
        List<Contato> lst = null;
        
        try {
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Contato as contato ORDER BY contato.nome";
            Query query = session.createQuery(hql);
            lst = query.list();
            
            session.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lst;
    }
    
    public void remove(Contato c) {
        System.out.println("ID: "+ c.getId() +" - " + c.getNome() + " - sendo recebida para remoção");
        Session session = HibernateUtil.getSessionFactory().openSession();
       
        Transaction t = session.beginTransaction();
        session.delete(c);
        t.commit();
        
        session.close();
        System.out.println("ID: "+ c.getId() +" Removido");
    }
    
    
    public void update(Contato c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Transaction t = session.beginTransaction();
        session.update(c);
        t.commit();
       
        session.close();
        System.out.println("ID: "+ c.getId() +" Atualizado");
    }
    
    
    public Contato getContatoByTelefone(String telefone){
        
        Contato c = null;
        
        try {
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            String hql = "FROM Contato AS contato WHERE contato.telefoneNum = :t";
            Query query = session.createQuery(hql);
            query.setParameter("t", telefone);
            
            c = (Contato) query.uniqueResult();
            
            session.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        return c;
    }
    
}
