package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.dto.RelationDTO;
import br.com.mythkrouz.MK.entities.Character;
import br.com.mythkrouz.MK.entities.Relation;
import br.com.mythkrouz.MK.entities.User;
import br.com.mythkrouz.MK.entities.enums.RelationType;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.mappers.RelationMapper;
import br.com.mythkrouz.MK.repositories.CharacterRepository;
import br.com.mythkrouz.MK.repositories.RelationRepository;
import br.com.mythkrouz.MK.repositories.UserRepository;
import br.com.mythkrouz.MK.services.RelationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RelationServiceImpl implements RelationService {

    private final RelationRepository relationRepository;
    private final UserRepository userRepository;
    private final CharacterRepository characterRepository;

    @Autowired
    public RelationServiceImpl(RelationRepository relationRepository, UserRepository userRepository, CharacterRepository characterRepository) {
        this.relationRepository = relationRepository;
        this.userRepository = userRepository;
        this.characterRepository = characterRepository;
    }

    @Transactional
    @Override
    public RelationDTO createRelation(RelationDTO relationDto, String userEmail) throws EntityAlreadyExistsException {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        // Busca os personagens envolvidos na relação
        Character character1 = characterRepository.findById(relationDto.character1Id())
                .orElseThrow(() -> new EntityNotFoundException("Personagem com ID " + relationDto.character1Id() + " não encontrado."));
        Character character2 = characterRepository.findById(relationDto.character2Id())
                .orElseThrow(() -> new EntityNotFoundException("Personagem com ID " + relationDto.character2Id() + " não encontrado."));

        // Verifica se a relação já existe no banco
        Optional<Relation> existingRelation = relationRepository.findByCharactersAndType(
                character1.getCharacterId(),
                character2.getCharacterId(),
                relationDto.relationType()
        );

        if (!existingRelation.isEmpty()) {
            throw new EntityAlreadyExistsException("A relação entre os personagens já existe.");
        }
        if (!userId.equals(existingRelation.get().getCharacter1().getTerritory().getUniverse().getCreator().getUserId()) &&
                !userId.equals(existingRelation.get().getCharacter2().getTerritory().getUniverse().getCreator().getUserId())
        ){
            throw new IllegalArgumentException("Usuário não autorizado a editar este item.");
        }

            // Cria uma nova relação
        Relation relation = new Relation();
        relation.setCharacter1(character1);
        relation.setCharacter2(character2);
        relation.setRelationType(relationDto.relationType());

        // Salva a relação no banco de dados
        Relation savedRelation = relationRepository.save(relation);

        // Retorna o DTO da relação salva
        return RelationMapper.toDTO(savedRelation);
    }

    @Transactional
    @Override
    public RelationDTO updateRelation(Long id, RelationDTO relationDto, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        Relation existingRelation = relationRepository.findById(relationDto.relationId())
                .orElseThrow(() -> new EntityNotFoundException("A relação de ID: " + id +
                        " não foi encontrada."));

        boolean character1 = userId.equals(existingRelation
                .getCharacter1().getTerritory().getUniverse().getCreator().getUserId());
        boolean character2 = userId.equals(existingRelation
                .getCharacter2().getTerritory().getUniverse().getCreator().getUserId());

        if (!(character1 && character2)){
            throw new IllegalArgumentException("Usuário não autorizado a acessar esta relação.");
        }

        if (relationDto.character1Id() != null) {
            Character characterOne = characterRepository.findById(relationDto.character1Id())
                    .orElseThrow(() -> new EntityNotFoundException("Personagem com ID: " + relationDto.character1Id() + " não encontrado."));
            existingRelation.setCharacter1(characterOne);
        }

        if (relationDto.character2Id() != null) {
            Character characterTwo = characterRepository.findById(relationDto.character2Id())
                    .orElseThrow(() -> new EntityNotFoundException("Personagem com ID: " + relationDto.character2Id() + " não encontrado."));
            existingRelation.setCharacter2(characterTwo);
        }

        if (relationDto.relationType() != null) {
            try {
                RelationType relationType = RelationType.valueOf(relationDto.relationType().toString().toUpperCase());
                existingRelation.setRelationType(relationType);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Tipo de relação inválido fornecido: " + relationDto.relationType());
            }
        }

        relationRepository.save(existingRelation);
        return RelationMapper.toDTO(existingRelation);
    }

    @Transactional
    @Override
    public void deleteRelation(Long relationId, String userEmail) {
        Relation existingRelation = relationRepository.findById(relationId)
                .orElseThrow(() -> new EntityNotFoundException("Relação de ID: "+ relationId+" não foi encontrada."));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        if (!userId.equals(existingRelation.getCharacter1().getTerritory().getUniverse().getCreator().getUserId()) &&
                !userId.equals(existingRelation.getCharacter2().getTerritory().getUniverse().getCreator().getUserId())
        ){
            throw new IllegalArgumentException("Usuário não autorizado a deletar esta relação.");
        }

        relationRepository.deleteById(relationId);
    }

    @Override
    public Optional<Relation> getRelationById(Long relationId, String userEmail) {
        Optional<Relation> existingRelation = relationRepository.findById(relationId);
        if (existingRelation.isEmpty()){
            return Optional.empty();
        }

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com email " + userEmail + " não encontrado."));
        Long userId = user.getUserId();

        boolean character1 = userId.equals(existingRelation.get()
                .getCharacter1().getTerritory().getUniverse().getCreator().getUserId());
        boolean character2 = userId.equals(existingRelation.get()
                .getCharacter2().getTerritory().getUniverse().getCreator().getUserId());

        if (!(character1 && character2)){
            throw new IllegalArgumentException("Usuário não autorizado a acessar esta relação.");
        }

        return existingRelation;
    }

    @Override
    public List<RelationDTO> getAllRelations() {
        List<Relation> relations= relationRepository.findAll();
        return relations.stream()
                .map(RelationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RelationDTO> getAllRelationsByCharacterId(Long characterId) {
        List<Relation> relations= relationRepository.findAllByCharacter1_CharacterIdOrCharacter2_CharacterId
                (characterId, characterId);
        return relations.stream()
                .map(RelationMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RelationDTO> getAllRelationsByRelationType(RelationType relationType) {
        List<Relation> relations= relationRepository.findAllByRelationType(relationType);
        return relations.stream()
                .map(RelationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
