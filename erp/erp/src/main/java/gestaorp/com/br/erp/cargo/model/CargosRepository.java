package gestaorp.com.br.erp.cargo.model;

import gestaorp.com.br.erp.cargo.entities.Cargo;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CargosRepository extends JpaRepository<Cargo, Long> {

    Cargo findByNome(String nome);

    List<Cargo> findByDesativacaoIsNotNull();

    List<Cargo> findByDesativacaoIsNull(Sort sort);

    Cargo findByIdAndDesativacaoIsNotNull(Long id);
}
