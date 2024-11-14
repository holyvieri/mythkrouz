package br.com.mythkrouz.MK.entities.enums;

public enum Gender {
    MALE("Masculino"),
    FEMALE("Feminino"),
    NON_BINARY("Não-Binário"),
    ANDROGYNOUS("Andrógino"),
    GENDERLESS("Sem Gênero"),
    TRANS_MALE("Homem Trans"),
    TRANS_FEMALE("Mulher Trans"),
    OTHER("Outro");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
