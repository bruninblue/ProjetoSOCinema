import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

//nesse método configura a janela principal da aplicação

//Stage: a janela da aplicação
//primaryStage: primeiro palco (janela) da aplicação
//Scene: representa o conteúdo da janela principal, contém todos os elementos visuais.
//root: componente principal que agrupa os elementos visuais.

public class MainFX extends Application {

    private String[] cores = {"red", "blue", "orange", "pink"};
    Random random = new Random();
    public static AnchorPane root;
    public static Double topoInicialFilaX = 50.0;
    public static Double topoInicialFilaY = 270.0;
    public static Double topoFilaX = 50.0;
    public static Double topoFilaY = 270.0;
    public static Double topoPrimeraFileira = 100.00;
    public static Double topoSegundaFileira = 180.0;
    public static TextArea logArea;
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private final static Queue<Fan> tarefas = new LinkedList();
    public static Map<Integer, ArrayList<Integer>> posicoes;
    Fan fan;
    private ArrayList<ImageView> poltronasOcupadas;
    private Map<Integer, ArrayList<Integer>> posicoesNaMesa;

    public void gerarAssentosCinema(int numero) {
        posicoes = new HashMap<>();
        poltronasOcupadas = new ArrayList<>();
        posicoes.put(1, new ArrayList<>(Arrays.asList(280, 100)));
        posicoes.put(2, new ArrayList<>(Arrays.asList(360, 100)));
        posicoes.put(3, new ArrayList<>(Arrays.asList(440, 100)));
        posicoes.put(4, new ArrayList<>(Arrays.asList(520, 100)));
        posicoes.put(5, new ArrayList<>(Arrays.asList(600, 100)));
        posicoes.put(6, new ArrayList<>(Arrays.asList(280, 180)));
        posicoes.put(7, new ArrayList<>(Arrays.asList(360, 180)));
        posicoes.put(8, new ArrayList<>(Arrays.asList(440, 180)));
        posicoes.put(9, new ArrayList<>(Arrays.asList(520, 180)));
        posicoes.put(10, new ArrayList<>(Arrays.asList(600, 180)));

        for (int i = 1; i <= numero; i++) {
            Image imagemPoltrona = new Image("imagens/poltrona.png");
            ImageView imagemViewPoltrona = new ImageView(imagemPoltrona);
            imagemViewPoltrona.setFitWidth(50);
            imagemViewPoltrona.setPreserveRatio(true);

            imagemViewPoltrona.setX(posicoes.get(i).get(0));
            imagemViewPoltrona.setY(posicoes.get(i).get(1));

            poltronasOcupadas.add(imagemViewPoltrona);
        }
    }

    public void gerarPosicoesAssentosMesa() {
        posicoesNaMesa = new HashMap<>();

        posicoesNaMesa.put(1, new ArrayList<>(Arrays.asList(342, 363)));
        posicoesNaMesa.put(2, new ArrayList<>(Arrays.asList(395, 363)));
        posicoesNaMesa.put(3, new ArrayList<>(Arrays.asList(302, 403)));
        posicoesNaMesa.put(4, new ArrayList<>(Arrays.asList(342, 444)));
        posicoesNaMesa.put(5, new ArrayList<>(Arrays.asList(395, 444)));
        posicoesNaMesa.put(6, new ArrayList<>(Arrays.asList(581, 364)));
        posicoesNaMesa.put(7, new ArrayList<>(Arrays.asList(635, 364)));
        posicoesNaMesa.put(8, new ArrayList<>(Arrays.asList(540, 403)));
        posicoesNaMesa.put(9, new ArrayList<>(Arrays.asList(582, 444)));
        posicoesNaMesa.put(10, new ArrayList<>(Arrays.asList(634, 444)));
        // posicoesNaMesa.put(1, new ArrayList<>(Arrays.asList(364, 379)));
        // posicoesNaMesa.put(2, new ArrayList<>(Arrays.asList(397, 379)));
        // posicoesNaMesa.put(3, new ArrayList<>(Arrays.asList(432, 379)));
        // posicoesNaMesa.put(4, new ArrayList<>(Arrays.asList(464, 379)));
        // posicoesNaMesa.put(5, new ArrayList<>(Arrays.asList(498, 379)));
        // posicoesNaMesa.put(6, new ArrayList<>(Arrays.asList(530, 379)));
        // posicoesNaMesa.put(7, new ArrayList<>(Arrays.asList(563, 379)));
        // posicoesNaMesa.put(8, new ArrayList<>(Arrays.asList(596, 379)));
        // posicoesNaMesa.put(9, new ArrayList<>(Arrays.asList(631, 379)));
        // posicoesNaMesa.put(10, new ArrayList<>(Arrays.asList(663, 379)));
    }

    

