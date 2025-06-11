import java.util.concurrent.CountDownLatch;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Fan extends Thread {

    private Image image;
    private ImageView imageView;
    private int id;
    private Double xAtual;
    private Double yAtual;
    private Double xNaFila;
    private Double yNaFila;
    private String color;
    private String nome;
    private CountDownLatch latch = new CountDownLatch(1);
    private Integer posicaoPoltrona;
    private int tempoLanche;

    public Fan(String nome, int id, int tempoLanche) {
        super(nome);
        this.nome = nome;
        this.id = id;
        this.tempoLanche = tempoLanche;
        this.color = new String("red");
        ;
        createCharacter();

    }

    public Fan(String nome, int id, int tempoLanche, String color) {
        super(nome);
        this.nome = nome;
        this.id = id;
        this.tempoLanche = tempoLanche;
        this.color = color;
        createCharacter();

    }

    @Override
    public void run() {
        try {
            int lanche;
            boolean nafila = false;
            while (true) {

                if (!nafila) {
                    // so a thread com o id certo entra no cinema

                    Semaforos.mutexFila.acquire();
                    moveTopoFila();
                    latch.await();
                    Semaforos.fila.add(id);
                    nafila = true;
                    System.out.println(
                            "fan:" + nome + " esta na fila, posicao:" + Semaforos.fila.size());

                    Platform.runLater(
                            () -> MainFX.addLog("fan:" + nome + " esta na fila, posicao:" + Semaforos.fila.size()));
                    Semaforos.mutexFila.release();

                    sleep(4000);
                }

                Semaforos.entrarCinema.acquire();
                Semaforos.mutexFila.acquire();

                if (id == Semaforos.fila.get(0)) {

                    Semaforos.fila.remove(0);
                    // posicaoPoltrona = Semaforos.fila.indexOf(id) + 1;
                    nafila = false;
                    System.out.println("fan:" + nome + " entrou no cinema");

                    Platform.runLater(() -> MainFX.addLog("fan:" + nome + " entrou no cinema"));

                    Semaforos.mutexFila.release();

                    Semaforos.mutex.acquire();

                    if (Semaforos.cadeirasOcupadas < Semaforos.vagas) {
                        posicaoPoltrona = Semaforos.cadeirasOcupadas + 1;
                        Semaforos.cadeirasOcupadas++;
                        latch = new CountDownLatch(1);
                        movePoltrona();
                        latch.await();

                        if (Semaforos.vagas == Semaforos.cadeirasOcupadas) {
                            Semaforos.iniciarFilme.release();
                            System.out.println("acordar apresentador");

                            Platform.runLater(() -> MainFX.addLog("acordar apresentador"));
                        }
                        // acordar o apresentador
                        Semaforos.mutex.release();

                        Semaforos.assistir.acquire();

                        while (Semaforos.apresentar) {
                            System.out.println("fan: " + nome + " assistindo");

                            Platform.runLater(() -> MainFX.addLog("fan: " + nome + " assistindo"));

                            sleep(1000);

                        }

                        System.out.println("sessão encerrada");

                        Platform.runLater(() -> MainFX.addLog("sessão encerrada"));

                        Semaforos.sairCinema.acquire();

                        latch = new CountDownLatch(1);
                        exitCinema();
                        latch.await();

                        Semaforos.sairCinema.release();

                        Semaforos.mutex.acquire();

                        Semaforos.cadeirasOcupadas--;
                        Semaforos.mutex.release();

                        for (lanche = 0; lanche < tempoLanche; lanche++) {
                            System.out.println("fan:" + nome + " está lanchando");

                            Platform.runLater(() -> MainFX.addLog("fan:" + nome + " está lanchando"));

                            sleep(1000);
                        }

                        sleep(5000);
                        latch = new CountDownLatch(1);
                        moveInicio();
                        latch.await();

                    } else {
                        Semaforos.mutex.release();
                    }
                } else {
                    Semaforos.entrarCinema.release();
                    Semaforos.mutexFila.release();
                }

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void createCharacter() {
        this.image = new Image("file:characters/" + color + "/up.png");
        this.imageView = new ImageView(image);
        imageView.setFitWidth(25);
        imageView.setFitHeight(25);
        imageView.setPreserveRatio(true);
        imageView.setX(926d);
        xAtual = 926d;
        imageView.setY(MainFX.root.heightProperty().floatValue() - 30);
        yAtual = MainFX.root.heightProperty().doubleValue() - 30;
        // Removido: Platform.runLater(() -> MainFX.root.getChildren().add(imageView));
    }

    private void moveX(Double xDest, Runnable onFinished) {
        if (xDest.compareTo(xAtual) != 0) {
            Platform.runLater(() -> {
                if (xDest > xAtual)
                    image = new Image("file:characters/" + color + "/right.png");
                else
                    image = new Image("file:characters/" + color + "/left.png");
                imageView.setImage(image);
                Path path = new Path();
                path.getElements().add(new MoveTo(xAtual, yAtual));
                path.getElements().add(new LineTo(xDest, yAtual));
                PathTransition pt = new PathTransition(Duration.seconds(1), path, imageView);
                pt.setOnFinished(e -> {
                    // imageView.setX(xDest);
                    xAtual = xDest;
                    if (onFinished != null)
                        onFinished.run();
                });
                pt.play();
            });
        } else {
            if (onFinished != null)
                Platform.runLater(onFinished);
        }
    }

    private void moveY(Double yDest, Runnable onFinished) {
        if (yDest.compareTo(yAtual) != 0) {
            Platform.runLater(() -> {
                if (yDest < yAtual)
                    image = new Image("file:characters/" + color + "/up.png");
                else
                    image = new Image("file:characters/" + color + "/down.png");
                imageView.setImage(image);
                Path path = new Path();
                path.getElements().add(new MoveTo(xAtual, yAtual));
                path.getElements().add(new LineTo(xAtual, yDest));
                PathTransition pt = new PathTransition(Duration.seconds(1), path, imageView);
                pt.setOnFinished(e -> {
                    yAtual = yDest;
                    if (onFinished != null)
                        onFinished.run();
                });
                pt.play();
            });
        } else {
            if (onFinished != null)
                Platform.runLater(onFinished);
        }
    }

    private void moveTopoFila() {
        // double destinoX = MainFX.topoFilaX;
        // double destinoY = MainFX.topoFilaY;
        if (xNaFila == null && yNaFila == null) {
            xNaFila = MainFX.topoFilaX;
            yNaFila = MainFX.topoFilaY;
            if (MainFX.topoFilaY + 30 <= MainFX.root.heightProperty().floatValue() - 30) {
                MainFX.topoFilaY += 30;
            } else {
                MainFX.topoFilaX += 30;
            }
        }

        Platform.runLater(() -> {
            if (!MainFX.root.getChildren().contains(imageView)) {
                MainFX.root.getChildren().add(imageView);
            }
        });

        moveX(xNaFila, () -> moveY(yNaFila, latch::countDown));

    }

    private void exitTopo() {
        if (MainFX.topoFilaY - 30 >= MainFX.topoPrimeraFileira) {
            MainFX.topoFilaY -= 30;
        } else {
            MainFX.topoFilaX -= 30;
            MainFX.topoFilaY = MainFX.topoPrimeraFileira;
        }
    }

    private void movePoltrona() {
        if (posicaoPoltrona <= 5) {
            moveX(MainFX.topoInicialFilaX, () -> moveY(MainFX.topoPrimeraFileira - 25,
                    () -> moveX(MainFX.posicoes.get(posicaoPoltrona).get(0).doubleValue() + 25,
                            () -> moveY(yAtual + 45, () -> {
                                image = new Image("file:characters/" + color + "/up.png");
                                imageView.setImage(image);
                                // exitTopo();
                                latch.countDown();
                            }))));
        } else {
            moveX(MainFX.topoInicialFilaX,
                    () -> moveY(MainFX.topoSegundaFileira - 25,
                            () -> moveX(MainFX.posicoes.get(posicaoPoltrona).get(0).doubleValue() + 25,
                                    () -> moveY(yAtual + 45, () -> {
                                        image = new Image("file:characters/" + color + "/up.png");
                                        imageView.setImage(image);
                                        // exitTopo();
                                        latch.countDown();
                                    }))));
        }
    }

    private void exitCinema() {
        Double lugarLanchonete = 292.d + (posicaoPoltrona * 46);
        moveY(yAtual - 25,
                () -> moveX(930.0,
                        () -> moveY(350.0,
                                () -> moveX(lugarLanchonete,
                                        () -> moveY(yAtual + 40,
                                                () -> {
                                                    // exitTopo();
                                                    latch.countDown();
                                                })))));
    }

    private void moveInicio() {
        moveY(yAtual - 40,
                () -> moveX(150.0,
                        () -> moveY(MainFX.root.heightProperty().doubleValue() - 60,
                                () -> moveX(926.0,
                                        // () -> moveY(MainFX.root.heightProperty().doubleValue() - 30, null))));
                                        () -> moveY(MainFX.root.heightProperty().doubleValue() - 30,
                                                latch::countDown)))));
    }
}