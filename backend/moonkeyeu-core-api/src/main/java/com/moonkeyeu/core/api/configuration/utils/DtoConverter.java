package com.moonkeyeu.core.api.configuration.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <D> D convertToDto(Object obj, Class<D> dtoClass) {
            return modelMapper.map(obj, dtoClass);
    }
    public <E> E convertToEntity(Object obj, Class<E> entityClass) {
            return modelMapper.map(obj, entityClass);
        }
}
