import br.com.mythkrouz.MK.entities.Territory;
import br.com.mythkrouz.MK.entities.Universe;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface UniverseRepository extends JpaRepository<Universe, Long> {
    public List<Universe> findByCreatorId(Long creatorId);

    public Optional<Universe> findByTerritoryId(Long territoryId);

    public Optional<Universe> findByCharactersContains(Long characterId);
}
