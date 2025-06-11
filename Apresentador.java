import javafx.application.Platform;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;

public class Apresentador extends Thread {
    private int tempoDeexebicao;
    private int permitir; 
    private Rectangle alteraCorTela; 
    

    public Apresentador(){

    }

    public Apresentador(int temp, int permitir, Rectangle alterarCorTela){
        tempoDeexebicao = temp;
        this.permitir = permitir;
        this.alteraCorTela = alterarCorTela;
    }



    @Override
    public void run() {
        while (true) {
            try {
                Semaforos.iniciarFilme.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Semaforos.apresentar = true;
            Semaforos.assistir.release(permitir);
            System.out.println("apresentando");
            Platform.runLater(() -> MainFX.addLog("apresentando"));


            alteraCorTela.setFill(Color.rgb(177, 196, 199));

            for(int i=0; i<tempoDeexebicao*2; i++){
                try {
                    sleep(500);
                    if (alteraCorTela.getFill().equals(Color.rgb(177, 196, 199))){
                        alteraCorTela.setFill(Color.rgb(58, 60, 61));
                    }else{
                        alteraCorTela.setFill(Color.rgb(177, 196, 199));
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            alteraCorTela.setFill(Color.BLACK);
            
            Semaforos.apresentar = false;
            while (Semaforos.cadeirasOcupadas != 0) {
                System.out.println("esperando o cinema desocupar");
                Platform.runLater(() -> MainFX.addLog("esperando o cinema desocupar"));

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Semaforos.entrarCinema.release(permitir);
        }
        
    }

    public void setTempoDeexebicao(int tempoDeexebicao) {
        this.tempoDeexebicao = tempoDeexebicao;
    }

    public void setPermitir(int permitir) {
        this.permitir = permitir;
    }
    
}
