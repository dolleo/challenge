package com.ldol.challenge.web.rest.mapper;

import com.ldol.challenge.domain.City;
import com.ldol.challenge.web.rest.dto.CityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityDTO toDTO(City city);

    City toEntity(CityDTO cityDTO);
}
