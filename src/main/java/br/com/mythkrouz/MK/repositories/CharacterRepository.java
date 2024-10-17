package br.com.mythkrouz.MK.repositories;

import br.com.mythkrouz.MK.entities.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<br.com.mythkrouz.MK.entities.Character, Long> {
    List<Character> findByUniverse_UniverseId(Long universeId);
    public Optional<Character> findByName(String name);
    public List<Character> findAllByRace(String race);
    public List<Character> findAllByGender(String gender);
    public List<Character> findAllByRaceAndGender(String race, String gender);
    public List<Character> findAllByCharacterClass(String characterClass);



}
