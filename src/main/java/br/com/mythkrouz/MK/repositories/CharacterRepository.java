package br.com.mythkrouz.MK.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    public List<Character> findByUniverseId(Long universeId);
    public List<Character> findByRace(String race);
    public List<Character> findByGender(String gender);
    public List<Character> findByRaceAndGender(String race, String gender);
    public List<Character> findByCharacterClass(String characterClass);



}
