package com.moonkeyeu.core.api.launch.services.impl.search;

import com.moonkeyeu.core.api.configuration.utils.CacheNames;
import com.moonkeyeu.core.api.launch.dto.launch.LaunchNormalDTO;
import com.moonkeyeu.core.api.launch.dto.paging.PageSortingDTO;
import com.moonkeyeu.core.api.launch.dto.program.ProgramDetailedDTO;
import com.moonkeyeu.core.api.launch.dto.program.ProgramSummarizedDTO;
import com.moonkeyeu.core.api.launch.model.launch.Launch;
import com.moonkeyeu.core.api.launch.model.program.Programs;
import com.moonkeyeu.core.api.launch.dto.DTOEntity;
import com.moonkeyeu.core.api.launch.repository.LaunchRepository;
import com.moonkeyeu.core.api.launch.repository.ProgramsRepository;
import com.moonkeyeu.core.api.launch.repository.specifications.ProgramSpecification;
import com.moonkeyeu.core.api.launch.services.ProgramsService;
import com.moonkeyeu.core.api.configuration.utils.DtoConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class ProgramsServiceImpl implements ProgramsService {

    private final ProgramsRepository programsRepository;
    private final LaunchRepository launchRepository;
    private final DtoConverter dtoConverter;
    @Autowired
    public ProgramsServiceImpl( DtoConverter dtoConverter, ProgramsRepository programsRepository, LaunchRepository launchRepository) {
        this.dtoConverter = dtoConverter;
        this.programsRepository = programsRepository;
        this.launchRepository = launchRepository;
    }

    @Override
    @Cacheable(value = CacheNames.PROGRAM_CACHE, key = "'program-pagination-' + #requestParams + '-' + #pageSortingDTO", sync = true)
    public Page<DTOEntity> searchProgram(Map<String, String> requestParams, PageSortingDTO pageSortingDTO) {
        Specification<Programs> spec = Specification.where(null);
        if (requestParams != null && !requestParams.isEmpty()) {
            if (requestParams.containsKey("search")) {
                String searchKey = requestParams.get("search");
                spec = spec.and(ProgramSpecification.hasSearchKey(searchKey));
            }
        }
        Sort sortObject = "desc".equalsIgnoreCase(pageSortingDTO.getSort())
                ? Sort.by(pageSortingDTO.getField()).descending()
                : Sort.by(pageSortingDTO.getField()).ascending();
        int page = (pageSortingDTO.getPage() > 0) ? pageSortingDTO.getPage() - 1 : 0;
        Pageable pageable = PageRequest.of(page, pageSortingDTO.getLimit(), sortObject);
        Page<Programs> programs = programsRepository.findAll(spec, pageable);
        return programs.map(program -> dtoConverter.convertToDto(program, ProgramSummarizedDTO.class));
    }
    @Override
    @Cacheable(value = CacheNames.PROGRAM_CACHE, key = "'program-' + #programId", sync = true)
    public Optional<DTOEntity> getProgramById(Integer programId) {
        Optional<Programs> programs = programsRepository.findProgramById(programId);
        Optional<Launch> launch = launchRepository.findUpcomingLaunchesByProgramId(programId);
        return programs.map(org -> {
            ProgramDetailedDTO dto = dtoConverter.convertToDto(org, ProgramDetailedDTO.class);
            launch.ifPresent(l -> {
                LaunchNormalDTO launchDTO = dtoConverter.convertToDto(l, LaunchNormalDTO.class);
                dto.setUpcomingLaunches(launchDTO);
            });
            return dto;
        });
    }
}