    @Override
    public void start(Stage primaryStage) {
        gerarPosicoesAssentosMesa();

        root = new AnchorPane();
        root.setStyle("-fx-background-color: #6B8E23;");

        Rectangle palcoCinema = new Rectangle(0, 0, 950, 250);
        palcoCinema.setFill(Color.web("#dddd88"));

        Rectangle linhaFila1 = new Rectangle(100, 270, 5, 70);
        linhaFila1.setFill(Color.BLACK);

        Rectangle linhaFila2 = new Rectangle(100, 360, 5, 70);
        linhaFila2.setFill(Color.BLACK);

        Rectangle bordaInferiorPalco = new Rectangle(100, 245, 750, 5);
        bordaInferiorPalco.setFill(Color.BLACK);

        Rectangle telaFilme = new Rectangle(200, 0, 550, 50);
        telaFilme.setFill(Color.BLACK);

        Rectangle porta = new Rectangle(940, 543, 10, 51);
        porta.setFill(Color.web("#5C4033"));

        // Imagem da Lanchonete
        Image imagemLanchonete = new Image("imagens/comida.png");
        ImageView imagemViewLanchonete = new ImageView(imagemLanchonete);
        imagemViewLanchonete.setX(830);
        imagemViewLanchonete.setY(370);
        imagemViewLanchonete.setFitWidth(120);
        imagemViewLanchonete.setPreserveRatio(true);

        // Imagem da mesa
        Image mesa = new Image("imagens/mesa.png");
        ImageView mesaView = new ImageView(mesa);
        mesaView.setX(311);
        mesaView.setY(370);
        mesaView.setFitWidth(450);
        mesaView.setPreserveRatio(true);


        Rectangle fundoForms = new Rectangle(951, 0, 250, 600);
        fundoForms.setFill(Color.web("#39444e"));

        Label inforExibicao = new Label("Informações da exibição");
        inforExibicao.setLayoutX(987);
        inforExibicao.setLayoutY(10);
        inforExibicao.setTextFill(Color.web("#ffffff"));
        inforExibicao.setFont(new Font(16));

        Label capacidadeAudiotorio = new Label("Capacidade auditório");
        capacidadeAudiotorio.setLayoutX(989);
        capacidadeAudiotorio.setLayoutY(41);
        capacidadeAudiotorio.setTextFill(Color.web("#ffffff"));
        capacidadeAudiotorio.setFont(new Font(14));
        TextField capacidadeAudiotorioField = new TextField();
        capacidadeAudiotorioField.setLayoutX(999);
        capacidadeAudiotorioField.setLayoutY(64);

        Label tempoExibicao = new Label("Tempo exibição");
        tempoExibicao.setLayoutX(989);
        tempoExibicao.setLayoutY(90);
        tempoExibicao.setTextFill(Color.web("#ffffff"));
        tempoExibicao.setFont(new Font(14));
        TextField tempoExibicaoField = new TextField();
        tempoExibicaoField.setLayoutX(999);
        tempoExibicaoField.setLayoutY(113);

        Button enviarInforExibi = new Button("Enviar");
        enviarInforExibi.setLayoutX(1045);
        enviarInforExibi.setLayoutY(144);

        enviarInforExibi.setOnAction(e -> {
            try {
                int tempExb = Integer.parseInt(tempoExibicaoField.getText());
                int numAssentos = Integer.parseInt(capacidadeAudiotorioField.getText());

                gerarAssentosCinema(numAssentos);
                root.getChildren().addAll(poltronasOcupadas);

                tempoExibicaoField.setEditable(false);
                capacidadeAudiotorioField.setEditable(false);
                Semaforos.entrarCinema.release(numAssentos);
                Semaforos.vagas = numAssentos;

                Apresentador apresentador = new Apresentador(tempExb, numAssentos, telaFilme);
                apresentador.start();

            } catch (Exception ex) {
                tempoExibicaoField.setText("");
                capacidadeAudiotorioField.setText("");
            }
        });

        // Label nome = new Label("Nome thread:");
        // nome.setLayoutX(989);
        // nome.setLayoutY(185);
        // nome.setTextFill(Color.web("#ffffff"));
        // nome.setFont(new Font(14));

        // TextField inseriNome = new TextField();
        // inseriNome.setLayoutX(999);
        // inseriNome.setLayoutY(208);

        // Button iniciarThread = new Button("Criar");
        // iniciarThread.setLayoutX(1045);
        // iniciarThread.setLayoutY(290);
        // iniciarThread.setPrefSize(50, 25);

        // Label tempoLanche = new Label("Tempo de lanche:");
        // tempoLanche.setLayoutX(989);
        // tempoLanche.setLayoutY(234);
        // tempoLanche.setTextFill(Color.web("#ffffff"));
        // tempoLanche.setFont(new Font(14));

        // TextField inseriTempoLanche = new TextField();
        // inseriTempoLanche.setLayoutX(999);
        // inseriTempoLanche.setLayoutY(255);

        // root.getChildren().addAll(palcoCinema, bordaInferiorPalco, telaFilme,
        // imagemViewLanchonete, mesaView, linhaFila1, linhaFila2, porta,
        // fundoForms,inforExibicao,
        // capacidadeAudiotorio,capacidadeAudiotorioField,tempoExibicao,tempoExibicaoField,
        // enviarInforExibi,nome,inseriNome, iniciarThread, tempoLanche,
        // inseriTempoLanche );

        Button criarFa = new Button("Criar Fã");
        criarFa.setLayoutX(1035);
        criarFa.setLayoutY(188);
        criarFa.setPrefSize(70, 25);

        criarFa.setOnAction(e -> showCriarFaDialog());

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setPrefWidth(240);
        logArea.setPrefHeight(180);
        logArea.setLayoutX(960);
        logArea.setLayoutY(340);
        logArea.setStyle("-fx-control-inner-background: #222; -fx-text-fill: #fff;");

        root.getChildren().addAll(palcoCinema, bordaInferiorPalco, telaFilme,
                imagemViewLanchonete, mesaView, linhaFila1, linhaFila2, porta, fundoForms, inforExibicao,
                capacidadeAudiotorio, capacidadeAudiotorioField, tempoExibicao, tempoExibicaoField,
                enviarInforExibi, criarFa, logArea);

        Scene scene = new Scene(root, 1200, 600); // largura //altura

        primaryStage.setTitle("Cinema");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.centerOnScreen();
        primaryStage.show();

        // for (int i = 0; i < 8; i++) {
        //     // Fan fan = new Fan(String.valueOf(i), tarefas.size() + 1, 4);
        //     // fan.start();
        //     String corAleatoria = cores[random.nextInt(cores.length)];
        //     Fan fan = new Fan(String.valueOf(i), tarefas.size() + 1, 4, corAleatoria);
        //     fan.start();
        // }
    }

