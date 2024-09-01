package gestaorp.com.br.erp.responses;

import gestaorp.com.br.erp.cargos.exeptions.ErrorExeptions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsesList<T> {
    private int statusCode;
    private List<T> data;
    private Integer page;
    private Integer sizePage;
    private ErrorExeptions error;
}