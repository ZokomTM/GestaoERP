package gestaorp.com.br.erp.empresa.controller;

import gestaorp.com.br.erp.empresa.entities.EmpresaDTO;
import gestaorp.com.br.erp.empresa.services.EmpresaService;
import gestaorp.com.br.erp.responses.Responses;
import gestaorp.com.br.erp.responses.ResponsesList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cargos")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping("")
    public ResponseEntity<Responses> cadastrar(@RequestBody EmpresaDTO cargo) {
        return this.empresaService.cadastrar(cargo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Responses> alterar(@PathVariable Long id, @RequestBody EmpresaDTO cargo) {
        return this.empresaService.alterar(id, cargo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Responses> reativar(@PathVariable Long id) {
        return this.empresaService.reativar(id);
    }

    @PatchMapping("/pagamento/{id}")
    public ResponseEntity<Responses> efetuarPagamento(@PathVariable Long id) {
        return this.empresaService.efetuarPagamento(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Responses> removerPorID(@PathVariable Long id) {
        return this.empresaService.desativarPorID(id);
    }

    @GetMapping("")
    public ResponseEntity<ResponsesList> listarTodos() {
        return this.empresaService.listarTodosAtivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Responses> listarPorID(@PathVariable Long id) {
        return this.empresaService.listarPorID(id);
    }

    @GetMapping("/desativados")
    public ResponseEntity<ResponsesList> listarDesativados() {
        return this.empresaService.listarTodosDesativados();
    }
}
