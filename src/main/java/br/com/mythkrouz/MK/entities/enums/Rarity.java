package br.com.mythkrouz.MK.entities.enums;

public enum Rarity {
    COMMON("Comum"),
    UNCOMMON("Incomum"),
    RARE("Raro"),
    EPIC("Épico"),
    LEGENDARY("Lendário"),
    MYTHIC("Mítico"),
    UNIQUE("Único"),
    ANCIENT("Antigo"),
    CURSED("Amaldiçoado");

    private final String displayName;

    Rarity(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
