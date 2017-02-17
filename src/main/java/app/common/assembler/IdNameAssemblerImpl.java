package app.common.assembler;

import app.common.vo.IdNameVO;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: Dachu
 * Date: 10.07.15
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
@Component
public class IdNameAssemblerImpl implements IdNameAssembler {

    @Override
    public IdNameVO toVO(Long id, String name) {
        return IdNameVO.create(id, name);
    }
}