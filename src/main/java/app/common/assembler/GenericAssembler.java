package app.common.assembler;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface GenericAssembler<D,E> {

    E toVO(D dto);

    D toEntity(E entity);

    default List<E> toVOs(final Collection<D> entities) {
        return entities.stream()
                .map(this::toVO)
                .collect(Collectors.toList());
    }

    default List<D> toEntities(final Collection<E> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
