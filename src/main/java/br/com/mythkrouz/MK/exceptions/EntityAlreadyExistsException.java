package br.com.mythkrouz.MK.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String entityName) {
        super("Essa entidade: "+ entityName + " já existe no Banco de Dados.");
    }
}
