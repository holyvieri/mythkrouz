package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    public Optional<Item> findByName(String name);
    public List<Item> findAllByType(String type);
    public List<Item> findAllByRarity(String rarity);
    public List<Item> findAllByOwner_CharacterId(Long ownerId);
    public List<Item> findAllByTerritoryId(Long territoryId);
}
