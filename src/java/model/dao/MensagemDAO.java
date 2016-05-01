package model.dao;

import java.util.ArrayList;
import java.util.List;
import model.pojo.Contato;
import model.pojo.Mensagens;
import model.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MensagemDAO {
    
    public void incluir(Mensagens m) {
        //System.out.println("Salvando mensagem: " +m.getConteudo()+" para " + m.getContato().getNome());
        Session session = HibernateUtil.getSessionFactory().openSession();
       
        Transaction t = session.beginTransaction();
        session.save(m);
        t.commit();
        
        session.close();
        System.out.println("Mensagem salva com sucesso!!");
    }
    
    public void update(Mensagens m) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        Transaction t = session.beginTransaction();
        session.update(m);
        t.commit();
       
        session.close();
        System.out.println("ID: "+ m.getId() +" Atualizada");
    }
    
    public void ler(Contato contato) {
        List<Mensagens> lista = listaMensagensNaoLidasByContato(contato);
        
        for (Mensagens mensagem: lista) {
            mensagem.setVisualizada(Boolean.TRUE);
            
            update(mensagem);
        }
    }
    
    public List<Mensagens> listaMensagensRecebidas(){
        List<Mensagens> lst = null;
        
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Mensagens as mensagens inner join fetch mensagens.contato ORDER BY mensagens.contato.nome";
            Query query = session.createQuery(hql);
            lst = query.list();
            
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lst;
    }
    
    public List<Contato> listaContatosComMensagens(){
        List<Mensagens> lst = null;
        
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Mensagens as mensagens inner join fetch mensagens.contato ORDER BY mensagens.contato.nome";
            Query query = session.createQuery(hql);
            lst = query.list();
            
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        List<Contato> lista = new ArrayList<>();
        
        String nome = "";
        
        for (int i = 0; i < lst.size(); i++) {
            if (!lst.get(i).getContato().getNome().equals(nome)) {
                nome = lst.get(i).getContato().getNome();
                
                lista.add(lst.get(i).getContato());
            }
        }
        
        return lista;
    }
    
    public List<Mensagens> listaMensagensNaoLidasByContato(Contato contato){
        List<Mensagens> lst = null;
        
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Mensagens as mensagens WHERE mensagens.contato = :c AND mensagens.visualizada = FALSE";
            Query query = session.createQuery(hql);
            query.setParameter("c", contato);
            lst = query.list();
            
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lst;
    }
    
    public Long mensagensNaoLidas(Contato contato) {
        Long count = null;
        
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "select count(*) from Mensagens as mensagens WHERE mensagens.contato = :c AND mensagens.visualizada = FALSE";
            Query query = session.createQuery(hql);
            query.setParameter("c", contato);
            count = (Long)query.uniqueResult();
            
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return count;
    }
    
    public List<Mensagens> listaMensagensByContato(Contato contato){
        List<Mensagens> lst = null;
        
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            
            String hql = "from Mensagens as mensagens WHERE mensagens.contato = :c order by mensagens.dataHoraEnvio desc";
            Query query = session.createQuery(hql);
            query.setParameter("c", contato);
            lst = query.list();
            
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lst;
    }
    
}
