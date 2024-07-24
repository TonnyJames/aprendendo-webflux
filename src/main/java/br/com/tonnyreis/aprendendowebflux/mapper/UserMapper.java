package br.com.tonnyreis.aprendendowebflux.mapper;

import br.com.tonnyreis.aprendendowebflux.entity.User;
import br.com.tonnyreis.aprendendowebflux.model.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toEntity(final UserRequest request);

}
