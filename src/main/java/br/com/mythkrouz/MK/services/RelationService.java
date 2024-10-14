package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.entities.Relation;

import java.util.List;
import java.util.Optional;

public interface RelationService {
    public Relation createRelation(Relation relation) throws Exception;
    public Relation updateRelation(Relation relation);
    public void deleteRelation(Long id);
    public Optional<Relation> getRelationById(Long id);
    public List<Relation> getAllRelations();
    public List<Relation> getRelationsByCharacterId(Long characterId);
}
