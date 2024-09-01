package gestaorp.com.br.erp.cargos.controller;

import gestaorp.com.br.erp.cargos.entities.Cargos;
import gestaorp.com.br.erp.cargos.exeptions.Responses;
import gestaorp.com.br.erp.cargos.services.CargosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cargos/")
@Tag(name = "Cargos", description = "Gerenciamento de cargos")
public class CargosController {

    @Autowired
    private CargosService cargosService;

    @PostMapping("")
    @Operation(summary = "Cadastrar Cargo", description = "Cadastra um novo cargo")
    public ResponseEntity<Responses> cadastrar(@RequestBody Cargos cargo) {
        return this.cargosService.cadastrar(cargo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar Cargo", description = "Altera um cargo existente")
    public ResponseEntity<Responses> alterar(@RequestParam Long id, @RequestBody Cargos cargo) {
        return this.cargosService.alterar(id, cargo);
    }
}
