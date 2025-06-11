import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Semaforos {
    public static Semaphore entrarCinema = new Semaphore(0); // semaforo para que os fãs entrem no cinema
    public static Semaphore sairCinema = new Semaphore(1); // semaforo para que os fãs saiam no cinema
    public static Semaphore iniciarFilme = new Semaphore(0); // semafor que acordará a função demonstrador
    public static Semaphore assistir = new Semaphore(0);// acordar os fãs para assistir ao filme
    public static Semaphore mutex = new Semaphore(1); // manipular a variavel cadeirasOcupadas
    public static ArrayList<Integer> fila = new ArrayList<Integer>();
    public static Semaphore mutexFila = new Semaphore(1);
    public static Semaphore mutexLog = new Semaphore(1);
    static int vagas = 5; // numero de assentos disponiveis
    static int cadeirasOcupadas = 0; // numero de assentos ocupados
    static boolean apresentar = false; // condição para o filme iniciar
}
