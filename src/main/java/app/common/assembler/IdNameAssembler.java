package app.common.assembler;

import app.common.vo.IdNameVO;

/**
 * Created by Dachu on 2015-11-19.
 */
public interface IdNameAssembler {
    IdNameVO toVO(Long id, String name);
}
