package gestaorp.com.br.erp.funcionario.entities;

import gestaorp.com.br.erp.empresa.entities.EmpresaDTO;

public record FuncionarioCadastro(
        Long id,
        String nome,
        String telefone,
        String email,
        String webhook,
        String discordID,
        EmpresaDTO empresa
) {
}
