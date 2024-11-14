package br.com.mythkrouz.MK.entities.enums;

public enum RelationType {
    ALLY("Aliado"),
    ENEMY("Inimigo"),
    FRIEND("Amigo"),
    RIVAL("Rival"),
    MENTOR("Mentor"),
    APPRENTICE("Aprendiz"),
    FAMILY("Família"),
    SIBLING("Irmão/Irmã"),
    CHILD("Filho/Filha"),
    SPOUSE("Cônjuge"),
    LOVER("Amante"),
    COLLEAGUE("Colega"),
    PARTNER("Parceiro"),
    NEMESIS("Nêmesis"),
    PROTECTOR("Protetor"),
    PROTEGE("Protegido"),
    UNKNOWN("Desconhecido");

    private final String displayName;

    RelationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
