package gestaorp.com.br.erp.cargos.exeptions;

import lombok.Getter;

@Getter
public enum ErrorExeptions {
    ERROR_COMISSAO_MENOR_ZERO("Comissão informada é menor que 0%!"),
    ERROR_COMISSAO_MAIOR_CEM("Comissão informada é maior que 100%!"),
    ERROR_COMISSAO_NAO_INFORMADA("Comissão não foi informada!"),
    ERROR_ID_NAO_INFORMADO("Para alteração ID deve ser informado!"),
    ERROR_ID_FOI_INFORMADO("Para cadastro ID não pode ser informado!"),
    ERROR_CARGO_NAO_ENCONTRADO("O cargo informado para alteração não foi encontrado!"),
    ERROR_NOME_VAZIO_OU_NAO_INFORMADO("Nome do cargo está vazio ou não foi informado!");

    private final String mensagem;

    ErrorExeptions(String mensagem) {
        this.mensagem = mensagem;
    }
}