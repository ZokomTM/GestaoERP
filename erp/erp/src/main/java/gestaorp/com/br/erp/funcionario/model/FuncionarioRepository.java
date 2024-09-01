package gestaorp.com.br.erp.funcionario.model;

import gestaorp.com.br.erp.funcionario.entities.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {
}
