package model.dao;

import java.util.List;
import model.pojo.Contato;
import model.pojo.Ligacoes;
import model.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class LigacoesDAO {
    
    public void SaveOrUpdate(Ligacoes l) {
        Ligacoes ligacao = null;
               
        System.out.println("Salvando chamada de " +l.getDuracao()+" segundos para " + l.getContato().getNome());
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = session.beginTransaction();

        ligacao = getLigacao(l.getContato());
        
        if(ligacao==null){
            System.out.println("Não existe - Inserir");
            
            session.save(l);
            t.commit();
        }
        else{
            System.out.println("Existe - Atualizar");
            
            ligacao.setDuracao(ligacao.getDuracao()+l.getDuracao());
            ligacao.setQtdChamadas(ligacao.getQtdChamadas()+1);

            session.update(ligacao);
            t.commit();
        }
        
        session.close();
        System.out.println("Ligacao salva com sucesso!!");
    }
    
    
    public Ligacoes getLigacao(Contato c) {
        List<Ligacoes> lst = null;
        System.out.println("Preparando para buscar ligacao.");
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        String hql = "from Ligacoes WHERE id_contato = "+c.getId();
        Query query = session.createQuery(hql);
        
        lst = query.list();
        
        if(!lst.isEmpty()){
            Ligacoes l = (Ligacoes)query.uniqueResult();
            session.close();
            
            return l;
        }
        System.err.println("Sem ligacoes para " + c.getNome());
        session.close();
        
        return null;
    }
    
    public List<Ligacoes> listaLigacoes(){
        List<Ligacoes> lst = null;
       
        System.out.println("Listando todas as ligações");
        
        try {
            
            Session session = HibernateUtil.getSessionFactory().openSession();
            String hql = "from Ligacoes AS ligacoes INNER JOIN FETCH ligacoes.contato";
            
            //String hql = "SELECT DISTINCT l.contato, [SELECT count(c.duracao) FROM ligacoes AS c WHERE c.contato = l.contato] AS l.duracao FROM ligacoes AS l INNER JOIN FETCH l.contato";
            
            Query query = session.createQuery(hql);
            lst = query.list();
            
            session.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return lst;
    }
    
//    public List<Ligacoes> getDadosChamadasQtd(){
//        List<Ligacoes> lst = null;
//        
//         System.out.println("Criando ranking de chamadas");
//        
//        try {
//
////    SELECT DISTINCT l.id_contato, 
////        (SELECT count(c.duracao) FROM ligacoes AS c WHERE c.id_contato = l.id_contato) AS qtd_chamadas,
////        (SELECT sum(c.duracao) FROM ligacoes AS c WHERE c.id_contato = l.id_contato) AS tempo_chamadas 
////    FROM ligacoes AS l;
//            
//            Session session = HibernateUtil.getSessionFactory().openSession();
//            
//            String hql = "SELECT DISTINCT(l.contato) " + 
//            "(SELECT count(c.duracao) FROM Ligacoes AS c WHERE c.contato = l.contato) AS duracao " +
//            "FROM Ligacoes AS l INNER JOIN FETCH l.contato";
//            //"(SELECT sum(duracao) FROM ligacoes WHERE id_contato=l.id_contato) AS tempo_chamadas" +
//            
//            Query query = session.createQuery(hql);
//            lst = query.list();
//            
//            session.close();
//            
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        
//        return lst;
//    }
    
    
}
