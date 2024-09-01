package gestaorp.com.br.erp.cargo.controller;

import gestaorp.com.br.erp.cargo.entities.Cargo;
import gestaorp.com.br.erp.responses.Responses;
import gestaorp.com.br.erp.cargo.services.CargosService;
import gestaorp.com.br.erp.responses.ResponsesList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cargos")
public class CargosController {

    @Autowired
    private CargosService cargosService;

    @PostMapping("")
    public ResponseEntity<Responses> cadastrar(@RequestBody Cargo cargo) {
        return this.cargosService.cadastrar(cargo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Responses> alterar(@PathVariable Long id, @RequestBody Cargo cargo) {
        return this.cargosService.alterar(id, cargo);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Responses> reativar(@PathVariable Long id) {
        return this.cargosService.reativar(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Responses> removerPorID(@PathVariable Long id) {
        return this.cargosService.removerPorID(id);
    }

    @GetMapping("")
    public ResponseEntity<ResponsesList> listarTodos() {
        return this.cargosService.listarTodosAtivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Responses> listarPorID(@PathVariable Long id) {
        return this.cargosService.listarPorID(id);
    }

    @GetMapping("/desativados")
    public ResponseEntity<ResponsesList> listarDesativados() {
        return this.cargosService.listarTodosDesativados();
    }
}
