package br.com.mythkrouz.MK.entities.enums;

public enum Type {
    WEAPON("Arma"),
    ARMOR("Armadura"),
    POTION("Poção"),
    SCROLL("Pergaminho"),
    ACCESSORY("Acessório"),
    RING("Anel"),
    AMULET("Amuleto"),
    SHIELD("Escudo"),
    WAND("Varinha"),
    STAFF("Cajado"),
    GEM("Gema"),
    ARTIFACT("Artefato"),
    TOOL("Ferramenta"),
    CONSUMABLE("Consumível"),
    MATERIAL("Material"),
    TREASURE("Tesouro");

    private final String displayName;

    Type(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
