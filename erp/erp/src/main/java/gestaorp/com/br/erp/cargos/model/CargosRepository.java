package gestaorp.com.br.erp.cargos.model;

import gestaorp.com.br.erp.cargos.entities.Cargos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargosRepository extends JpaRepository<Cargos, Long> {

    Cargos findByNome(String nome);
}
