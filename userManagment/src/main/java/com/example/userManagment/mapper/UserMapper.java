package com.example.userManagment.mapper;

import org.mapstruct.Mapper;
import com.example.userManagment.dto.CreateEmployeeDTO;
import com.example.userManagment.dto.UpdateUserDTO;
import com.example.userManagment.dto.UserDTO;
import com.example.userManagment.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {




}
/*package com.example.check_in.management.mapper;

import org.mapstruct.Mapper;

import com.example.check_in.management.dto.CreateCheckInDTO;
import com.example.check_in.management.dto.UpdateCheckInDTO;
import com.example.check_in.management.dto.CheckInUserDTO;
import com.example.check_in.management.models.CheckIn;

@Mapper(componentModel = "spring")
public interface CheckInMapper {
    
    CheckIn toEntity(CreateCheckInDTO checkInDTO);

    CheckInUserDTO toUserDTO(CheckIn checkIn);

    CreateCheckInDTO getCheckInDto(CheckIn checkIn);

    CheckIn updateToEntity(UpdateCheckInDTO checkInDTO);


} */