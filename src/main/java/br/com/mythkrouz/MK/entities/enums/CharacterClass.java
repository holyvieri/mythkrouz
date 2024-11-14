package br.com.mythkrouz.MK.entities.enums;

public enum CharacterClass {
    WARRIOR("Guerreiro"),
    MAGE("Mago"),
    ARCHER("Arqueiro"),
    ROGUE("Ladino"),
    PALADIN("Paladino"),
    BERSERKER("Berserker"),
    CLERIC("Clérigo"),
    DRUID("Druida"),
    NECROMANCER("Necromante"),
    ASSASSIN("Assassino"),
    BARD("Bardo"),
    MONK("Monge"),
    RANGER("Guardião"),
    SORCERER("Feiticeiro"),
    WARLOCK("Bruxo");

    private final String displayName;

    CharacterClass(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
