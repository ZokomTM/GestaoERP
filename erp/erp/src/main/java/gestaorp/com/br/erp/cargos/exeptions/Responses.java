package gestaorp.com.br.erp.cargos.exeptions;

import gestaorp.com.br.erp.cargos.entities.Cargos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Responses<T> {

    private int statusCode;
    private T data;
    private ErrorExeptions error;
}