    private void showCriarFaDialog() {
        Stage dialog = new Stage();
        dialog.setTitle("Criar Fã");
        dialog.setResizable(false);
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(300, 250);
        pane.setStyle("-fx-background-color: #39444e; -fx-border-color: #333; -fx-border-width: 2px;");

        // Centralização horizontal
        double centerX = 150; // metade da largura

        Label nomeLabel = new Label("Nome fã:");
        nomeLabel.setLayoutX(centerX - 120);
        nomeLabel.setLayoutY(25);
        nomeLabel.setTextFill(Color.WHITE);
        TextField nomeField = new TextField();
        nomeField.setLayoutX(centerX - 40);
        nomeField.setLayoutY(20);
        nomeField.setPrefWidth(140);
        nomeField.setStyle("-fx-text-fill: white; -fx-background-color: #222;");

        Label tempoLabel = new Label("Tempo de lanche:");
        tempoLabel.setLayoutX(centerX - 120);
        tempoLabel.setLayoutY(65);
        tempoLabel.setTextFill(Color.WHITE);
        TextField tempoField = new TextField();
        tempoField.setLayoutX(centerX + 10);
        tempoField.setLayoutY(60);
        tempoField.setPrefWidth(50);
        tempoField.setStyle("-fx-text-fill: white; -fx-background-color: #222;");

        Label corLabel = new Label("Cor do fã:");
        corLabel.setLayoutX(centerX - 120);
        corLabel.setLayoutY(105);
        corLabel.setTextFill(Color.WHITE);

        // Botões de cor centralizados em duas linhas
        Button btnVermelho = new Button("Vermelho");
        btnVermelho.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
        btnVermelho.setLayoutX(centerX - 110);
        btnVermelho.setLayoutY(130);
        btnVermelho.setPrefWidth(90);

        Button btnAzul = new Button("Azul");
        btnAzul.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        btnAzul.setLayoutX(centerX + 20);
        btnAzul.setLayoutY(130);
        btnAzul.setPrefWidth(70);

        Button btnLaranja = new Button("Laranja");
        btnLaranja.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white;");
        btnLaranja.setLayoutX(centerX - 110);
        btnLaranja.setLayoutY(170);
        btnLaranja.setPrefWidth(90);

        Button btnRosa = new Button("Rosa");
        btnRosa.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        btnRosa.setLayoutX(centerX + 20);
        btnRosa.setLayoutY(170);
        btnRosa.setPrefWidth(70);

        // Seleção de cor
        final String[] corSelecionada = { null };
        btnVermelho.setOnAction(ev -> {
            corSelecionada[0] = "red";
            btnVermelho.setStyle(
                    "-fx-background-color: #e74c3c; -fx-border-color: #222; -fx-border-width: 2px; -fx-text-fill: white;");
            btnAzul.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            btnLaranja.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white;");
            btnRosa.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        });
        btnAzul.setOnAction(ev -> {
            corSelecionada[0] = "blue";
            btnVermelho.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            btnAzul.setStyle(
                    "-fx-background-color: #3498db; -fx-border-color: #222; -fx-border-width: 2px; -fx-text-fill: white;");
            btnLaranja.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white;");
            btnRosa.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        });
        btnLaranja.setOnAction(ev -> {
            corSelecionada[0] = "orange";
            btnVermelho.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            btnAzul.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            btnLaranja.setStyle(
                    "-fx-background-color: #e67e22; -fx-border-color: #222; -fx-border-width: 2px; -fx-text-fill: white;");
            btnRosa.setStyle("-fx-background-color: #ff69b4; -fx-text-fill: white;");
        });
        btnRosa.setOnAction(ev -> {
            corSelecionada[0] = "pink";
            btnVermelho.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");
            btnAzul.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
            btnLaranja.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white;");
            btnRosa.setStyle(
                    "-fx-background-color: #ff69b4; -fx-border-color: #222; -fx-border-width: 2px; -fx-text-fill: white;");
        });

        Button criarBtn = new Button("Criar Fã");
        criarBtn.setLayoutX(centerX - 50);
        criarBtn.setLayoutY(210);
        criarBtn.setPrefWidth(100);
        criarBtn.setStyle("-fx-background-color:rgb(255, 255, 255); -fx-text-fill: black;");

        criarBtn.setOnAction(ev -> {
            String nome = nomeField.getText();
            String tempoStr = tempoField.getText();
            String cor = corSelecionada[0];
            if (nome.isEmpty() || tempoStr.isEmpty() || cor == null) {
                nomeField.setStyle("-fx-border-color: red;");
                tempoField.setStyle("-fx-border-color: red;");
                return;
            }
            try {
                int tempoLanche = Integer.parseInt(tempoStr);
                Fan novoFan = new Fan(nome, tarefas.size() + 1, tempoLanche, cor); // Supondo construtor com cor
                novoFan.start();
                tarefas.add(novoFan);
                dialog.close();
            } catch (Exception ex) {
                tempoField.setText("");
                tempoField.setStyle("-fx-border-color: red;");
            }
        });

        pane.getChildren().addAll(nomeLabel, nomeField, tempoLabel, tempoField, corLabel,
                btnVermelho, btnAzul, btnLaranja, btnRosa, criarBtn);
        dialog.setScene(new Scene(pane));
        dialog.initOwner(root.getScene().getWindow());
        dialog.show();
    }

    public static void addLog(String mensagem) {
        if (logArea != null) {
            logArea.appendText(mensagem + "\n");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}