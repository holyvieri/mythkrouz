package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    public List<Item> findByType(String type);
    public List<Item> findByRarity(String rarity);
    public List<Item> findByOwnerId(Long ownerId);
    public List<Item> findByUniverseId(Long universeId);
}
