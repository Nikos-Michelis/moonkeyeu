package com.moonkeyeu.core.api.launch.model.views;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "astronauts_filters_view", schema = "moonkey_db")
public class AstronautFiltersView extends BaseFilter{
}
