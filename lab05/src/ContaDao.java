import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ContaDao implements IContaDao {
    private final PreparedStatement pstmCreate;
    private final PreparedStatement pstmRead;
    private final PreparedStatement pstmReadByNumber;
    private final PreparedStatement pstmUpdate;
    private final PreparedStatement pstmDelete;

    public ContaDao(Connection c) throws Exception {
        
        this.pstmCreate       = c.prepareStatement("INSERT INTO CONTAS (NRO_CONTA, SALDO) VALUES (?, ?)");
        this.pstmRead         = c.prepareStatement("SELECT * FROM CONTAS");
        this.pstmReadByNumber = c.prepareStatement("SELECT * FROM CONTAS WHERE NRO_CONTA = ?");
        this.pstmUpdate       = c.prepareStatement("UPDATE CONTAS SET SALDO = ? WHERE NRO_CONTA = ?");
        this.pstmDelete       = c.prepareStatement("DELETE FROM CONTAS WHERE NRO_CONTA = ?");
    }

    @Override
    public boolean criar(Conta conta) {
        try {
            pstmCreate.setLong(1, conta.getNumero());
            pstmCreate.setBigDecimal(2, conta.getSaldo());
            return pstmCreate.executeUpdate() == 1;
        } catch (SQLException e) {
            
            throw new RuntimeException("Erro ao criar conta", e);
        }
    }

    @Override
    public List<Conta> lerTodas() throws Exception {
        List<Conta> contas = new ArrayList<>();
        try (ResultSet rs = pstmRead.executeQuery()) {
            while (rs.next()) {
                long n = rs.getLong("nro_conta");
                BigDecimal s = rs.getBigDecimal("saldo");
                contas.add(new Conta(n, s));
            }
        }
        return contas;
    }

    @Override
    public Conta buscarPeloNumero(long id) {
        try {
            pstmReadByNumber.setLong(1, id);
            try (ResultSet rs = pstmReadByNumber.executeQuery()) {
                if (rs.next()) {
                    long n = rs.getLong("nro_conta");
                    BigDecimal s = rs.getBigDecimal("saldo");
                    return new Conta(n, s);
                }
                return null; // não encontrada
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar conta por número", e);
        }
    }

    @Override
    public boolean atualizar(Conta conta) {
        try {
            pstmUpdate.setBigDecimal(1, conta.getSaldo());
            pstmUpdate.setLong(2, conta.getNumero());
            return pstmUpdate.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar saldo da conta", e);
        }
    }

    @Override
    public boolean apagar(Conta conta) {
        try {
            pstmDelete.setLong(1, conta.getNumero());
            return pstmDelete.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao apagar conta", e);
        }
    }
}
