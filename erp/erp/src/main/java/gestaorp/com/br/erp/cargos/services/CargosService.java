package gestaorp.com.br.erp.cargos.services;

import gestaorp.com.br.erp.cargos.entities.Cargos;
import gestaorp.com.br.erp.cargos.exeptions.ErrorExeptions;
import gestaorp.com.br.erp.cargos.exeptions.Responses;
import gestaorp.com.br.erp.cargos.model.CargosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CargosService {

    @Autowired
    private CargosRepository cargosRepository;

    /**
     * Cadastra um novo cargo no sistema.
     *
     * Este método realiza as seguintes operações:
     * - Verifica se o ID do cargo já foi informado. Se sim, retorna um erro indicando que o ID não deve ser fornecido ao cadastrar um novo cargo.
     * - Executa as validações comuns sobre o cargo. Se houver erros, retorna o erro apropriado.
     * - Define a data de alteração do cargo como a data atual.
     * - Salva o cargo no repositório e retorna a resposta com o cargo salvo e o status HTTP OK.
     *
     * @param cargo O objeto `Cargos` que deve ser cadastrado.
     * @return Um `ResponseEntity` contendo um objeto `Responses` com o cargo salvo e o status HTTP OK,
     *         ou um erro indicando problemas com o cadastro.
     */
    public ResponseEntity<Responses> cadastrar(Cargos cargo){
        if(cargo.getId() != null){
            Responses<Cargos> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_ID_FOI_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Responses> erros = validacoesComuns(cargo);
        if(erros != null){
            return erros;
        }

        cargo.setAlteracao(new Date());
        Cargos savedCargo = cargosRepository.save(cargo);

        Responses<Cargos> response = new Responses<>(HttpStatus.OK.value(), savedCargo, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Altera um cargo existente no sistema.
     *
     * Este método realiza as seguintes operações:
     * - Verifica se o ID fornecido é válido. Se o ID for nulo ou zero, retorna um erro indicando que o ID é obrigatório.
     * - Verifica se o cargo com o ID fornecido existe no repositório. Se não for encontrado, retorna um erro indicando que o cargo não foi encontrado.
     * - Executa as validações comuns sobre o cargo. Se houver erros, retorna o erro apropriado.
     * - Define a data de alteração do cargo como a data atual.
     * - Salva as alterações no repositório e retorna a resposta com o cargo atualizado e o status HTTP OK.
     *
     * @param id O ID do cargo a ser alterado.
     * @param cargo O objeto `Cargos` contendo os novos dados para atualização.
     * @return Um `ResponseEntity` contendo um objeto `Responses` com o cargo atualizado e o status HTTP OK,
     *         ou um erro indicando problemas com a alteração.
     */
    public ResponseEntity<Responses> alterar(Long id, Cargos cargo){
        if(id == null || id == 0){
            Responses<Cargos> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_ID_NAO_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Optional<Cargos> cargoConsultado = this.cargosRepository.findById(id);
        if(cargoConsultado == null){
            Responses<Cargos> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_CARGO_NAO_ENCONTRADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<Responses> erros = validacoesComuns(cargo);
        if(erros != null){
            return erros;
        }

        cargo.setAlteracao(new Date());
        Cargos savedCargo = cargosRepository.save(cargo);

        Responses<Cargos> response = new Responses<>(HttpStatus.OK.value(), savedCargo, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Realiza as validações comuns necessárias para o objeto `Cargos`.
     *
     * Este método verifica se os campos obrigatórios do objeto `Cargos` estão preenchidos e se os valores estão dentro dos limites aceitáveis:
     * - Verifica se a comissão foi informada e se está dentro do intervalo de 0 a 100.
     * - Verifica se o nome do cargo não está vazio ou nulo.
     *
     * @param cargo O objeto `Cargos` a ser validado.
     * @return Um `ResponseEntity` contendo um objeto `Responses` com o status HTTP e mensagens de erro apropriadas,
     *         ou `null` se todas as validações forem bem-sucedidas.
     */
    private ResponseEntity<Responses> validacoesComuns(Cargos cargo){
        if (cargo.getComissao() == null) {
            Responses<Cargos> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_COMISSAO_NAO_INFORMADA);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(cargo.getNome() == null || cargo.getNome().isBlank()){
            Responses<Cargos> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_NOME_VAZIO_OU_NAO_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(cargo.getComissao() < 0) {
            Responses<Cargos> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_COMISSAO_MENOR_ZERO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(cargo.getComissao() > 100) {
            Responses<Cargos> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_COMISSAO_MAIOR_CEM);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
