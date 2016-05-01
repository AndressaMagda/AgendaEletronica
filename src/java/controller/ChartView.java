package controller;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.dao.LigacoesDAO;
import model.dao.MensagemDAO;
import model.pojo.Ligacoes;
import model.pojo.Mensagens;
import org.primefaces.model.chart.PieChartModel;

@ManagedBean(name = "chartView")
@ViewScoped
public class ChartView implements Serializable{
    private PieChartModel pieModelChamadasQtd;
    private PieChartModel pieModelChamadasTotal;
    private PieChartModel pieModelMensagensQtd;
    
 
    @PostConstruct
    public void init() {
        createChart();
    }
     
    public PieChartModel getPieModelChamadasQtd() {
        return pieModelChamadasQtd;
    }
    
    public PieChartModel getPieModelChamadasTotal() {
        return pieModelChamadasTotal;
    }
    
    public PieChartModel getPieModelMensagensQtd() {
        return pieModelMensagensQtd;
    }
     
    private void createChart() {
        pieModelChamadasQtd();
        pieModelChamadasTotal();
        pieModelMensagensQtd();
    }
    
    public List<Ligacoes> getLigacoes() {
        return new LigacoesDAO().listaLigacoes();
    }
    
    public List<Mensagens> getMensagens() {
        return new MensagemDAO().listaMensagensRecebidas();
    }
    
    private void pieModelChamadasQtd() {
        List<Ligacoes> dadosLigacoes = getLigacoes();
//        System.out.println("Organizando lista de tamanho " + dadosLigacoes.size());
        
        
        pieModelChamadasQtd = new PieChartModel();
        
//        for(int i = 0; i<(dadosLigacoes.size()); i++){
//            System.out.println(dadosLigacoes.get(i).getContato().getNome() + " Druração = "+dadosLigacoes.get(i).getDuracao() + " QTD: "+ dadosLigacoes.get(i).getQtdChamadas());
//        }
//        System.out.println("FIM");
        
        
        for(int i = 0; i<(dadosLigacoes.size()); i++){
            pieModelChamadasQtd.set(dadosLigacoes.get(i).getContato().getNome(), dadosLigacoes.get(i).getQtdChamadas());
        }
         
        //pieModelChamadasQtd.setTitle("Quantidades de Ligações Efetuadas para o Contato");
        pieModelChamadasQtd.setLegendPosition("e");
        pieModelChamadasQtd.setFill(false);
        pieModelChamadasQtd.setShowDataLabels(true);
        pieModelChamadasQtd.setDiameter(150);
    }
    
    private void pieModelChamadasTotal() {
        List<Ligacoes> dadosLigacoes = getLigacoes();
        
        pieModelChamadasTotal = new PieChartModel();
        
        for(int i = 0; i<(dadosLigacoes.size()); i++){
            pieModelChamadasTotal.set(dadosLigacoes.get(i).getContato().getNome(), dadosLigacoes.get(i).getDuracao());
        }
         
        //pieModelChamadasTotal.setTitle("Duração de Ligagões para Contato");
        pieModelChamadasTotal.setLegendPosition("w");
        //pieModelChamadasQtd.setFill(false);
        pieModelChamadasTotal.setShowDataLabels(true); //mostra a informação em cada pedaço do gráfico
        pieModelChamadasTotal.setDiameter(150);
    }
    
    private void pieModelMensagensQtd() {
        List<Mensagens> dadosMensagens = getMensagens();
        int msg=0;
        System.out.println("Organizando lista de Mensagens tamanho " + dadosMensagens.size());
        
        
        pieModelMensagensQtd = new PieChartModel();
        
        for(int i = 0; i<(dadosMensagens.size()); i++){
            System.out.println(dadosMensagens.get(i).getContato().getNome() + " Texto = "+dadosMensagens.get(i).getConteudo());
        }
        System.out.println("FIM");
        
        
        for(int i = 0; i<(dadosMensagens.size()); i++){
            
            if(dadosMensagens.get(i).getConteudo() != null) msg=1;
            
            pieModelMensagensQtd.set(dadosMensagens.get(i).getContato().getNome(), msg);
        }
         
        //pieModelChamadasQtd.setTitle("Quantidades de Ligações Efetuadas para o Contato");
        pieModelMensagensQtd.setLegendPosition("e");
        pieModelMensagensQtd.setFill(false);
        pieModelMensagensQtd.setShowDataLabels(true);
        pieModelMensagensQtd.setDiameter(150);
    }
    
}
