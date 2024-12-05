package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Item;
import br.com.mythkrouz.MK.entities.enums.Rarity;
import br.com.mythkrouz.MK.entities.enums.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT i FROM Item i WHERE i.name LIKE %:name%")
    public Optional<Item> findByName(@Param("name") String name);

    public List<Item> findAllByType(Type type);
    public List<Item> findAllByRarity(Rarity rarity);
    public List<Item> findAllByOwners_CharacterId(Long ownerId);
    public List<Item> findAllByOrigin_TerritoryId(Long territoryId);
}
