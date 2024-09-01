package gestaorp.com.br.erp.cargos.controller;

import gestaorp.com.br.erp.cargos.entities.Cargos;
import gestaorp.com.br.erp.cargos.exeptions.Responses;
import gestaorp.com.br.erp.cargos.services.CargosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cargos/")
public class CargosController {

    @Autowired
    private CargosService cargosService;

    @PostMapping("")
    public ResponseEntity<Responses> cadastrar(@RequestBody Cargos cargo) {
        return this.cargosService.cadastrar(cargo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Responses> alterar(@RequestParam Long id, @RequestBody Cargos cargo) {
        return this.cargosService.alterar(id, cargo);
    }
}
