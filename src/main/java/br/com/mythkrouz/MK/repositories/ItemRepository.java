package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    public Optional<Item> findItemByName(String name);
    public List<Item> findByType(String type);
    public List<Item> findByRarity(String rarity);
    public List<Item> findByOwner_CharacterId(Long ownerId);
    public List<Item> findByUniverse_UniverseId(Long universeId);
}
