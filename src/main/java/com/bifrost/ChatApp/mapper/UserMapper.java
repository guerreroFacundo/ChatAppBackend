package com.bifrost.ChatApp.mapper;

import com.bifrost.ChatApp.dto.UserDTO;
import com.bifrost.ChatApp.entitie.User;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Named("toDto")
    UserDTO userToUserDTO(User user);

    @Named("toEntity")
    User userDTOToUser(UserDTO userDTO);

    @IterableMapping(qualifiedByName = "toDto")
    public List<UserDTO> getDTOUserList(List<User> entityList);

    @IterableMapping(qualifiedByName = "toEntity")
    public List<User> getUserList(List<UserDTO> dtoList);
}
