package com.namsu.inventoryspring.domain.stockmovement.domain;

public enum MovementType {
    INBOUND("입고"), OUTBOUND("출고");

    private final String displayName;

    MovementType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
