import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class AppStreaming {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Midia> midias = new ArrayList<>();

        while (true) {
            System.out.println("Streaming do Kauã");
            System.out.println("(1) Adicionar novo Filme");
            System.out.println("(2) Adicionar nova Série");
            System.out.println("(3) Listar todas as mídias");
            System.out.println("(4) Sair");
            System.out.print("Escolha uma opção: ");

            String opcao = sc.nextLine().trim();

            switch (opcao) {
                case "1":
                    adicionarFilme(sc, midias);
                    break;

                case "2":
                    adicionarSerie(sc, midias);
                    break;

                case "3":
                    listarMidias(midias);
                    break;

                case "4":
                    System.out.println("Fechando...");
                    sc.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.\n");
            }
        }
    }

    private static void adicionarFilme(Scanner sc, List<Midia> midias) {
        System.out.print("Título do filme: ");
        String titulo = sc.nextLine();
        long duracao = lerLong(sc, "Duração (em minutos): ");

        Midia filme = new Filme(titulo, duracao);
        midias.add(filme);
        System.out.println("Filme adicionado!\n");
    }

    private static void adicionarSerie(Scanner sc, List<Midia> midias) {
        System.out.print("Título da série: ");
        String titulo = sc.nextLine();

        Serie serie = new Serie(titulo);

        for (int numTemp = 1; numTemp <= 2; numTemp++) {
            Temporada temporada = new Temporada(numTemp);

            for (int numEp = 1; numEp <= 2; numEp++) {
                System.out.print("Título do episódio " + numEp + " da temporada " + numTemp + ": ");
                String tituloEp = sc.nextLine();
                long duracaoEp = lerLong(sc, "Duração (em minutos) do episódio: ");

                temporada.adicionar(new Episodio(tituloEp, duracaoEp));
            }

            serie.adicionar(temporada);
        }

        midias.add(serie);
        System.out.println("Série adicionada!\n");
    }

    private static void listarMidias(List<Midia> midias) {
        if (midias.isEmpty()) {
            System.out.println("Nenhuma mídia cadastrada.\n");
            return;
        }

        System.out.println("\n Mídias Adicionadas");
        for (Midia m : midias) {
            System.out.println(m.info());
        }
        System.out.println();
    }

    private static long lerLong(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String entrada = sc.nextLine().trim();
            try {
                return Long.parseLong(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Digite um número inteiro.");
            }
        }
    }
}
