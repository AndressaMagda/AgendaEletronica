package tarefa;

import controller.ContatoController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import model.dao.ContatoDAO;
import model.dao.MensagemDAO;
import model.pojo.Contato;
import model.pojo.Mensagens;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


public class RecebimentoMensagemJOB implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        // Aqui vai a lógica da tarefa a ser chamada, nesta caso, vai imprimir no console
        
        System.out.println("----- Verificando se Existe uma Nova Mensagem -----");
        
        String diretorio = "C:\\MensagensRecebidas";
        
        FileFilter filter = new FileFilter() {
            public boolean accept(File file) {
                return file.getName().endsWith(".txt");
            }
        };

        File dir = new File(diretorio);
        File[] files = dir.listFiles(filter);
        
        String telefone = "";
        FileReader reader;
        String texto = ""; 
        Contato contato=null;
        ContatoDAO cDAO = new ContatoDAO();
        Mensagens msg = new Mensagens();
        MensagemDAO msgDAO = new MensagemDAO();
        
        for(int i=0; i<files.length; i++){
            
            //System.out.println("CAMINHO: " + files[i].toString());
            telefone = files[i].toString().substring(files[i].toString().length()-15, files[i].toString().length()-4);
            System.out.println("MENSAGEM NOVA DE " + telefone);
            try {
                reader = new FileReader(files[i].toString());
                BufferedReader leitor = new BufferedReader(reader); //utilizado para minimizar o número de pedidos de io para ler blocos maiores do arquivo de uma vez só
                Date d = new Date(files[i].lastModified());
                
                String line;
                while ((line = leitor.readLine()) != null) {
                    texto += line + " \n";
                }
                
                System.out.println("CONTEUDO DA MSG: " + texto); 
                
                leitor.close();  
                reader.close();
                //System.out.println("INDO PEGAR O CONTATO PELO TELEFONE NO BANCO"); 
                //Armazenar no banco antes de apagar o arquivo
                contato = cDAO.getContatoByTelefone(telefone);
                
                msg.setContato(contato);
                msg.setConteudo(texto);
                msg.setDataHoraEnvio(d);
                msg.setVisualizada(Boolean.FALSE);
                
                ContatoController.setNovaMensagem(contato.getNome());
                
                System.out.println("CONTATO QUE FEZ ENVIO DA MSG: " + msg.getContato().getNome()); 
                
                msgDAO.incluir(msg);
                
                if(files[i].delete()) System.out.println("MENSAGEM ARMAZENADA NO BANCO E ARQUIVO REMOVIDO DA PASTA"); 
                texto = ""; //Prepara para armazenar a proxima mensagem, se houver
                
                System.out.println("-----------------------------------------------------------------------------");
                
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
    }
    
}
