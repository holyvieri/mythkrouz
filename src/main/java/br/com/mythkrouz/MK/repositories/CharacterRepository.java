package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Character;
import br.com.mythkrouz.MK.entities.enums.CharacterClass;
import br.com.mythkrouz.MK.entities.enums.Gender;
import br.com.mythkrouz.MK.entities.enums.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<br.com.mythkrouz.MK.entities.Character, Long> {
    public List<Character> findAllByTerritory_TerritoryId(Long territoryId);
    public List<Character> findAllByEvents_EventId(Long eventId);

    @Query("SELECT c FROM Character c WHERE c.name LIKE %:name%")
    public Optional<Character> findByName(@Param("name") String name);

    public List<Character> findAllByRace(Race race);
    public List<Character> findAllByGender(Gender gender);
    public List<Character> findAllByRaceAndGender(Race race, Gender gender);
    public List<Character> findAllByCharacterClass(CharacterClass characterClass);



}
