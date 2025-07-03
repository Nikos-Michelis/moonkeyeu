package com.moonkeyeu.core.api.launch.repository.filters;

import com.moonkeyeu.core.api.launch.model.views.BaseFilter;
import com.moonkeyeu.core.api.launch.model.views.LaunchFiltersView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FiltersRepository extends JpaRepository<LaunchFiltersView, String> {
    @Query("SELECT fv FROM LaunchFiltersView fv")
    List<BaseFilter> findAllLaunchFilters();
    @Query("SELECT fv FROM AstronautFiltersView fv")
    List<BaseFilter> findAllAstronautFilters();


}
