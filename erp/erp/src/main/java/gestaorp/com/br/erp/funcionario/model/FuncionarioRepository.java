package gestaorp.com.br.erp.funcionario.model;

import gestaorp.com.br.erp.funcionario.entities.FuncionarioDTO;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuncionarioRepository extends JpaRepository<FuncionarioDTO, Long> {

    FuncionarioDTO findByNome(String nome);

    FuncionarioDTO findByEmail(String email);

    List<FuncionarioDTO> findByDesativacaoIsNotNull();

    List<FuncionarioDTO> findByDesativacaoIsNull(Sort sort);

    FuncionarioDTO findByIdAndDesativacaoIsNotNull(Long id);
}
