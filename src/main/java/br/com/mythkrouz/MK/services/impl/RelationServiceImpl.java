package br.com.mythkrouz.MK.services.impl;

import br.com.mythkrouz.MK.entities.Relation;
import br.com.mythkrouz.MK.entities.enums.RelationType;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;
import br.com.mythkrouz.MK.repositories.RelationRepository;
import br.com.mythkrouz.MK.services.RelationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RelationServiceImpl implements RelationService {

    RelationRepository relationRepository;

    @Autowired
    public RelationServiceImpl(RelationRepository relationRepository) {
        this.relationRepository = relationRepository;
    }

    @Transactional
    @Override
    public Relation createRelation(Relation relation) throws EntityAlreadyExistsException {
        //os envolvidos nao podem ser nulos ne
        if (relation.getCharacter1() == null || relation.getCharacter2() == null) {
            throw new IllegalArgumentException("Os personagens envolvidos não podem ser nulos.");
        }

        //vê agr se já existe pelo menos uma relação entre c1 e c2
        List<Relation> existingRelations = relationRepository.findAllByCharacter1_CharacterIdOrCharacter2_CharacterId(
                relation.getCharacter1().getCharacterId(), relation.getCharacter2().getCharacterId()
        );

        //vai checar agr cada relação deles p ver se é igual
        for (Relation existingRelation : existingRelations) {
            if (existingRelation.getCharacter1().getCharacterId().equals(relation.getCharacter2().getCharacterId())
            && existingRelation.getCharacter2().getCharacterId().equals(relation.getCharacter1().getCharacterId())) {
                throw new EntityAlreadyExistsException("Relacionamento");
            }
        }

        //se n for engyal, vai criar no bd
        return relationRepository.save(relation);
    }

    @Transactional
    @Override
    public Relation updateRelation(Relation relation) {
        Optional<Relation> existingRelation = relationRepository.findById(relation.getRelationId());

        if (existingRelation.isPresent()) {
            return relationRepository.save(relation);
        }else{
            throw new EntityNotFoundException("Relação com ID " + relation.getRelationId()+ " não encontrada.");
        }
    }

    @Transactional
    @Override
    public void deleteRelation(Long relationId) {
        relationRepository.deleteById(relationId);
    }

    @Override
    public Optional<Relation> getRelationById(Long relationId) {
        return relationRepository.findById(relationId);
    }

    @Override
    public List<Relation> getAllRelations() {
        return relationRepository.findAll();
    }

    @Override
    public List<Relation> getAllRelationsByCharacterId(Long characterId) {
        return relationRepository.findAllByCharacter1_CharacterIdOrCharacter2_CharacterId(characterId, characterId);
    }

    @Override
    public List<Relation> getAllRelationsByRelationType(RelationType relationType) {
        return relationRepository.findAllByRelationType(relationType);
    }
}
