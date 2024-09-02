package gestaorp.com.br.erp.funcionario.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import gestaorp.com.br.erp.cargo.entities.Cargo;
import gestaorp.com.br.erp.empresa.entities.EmpresaDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class FuncionarioDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String telefone;

    private String webhook;

    private String discordID;

    private Boolean solicitaPrimeiroAcesso;

    private String email;

    private String nomeUsuario;

    private String password;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private List<EmpresaDTO> empresa;

    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date contratacao;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date demisao;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private Date alteracao;

    public void adicionarNovaEmpresa(EmpresaDTO empresaNova){
        this.empresa.add(empresaNova);
    }

    public FuncionarioDTO(FuncionarioCadastro funcionarioNovo){
        this.id = funcionarioNovo.id();
        this.nome = funcionarioNovo.nome();
        this.telefone = funcionarioNovo.telefone();
        this.email = funcionarioNovo.email();
        this.webhook = funcionarioNovo.webhook();
        this.discordID = funcionarioNovo.discordID();
        this.empresa.add(funcionarioNovo.empresa());
    }
}