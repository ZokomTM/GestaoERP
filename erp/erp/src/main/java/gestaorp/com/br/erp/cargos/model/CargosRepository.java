package gestaorp.com.br.erp.cargos.model;

import gestaorp.com.br.erp.cargos.entities.Cargos;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CargosRepository extends JpaRepository<Cargos, Long> {

    Cargos findByNome(String nome);

    List<Cargos> findByDesativacaoIsNotNull();

    List<Cargos> findByDesativacaoIsNull(Sort sort);

    Cargos findByIdAndDesativacaoIsNotNull(Long id);
}
