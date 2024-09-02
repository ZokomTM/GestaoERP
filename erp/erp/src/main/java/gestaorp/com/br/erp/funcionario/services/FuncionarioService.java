package gestaorp.com.br.erp.funcionario.services;

import gestaorp.com.br.erp.exeptions.ErrorExeptions;
import gestaorp.com.br.erp.funcionario.entities.FuncionarioDTO;
import gestaorp.com.br.erp.funcionario.entities.FuncionarioCadastro;
import gestaorp.com.br.erp.funcionario.model.FuncionarioRepository;
import gestaorp.com.br.erp.responses.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;


    public ResponseEntity<Responses> cadastrar(FuncionarioCadastro funcionario){
        if(funcionario.id() != null){
            Responses<FuncionarioCadastro> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_ID_FOI_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Responses> erros = validacoesComuns(funcionario);
        if(erros != null){
            return erros;
        }

        FuncionarioDTO novoFuncionario = new FuncionarioDTO(funcionario);
        novoFuncionario.setSolicitaPrimeiroAcesso(true);
        novoFuncionario.setContratacao(new Date());
        novoFuncionario.setAlteracao(new Date());
        FuncionarioDTO funcionarioSalvo = this.funcionarioRepository.save(novoFuncionario);

        Responses<FuncionarioDTO> response = new Responses<>(HttpStatus.CREATED.value(), funcionarioSalvo, null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<Responses> alterar(FuncionarioCadastro funcionario){
        if(funcionario.id() == null){
            Responses<FuncionarioCadastro> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_ID_NAO_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        ResponseEntity<Responses> erros = validacoesComuns(funcionario);
        if(erros != null){
            return erros;
        }

        FuncionarioDTO funcionarioParaAlteracao = this.funcionarioRepository.findById(funcionario.id()).orElse(null);
        if(funcionarioParaAlteracao == null){
            Responses<FuncionarioCadastro> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_FUNCIONARIO_NAO_ENCONTRADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        funcionarioParaAlteracao.setNome(funcionario.nome());
        funcionarioParaAlteracao.setTelefone(funcionario.telefone());
        funcionarioParaAlteracao.setEmail(funcionario.email());
        funcionarioParaAlteracao.setWebhook(funcionario.webhook());
        funcionarioParaAlteracao.setDiscordID(funcionario.discordID());
        this.funcionarioRepository.save(funcionarioParaAlteracao);

        Responses<FuncionarioDTO> response = new Responses<>(HttpStatus.OK.value(), funcionarioParaAlteracao, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<Responses> validacoesComuns(FuncionarioCadastro funcionario){
        if(funcionario.nome() == null || funcionario.nome().isBlank()){
            Responses<FuncionarioCadastro> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_NOME_VAZIO_OU_NAO_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        if(funcionario.email() == null || funcionario.email().isBlank()){
            Responses<FuncionarioCadastro> response = new Responses<>(HttpStatus.BAD_REQUEST.value(), null, ErrorExeptions.ERROR_EMAIL_NAO_INFORMADO);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return null;
    }
}
