package gestaorp.com.br.erp.cargos.services;

import gestaorp.com.br.erp.cargos.entities.Cargos;
import gestaorp.com.br.erp.cargos.exeptions.ErrorExeptions;
import gestaorp.com.br.erp.responses.Responses;
import gestaorp.com.br.erp.cargos.model.CargosRepository;
import gestaorp.com.br.erp.responses.ResponsesList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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

        cargo.setCriacao(new Date());
        cargo.setAlteracao(new Date());
        Cargos savedCargo = cargosRepository.save(cargo);

        Responses<Cargos> response = new Responses<>(HttpStatus.CREATED.value(), savedCargo, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
     * Remove (ou desativa) um cargo pelo ID.
     *
     * Este método busca um cargo pelo ID no repositório. Se o cargo for encontrado, ele define a data de alteração
     * e a data de desativação como a data atual, efetivamente desativando o cargo. O método então salva as alterações
     * no repositório. O método retorna uma resposta com o status HTTP 202 (Accepted), indicando que a solicitação foi
     * aceita para processamento, mas a ação final pode ocorrer de forma assíncrona.
     *
     * @param id O ID do cargo a ser removido (ou desativado).
     * @return ResponseEntity contendo um objeto {@link Responses} com o código de status HTTP 202 (Accepted)
     *         e informações adicionais (se houver).
     */
    public ResponseEntity<Responses> removerPorID(Long id) {

        Cargos cargo = this.cargosRepository.findById(id).orElse(null);

        if(cargo != null){
            cargo.setAlteracao(new Date());
            cargo.setDesativacao(new Date());
            cargosRepository.save(cargo);
        }

        Responses<Cargos> response = new Responses<>(HttpStatus.ACCEPTED.value(), null, null);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }


    /**
     * Retorna uma lista de todos os cargos ordenados pelo nome em ordem ascendente.
     *
     * @return ResponseEntity contendo um objeto ResponsesList com status HTTP 200 (OK) e a lista de cargos.
     *         Se não houver cargos, a lista retornada será vazia.
     */
    public ResponseEntity<ResponsesList> listarTodos() {
        List<Cargos> cargos = this.cargosRepository.findByDesativacaoIsNull(Sort.by("nome").ascending());
        ResponsesList<Cargos> response = new ResponsesList<>(HttpStatus.OK.value(), cargos, 0, 0, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Listar todos os cargos que estão desativados.
     *
     * Este método busca todos os cargos no repositório que têm a data de desativação não nula,
     * indicando que estão desativados. Ele retorna uma lista de cargos desativados encapsulada em
     * um objeto {@link ResponsesList} com status HTTP 200 (OK).
     *
     * @return ResponseEntity contendo um objeto {@link ResponsesList} com a lista de cargos desativados,
     *         o código de status HTTP e informações adicionais (se houver).
     */
    public ResponseEntity<ResponsesList> listarDesativados() {
        List<Cargos> cargos = this.cargosRepository.findByDesativacaoIsNotNull();
        ResponsesList<Cargos> response = new ResponsesList<>(HttpStatus.OK.value(), cargos, 0, 0, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Reativar um cargo que foi desativado.
     *
     * Este método busca um cargo pelo ID que está desativado (ou seja, possui uma data de desativação não nula),
     * e o reativa. A reativação é feita definindo a data de desativação como nula e atualizando a data de alteração.
     * Após reativar o cargo, o método chama o método {@link #alterar(Long, Cargos)} para persistir as alterações.
     * O método retorna o cargo atualizado encapsulado em um objeto {@link Responses} com status HTTP 200 (OK).
     *
     * @param id O ID do cargo a ser reativado.
     * @return ResponseEntity contendo um objeto {@link Responses} com o cargo reativado, o código de status HTTP
     *         e informações adicionais (se houver).
     */
    public ResponseEntity<Responses> reativar(Long id) {
        Cargos cargo = this.cargosRepository.findByIdAndDesativacaoIsNotNull(id);

        if(cargo != null){
            cargo.setAlteracao(new Date());
            cargo.setDesativacao(null);
            this.alterar(id, cargo);
        }

        Responses<Cargos> response = new Responses<>(HttpStatus.OK.value(), cargo, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    /**
     * Retorna os detalhes de um cargo específico com base no ID fornecido.
     *
     * @param id O ID do cargo que se deseja obter.
     * @return ResponseEntity contendo um objeto Responses com status HTTP 200 (OK) e os detalhes do cargo.
     *         Se o cargo com o ID fornecido não for encontrado, será retornado null no lugar.
     */
    public ResponseEntity<Responses> listarPorID(Long id) {
        Cargos cargo = this.cargosRepository.findById(id)
                .orElse(null);

        Responses<Cargos> response = new Responses<>(HttpStatus.OK.value(), cargo, null);
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
