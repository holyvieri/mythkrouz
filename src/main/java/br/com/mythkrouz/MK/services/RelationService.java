package br.com.mythkrouz.MK.services;

import br.com.mythkrouz.MK.dto.RelationDTO;
import br.com.mythkrouz.MK.entities.Relation;
import br.com.mythkrouz.MK.entities.enums.RelationType;
import br.com.mythkrouz.MK.exceptions.EntityAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface RelationService {
    public RelationDTO createRelation(RelationDTO relation, String userEmail) throws EntityAlreadyExistsException;
    public RelationDTO updateRelation(Long id, RelationDTO relation, String userEmail);
    public void deleteRelation(Long relationId, String userEmail);
    public Optional<Relation> getRelationById(Long relationId, String userEmail);
    public List<RelationDTO> getAllRelations();
    public List<RelationDTO> getAllRelationsByCharacterId(Long characterId);
    public List<RelationDTO> getAllRelationsByRelationType(RelationType relationType);

}
