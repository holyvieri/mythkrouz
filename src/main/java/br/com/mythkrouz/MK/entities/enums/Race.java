package br.com.mythkrouz.MK.entities.enums;

public enum Race {
    HUMAN("Humano"),
    ELF("Elfo"),
    DWARF("Anão"),
    ORC("Orc"),
    TROLL("Troll"),
    GOBLIN("Goblin"),
    OGRE("Ogro"),
    VAMPIRE("Vampiro"),
    WEREWOLF("Lobisomem"),
    DRAGONBORN("Draconato"),
    ANGEL("Anjo"),
    DEMON("Demônio"),
    FAIRY("Fada"),
    GNOME("Gnomo"),
    MERFOLK("Sereia/Sereno"),
    CENTAUR("Centauro");

    private final String displayName;

    Race(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
