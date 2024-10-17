package br.com.mythkrouz.MK.entities.dtos;

import br.com.mythkrouz.MK.entities.Character;
import br.com.mythkrouz.MK.entities.Event;
import br.com.mythkrouz.MK.entities.Item;
import br.com.mythkrouz.MK.entities.Relation;
import br.com.mythkrouz.MK.entities.Universe;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CharacterDTO {

        private Long characterId;
        private String name;
        private String race;
        private String characterClass;
        private String gender;
        private int age;
        private String description;
        private List<Long> itemId;
        private List<Long> relationId;
        private Long universeId;
        private List<Long> eventId;

        public CharacterDTO(Character character) {
                this.characterId = character.getCharacterId();
                this.name = character.getName();
                this.race = character.getRace();
                this.gender = character.getGender();
                this.age = character.getAge();
                this.description = character.getDescription();
                this.itemId = new ArrayList<>();
                this.relationId = new ArrayList<>();
                this.universeId = character.getUniverse().getUniverseId();
                this.eventId = new ArrayList<>();

        }

}
