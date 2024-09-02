package gestaorp.com.br.erp.exeptions;

import lombok.Getter;

@Getter
public enum ErrorExeptions {
    /**
     * ERROS PADRÕES
     * */
    ERROR_COMISSAO_MENOR_ZERO("Comissão informada é menor que 0%!"),
    ERROR_COMISSAO_MAIOR_CEM("Comissão informada é maior que 100%!"),
    ERROR_COMISSAO_NAO_INFORMADA("Comissão não foi informada!"),
    ERROR_FUNCIONARIO_JA_CADASTRADO_PARA_ESSA_EMPRESA("Usuário já está cadastrado para essa empresa!"),
    ERROR_QUANTIDADE_FUNCIONARIO_NAO_INFORMADA("Quantidade de funcionarios não foi informada!"),
    ERROR_QUANTIDADE_FUNCIONARIO_ZERADO("Quantidade de funcionarios não pode ser 0!"),
    ERROR_EMAIL_NAO_INFORMADO("E-mail não foi informado!"),
    ERROR_NOME_USUARIO_NAO_INFORMADO("Nome de usuário não foi informado!"),
    ERROR_SENHA_NAO_INFORMADO("Senha não foi informado informado!"),
    ERROR_NOME_USUARIO_NAO_PODE_SER_INFORMADO("Nome de usuário não pode ser informado!"),
    ERROR_SENHA_NAO_PODE_SER_INFORMADO("Senha não pode ser informado!"),
    ERROR_ID_NAO_INFORMADO("Para alteração ID deve ser informado!"),
    ERROR_ID_FOI_INFORMADO("Para cadastro ID não pode ser informado!"),
    ERROR_CARGO_NAO_ENCONTRADO("O cargo informado para alteração não foi encontrado!"),
    ERROR_EMPRESA_NAO_ENCONTRADO("A empresa informada para alteração não foi encontrado!"),
    ERROR_NOME_VAZIO_OU_NAO_INFORMADO("Nome do cargo está vazio ou não foi informado!"),
    ERROR_FUNCIONARIO_NAO_ENCONTRADO("Funcionário não foi encontrado!"),
    ERROR_SERVIDOR_VAZIO_OU_NAO_INFORMADO("Nome do servidor está vazio ou não foi informado!");

    private final String mensagem;

    ErrorExeptions(String mensagem) {
        this.mensagem = mensagem;
    }
}