public class CriterioPrecoMaximo implements CriterioBusca {
    @Override
    public boolean testar(Produto p, String valor) {
        try {
            double precoMaximo = Double.parseDouble(valor);
            return p.getPreco() <= precoMaximo;
        } catch (NumberFormatException e) {
            // valor informado não é numérico
            return false;
        }
    }
}
