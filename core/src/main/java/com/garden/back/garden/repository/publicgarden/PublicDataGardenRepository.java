package com.garden.back.garden.repository.publicgarden;

import com.garden.back.garden.domain.PublicDataGarden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicDataGardenRepository extends JpaRepository<PublicDataGarden, Long> {
}
