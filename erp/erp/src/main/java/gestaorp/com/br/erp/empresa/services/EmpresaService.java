package gestaorp.com.br.erp.empresa.services;

import gestaorp.com.br.erp.empresa.entities.Empresa;
import gestaorp.com.br.erp.exeptions.ErrorExeptions;
import gestaorp.com.br.erp.empresa.model.EmpresaRepository;
import gestaorp.com.br.erp.responses.Responses;
import gestaorp.com.br.erp.responses.ResponsesList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    /**
     * Cadastra uma nova empresa no sistema.
     *
     * Este método realiza as seguintes operações:
     * - Verifica se o ID da empresa foi informado. Se sim, retorna um erro indicando que o ID não deve ser fornecido ao cadastrar uma nova empresa.
     * - Executa as validações comuns sobre a empresa. Se houver erros, retorna o erro apropriado.
     * - Define as datas de contratação e alteração da empresa como a data atual.
     * - Salva a empresa no repositório e retorna a resposta com a empresa salva e o status HTTP CREATED.
     *
     * @param empresa O objeto `Empresa` que deve ser cadastrado.
     * @return Um `ResponseEntity` contendo um objeto `Responses` com a empresa salva e o status HTTP CREATED,
     *         ou um erro indicando problemas com o cadastro.
     */
    public ResponseEntity<Responses> cadastrar(Empresa empresa){
        if(empresa.getId() != null){
            Responses<Empresa> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_ID_FOI_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Responses> erros = validacoesComuns(empresa);
        if(erros != null){
            return erros;
        }

        empresa.setContratacao(new Date());
        empresa.setAlteracao(new Date());
        Empresa empresaSalva = this.empresaRepository.save(empresa);

        Responses<Empresa> response = new Responses<>(HttpStatus.CREATED.value(), empresaSalva, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Altera os dados de uma empresa existente no sistema.
     *
     * Este método realiza as seguintes operações:
     * - Verifica se o ID da empresa foi informado. Se não, retorna um erro indicando que o ID é necessário para realizar a alteração.
     * - Busca a empresa no repositório. Se não for encontrada, retorna um erro indicando que a empresa não existe.
     * - Executa as validações comuns sobre a empresa. Se houver erros, retorna o erro apropriado.
     * - Atualiza as informações da empresa, mantendo as datas de contratação e último pagamento inalteradas.
     * - Salva a empresa atualizada no repositório e retorna a resposta com a empresa alterada e o status HTTP OK.
     *
     * @param id O ID da empresa a ser alterada.
     * @param empresa O objeto `Empresa` com os dados atualizados.
     * @return Um `ResponseEntity` contendo um objeto `Responses` com a empresa alterada e o status HTTP OK,
     *         ou um erro indicando problemas com a alteração.
     */
    public ResponseEntity<Responses> alterar(Long id, Empresa empresa){
        if(id == null || id == 0){
            Responses<Empresa> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_ID_NAO_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Empresa empresaConsultada = this.empresaRepository.findById(id).orElse(null);
        if(empresaConsultada == null){
            Responses<Empresa> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_EMPRESA_NAO_ENCONTRADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<Responses> erros = validacoesComuns(empresa);
        if(erros != null){
            return erros;
        }

        empresa.setAlteracao(new Date());
        empresa.setContratacao(empresaConsultada.getContratacao());
        empresa.setUltimoPagamento(empresaConsultada.getUltimoPagamento());
        empresa.setDesativacao(empresaConsultada.getDesativacao());

        Empresa savedCargo = this.empresaRepository.save(empresa);

        Responses<Empresa> response = new Responses<>(HttpStatus.OK.value(), savedCargo, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Desativa uma empresa existente no sistema pelo seu ID.
     *
     * Este método realiza as seguintes operações:
     * - Busca a empresa no repositório pelo ID. Se não for encontrada, retorna um erro indicando que a empresa não existe.
     * - Define as datas de alteração e desativação da empresa como a data atual.
     * - Salva a empresa desativada no repositório e retorna a resposta com o status HTTP ACCEPTED.
     *
     * @param id O ID da empresa a ser desativada.
     * @return Um `ResponseEntity` contendo um objeto `Responses` com o status HTTP ACCEPTED,
     *         ou um erro indicando que a empresa não foi encontrada.
     */
    public ResponseEntity<Responses> desativarPorID(Long id) {

        Empresa empresa = this.empresaRepository.findById(id).orElse(null);

        if(empresa != null){
            empresa.setAlteracao(new Date());
            empresa.setDesativacao(new Date());
            this.empresaRepository.save(empresa);
        } else{
            Responses<Empresa> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_EMPRESA_NAO_ENCONTRADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Responses<Empresa> response = new Responses<>(HttpStatus.ACCEPTED.value(), null, null);
        return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
    }

    /**
     * Lista todas as empresas ativas no sistema.
     *
     * Este método realiza as seguintes operações:
     * - Busca todas as empresas ativas (aquelas que não possuem data de desativação) no repositório, ordenadas pelo nome.
     * - Retorna uma lista das empresas ativas com o status HTTP OK.
     *
     * @return Um `ResponseEntity` contendo um objeto `ResponsesList` com a lista de empresas ativas e o status HTTP OK.
     */
    public ResponseEntity<ResponsesList> listarTodosAtivos() {
        List<Empresa> empresa = this.empresaRepository.findByDesativacaoIsNull(Sort.by("nome").ascending());
        ResponsesList<Empresa> response = new ResponsesList<>(HttpStatus.OK.value(), empresa, 0, 0, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Lista todas as empresas desativadas no sistema.
     *
     * Este método realiza as seguintes operações:
     * - Busca todas as empresas desativadas (aquelas que possuem data de desativação) no repositório.
     * - Retorna uma lista das empresas desativadas com o status HTTP OK.
     *
     * @return Um `ResponseEntity` contendo um objeto `ResponsesList` com a lista de empresas desativadas e o status HTTP OK.
     */
    public ResponseEntity<ResponsesList> listarTodosDesativados() {
        List<Empresa> empresa = this.empresaRepository.findByDesativacaoIsNotNull();
        ResponsesList<Empresa> response = new ResponsesList<>(HttpStatus.OK.value(), empresa, 0, 0, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Reativa uma empresa desativada no sistema pelo seu ID.
     *
     * Este método realiza as seguintes operações:
     * - Busca a empresa desativada no repositório pelo ID. Se não for encontrada, retorna um erro indicando que a empresa não existe.
     * - Remove a data de desativação da empresa, reativando-a.
     * - Salva a empresa reativada no repositório e retorna a resposta com o status HTTP OK.
     *
     * @param id O ID da empresa a ser reativada.
     * @return Um `ResponseEntity` contendo um objeto `Responses` com a empresa reativada e o status HTTP OK,
     *         ou um erro indicando que a empresa não foi encontrada.
     */
    public ResponseEntity<Responses> reativar(Long id) {
        Empresa empresa = this.empresaRepository.findByIdAndDesativacaoIsNotNull(id);

        if(empresa != null){
            empresa.setAlteracao(new Date());
            empresa.setDesativacao(null);
            this.alterar(id, empresa);
        } else{
            Responses<Empresa> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_EMPRESA_NAO_ENCONTRADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Responses<Empresa> response = new Responses<>(HttpStatus.OK.value(), empresa, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Efetua o pagamento para uma empresa desativada no sistema pelo seu ID.
     *
     * Este método realiza as seguintes operações:
     * - Busca a empresa desativada no repositório pelo ID. Se não for encontrada, retorna um erro indicando que a empresa não existe.
     * - Atualiza a data do último pagamento e a data de alteração da empresa.
     * - Salva a empresa atualizada no repositório e retorna a resposta com o status HTTP OK.
     *
     * @param id O ID da empresa para a qual o pagamento será efetuado.
     * @return Um `ResponseEntity` contendo um objeto `Responses` com a empresa atualizada e o status HTTP OK,
     *         ou um erro indicando que a empresa não foi encontrada.
     */
    public ResponseEntity<Responses> efetuarPagamento(Long id) {
        Empresa empresa = this.empresaRepository.findByIdAndDesativacaoIsNotNull(id);

        if(empresa != null){
            empresa.setAlteracao(new Date());
            empresa.setUltimoPagamento(new Date());
            this.alterar(id, empresa);
        } else{
            Responses<Empresa> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_EMPRESA_NAO_ENCONTRADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Responses<Empresa> response = new Responses<>(HttpStatus.OK.value(), empresa, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Lista uma empresa pelo seu ID.
     *
     * Este método realiza as seguintes operações:
     * - Busca a empresa no repositório pelo ID.
     * - Retorna a empresa encontrada com o status HTTP OK.
     *
     * @param id O ID da empresa a ser listada.
     * @return Um `ResponseEntity` contendo um objeto `Responses` com a empresa encontrada e o status HTTP OK,
     *         ou `null` se a empresa não for encontrada.
     */
    public ResponseEntity<Responses> listarPorID(Long id) {
        Empresa cargo = this.empresaRepository.findById(id)
                .orElse(null);

        Responses<Empresa> response = new Responses<>(HttpStatus.OK.value(), cargo, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Realiza as validações comuns ao cadastrar ou alterar uma empresa.
     *
     * Este método verifica se:
     * - O nome da empresa foi informado. Caso contrário, retorna um erro.
     * - O servidor da empresa foi informado. Caso contrário, retorna um erro.
     * - O responsável pela empresa foi informado. Caso contrário, retorna um erro.
     *
     * @param empresa O objeto `Empresa` a ser validado.
     * @return Um `ResponseEntity` com o erro correspondente, ou `null` se não houver erros.
     */
    private ResponseEntity<Responses> validacoesComuns(Empresa empresa){
        if(empresa.getNome() == null || empresa.getNome().isBlank()){
            Responses<Empresa> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_NOME_VAZIO_OU_NAO_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(empresa.getServidor() == null || empresa.getServidor().isBlank()){
            Responses<Empresa> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_SERVIDOR_VAZIO_OU_NAO_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(empresa.getResponsavel() == null){
            Responses<Empresa> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_SERVIDOR_VAZIO_OU_NAO_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}
