package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.Relation;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface RelationService {
    public Relation createRelation(Relation relation) throws EntityAlreadyExistsException;
    public Relation updateRelation(Relation relation);
    public void deleteRelation(Long relationId);
    public Optional<Relation> getRelationById(Long relationId);
    public List<Relation> getAllRelations();
    public List<Relation> getRelationsByCharacterId(Long characterId);
}
