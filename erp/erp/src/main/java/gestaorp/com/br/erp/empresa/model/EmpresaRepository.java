package gestaorp.com.br.erp.empresa.model;

import gestaorp.com.br.erp.empresa.entities.Empresa;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Empresa findByNome(String nome);

    List<Empresa> findByDesativacaoIsNotNull();

    List<Empresa> findByDesativacaoIsNull(Sort sort);

    Empresa findByIdAndDesativacaoIsNotNull(Long id);
}
