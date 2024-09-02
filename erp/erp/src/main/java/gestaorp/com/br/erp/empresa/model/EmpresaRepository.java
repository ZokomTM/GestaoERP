package gestaorp.com.br.erp.empresa.model;

import gestaorp.com.br.erp.empresa.entities.EmpresaDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<EmpresaDTO, Long> {

    EmpresaDTO findByNome(String nome);

    List<EmpresaDTO> findByDesativacaoIsNotNull();

    List<EmpresaDTO> findByDesativacaoIsNull(Sort sort);

    EmpresaDTO findByIdAndDesativacaoIsNotNull(Long id);
}
