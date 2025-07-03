package com.moonkeyeu.core.api.launch.services.impl;

import com.moonkeyeu.core.api.launch.dto.filters.FiltersDTO;
import com.moonkeyeu.core.api.launch.model.views.BaseFilter;
import com.moonkeyeu.core.api.launch.repository.filters.FiltersRepository;
import com.moonkeyeu.core.api.launch.services.FiltersService;
import com.moonkeyeu.core.api.configuration.utils.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FiltersServiceImpl implements FiltersService {
    private final FiltersRepository filtersRepository;
    private final DtoConverter dtoConverter;
    @Autowired
    public FiltersServiceImpl(FiltersRepository filtersRepository, DtoConverter dtoConverter) {
        this.filtersRepository = filtersRepository;
        this.dtoConverter = dtoConverter;
    }

    @Cacheable(value = "filters-cache", key = "'launch-filters'", sync = true)
    @Override
    public Map<String, Object> getLaunchFilters() {
        Map<String, List<FiltersDTO>> data = new HashMap<>();
        List<BaseFilter> launchFiltersViewList = filtersRepository.findAllLaunchFilters();
        data.put("agencies",
                fetchAndTransform(fetchCategorizedFilters(launchFiltersViewList, "agencies"), FiltersDTO.class));
        data.put("astronauts",
                fetchAndTransform(fetchCategorizedFilters(launchFiltersViewList, "astronauts"), FiltersDTO.class));
        data.put("launchers",
                fetchAndTransform(fetchCategorizedFilters(launchFiltersViewList, "launchers"), FiltersDTO.class));
        data.put("locations",
                fetchAndTransform(fetchCategorizedFilters(launchFiltersViewList, "locations"), FiltersDTO.class));
        data.put("rocket_configurations",
                fetchAndTransform(fetchCategorizedFilters(launchFiltersViewList, "rocket_configurations"), FiltersDTO.class));
        data.put("spacecraft_configurations",
                fetchAndTransform(fetchCategorizedFilters(launchFiltersViewList, "spacecraft_configurations"), FiltersDTO.class));

        Map<String, Object> allCommonData = new HashMap<>();
        allCommonData.put("data", data);

        return allCommonData;
    }
    @Cacheable(value = "filters-cache", key = "'astronaut-filters'", sync = true)
    @Override
    public Map<String, Object> getAstronautFilters() {
        Map<String, List<FiltersDTO>> data = new HashMap<>();
        List<BaseFilter> astronautFiltersViewList = filtersRepository.findAllAstronautFilters();
        data.put("status",
                fetchAndTransform(fetchCategorizedFilters(astronautFiltersViewList, "astronaut_status"), FiltersDTO.class));
        data.put("nationalities",
                fetchAndTransform(fetchCategorizedFilters(astronautFiltersViewList, "nationality"), FiltersDTO.class));
        data.put("agencies",
                fetchAndTransform(fetchCategorizedFilters(astronautFiltersViewList, "agencies"), FiltersDTO.class));

        Map<String, Object> allCommonData = new HashMap<>();
        allCommonData.put("data", data);

        return allCommonData;
    }


    private List<BaseFilter> fetchCategorizedFilters(List<BaseFilter> launchFiltersViewList, String category){
        return launchFiltersViewList
                .stream()
                .filter(filter -> filter.getFilterType().equals(category))
                .sorted(Comparator.comparing(BaseFilter::getFilterName))
                .collect(Collectors.toList());
    }

    private <T, R> List<R> fetchAndTransform(List<T> entities, Class<R> dtoClass) {
        return entities.stream()
                .map(entity -> dtoConverter.convertToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

}
