import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    static Scanner sc = new Scanner(System.in);

    static List<Produto> pesquisar(List<Produto> produtos, String valor, CriterioBusca criterio) {
        List<Produto> resultado = new ArrayList<>();
        for (Produto p : produtos) {
            if (criterio.testar(p, valor)) {
                resultado.add(p);
            }
        }
        return resultado;
    }

    static void menuPrincipal(List<Produto> produtos) {
        boolean sair = false;

        while (!sair) {
            System.out.println("\n(1) - Listar produtos");
            System.out.println("(2) - Pesquisar descrição");
            System.out.println("(3) - Pesquisar marca");
            System.out.println("(4) - Pesquisar pelo preço máximo");
            System.out.println("(5) - Pesquisar pelo preço mínimo");
            System.out.println("(0) - Sair");

            System.out.print("Escolha uma opção: ");
            String opcao = sc.nextLine();

            switch (opcao) {
                case "0":
                    sair = true;
                    break;

                case "1":
                    System.out.println("\n\nLista de produtos: ");
                    for (Produto p : produtos) {
                        System.out.println(p);
                    }
                    break;

                case "2":
                    System.out.print("Termo a pesquisar: ");
                    String termo = sc.nextLine();
                    System.out.println("\nResultado da pesquisa: ");
                    List<Produto> r1 = pesquisar(produtos, termo, new CriterioDescricao());
                    for (Produto p : r1) System.out.println(p);
                    break;

                case "3":
                    System.out.print("Marca a pesquisar: ");
                    String marca = sc.nextLine();
                    System.out.println("\nResultado da pesquisa: ");
                    List<Produto> r2 = pesquisar(produtos, marca, new CriterioMarca());
                    for (Produto p : r2) System.out.println(p);
                    break;

                case "4":
                    System.out.print("Preço máximo: ");
                    String precoMax = sc.nextLine();
                    System.out.println("\nResultado da pesquisa: ");
                    List<Produto> r3 = pesquisar(produtos, precoMax, new CriterioPrecoMaximo());
                    for (Produto p : r3) System.out.println(p);
                    break;

                case "5":
                    System.out.print("Preço mínimo: ");
                    String precoMinStr = sc.nextLine();
                    double precoMinimo;
                    try {
                        
                        precoMinimo = Double.parseDouble(precoMinStr.replace(",", "."));
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido!");
                        break;
                    }
                    System.out.println("\nResultado da pesquisa: ");
                    for (Produto p : produtos) {
                        if (p.getPreco() >= precoMinimo) {
                            System.out.println(p);
                        }
                    }
                    break;

                default:
                    System.out.println("Opção inválida");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Path arquivo = Paths.get("produtos.txt");
        List<Produto> produtos = new ArrayList<>();

        try {
            List<String> linhas = Files.readAllLines(arquivo);
            for (String linha : linhas) {
                String[] c = linha.split(";");
                if (c.length < 3) continue; 
                Produto p = new Produto(c[0], Double.parseDouble(c[1]), c[2]);
                produtos.add(p);
            }
            menuPrincipal(produtos);
        } catch (IOException e) {
            System.out.println("Erro ao carregar os produtos");
        }
    }
}
