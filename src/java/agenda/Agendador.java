package agenda;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import tarefa.RecebimentoMensagemJOB;

public class Agendador {
 
    public static void inicia() throws Exception {
        System.out.println("AGENDANDO A TAREFA!");
        
        // Detalhes da tarefa
        JobDetail job = JobBuilder.newJob(RecebimentoMensagemJOB.class).withIdentity("tarefaRecebeMensagem", "grupo").build();
        
        // Criado um objeto de intervalo de repetição
        SimpleScheduleBuilder intervalo = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever();
        
        
        // Criado um disparador
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("gatilhoRecebeMensagem", "grupo").withSchedule(intervalo).build();
        
        
        // Finalmente é criado um objeto de agendamento
        // que recebe o JOB e o disparador!
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        
        scheduler.start();
        scheduler.scheduleJob(job, trigger);
 
    }
}