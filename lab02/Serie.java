import java.util.List;
import java.util.ArrayList;

public class Serie extends Midia {
    private List<Temporada> temporadas;

    public Serie(String titulo) {
        super(titulo);
        this.temporadas = new ArrayList<>();
    }

    public void adicionar(Temporada temporada) {
        if (temporada != null) {
            temporadas.add(temporada);
        }
    }

    @Override
    public long getDuracao() {
        long duracaoTotal = 0;
        for (int i = 0; i < temporadas.size(); i++) {
            duracaoTotal += temporadas.get(i).getDuracao();
        }
        return duracaoTotal;
    }

    @Override
    public String info() {
        return "Série: " + super.info() + "; Duração: " + getDuracao();
    }

  
}
