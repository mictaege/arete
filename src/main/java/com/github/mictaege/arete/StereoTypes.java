package com.github.mictaege.arete;

public enum StereoTypes {
    /** An User or a Stakeholder, e.g Customer, Supplier */
    ACTOR("Actors", "tagged-actor"),
    /** A cross-cutting area or field, e.g., Management, Research, Production */
    DOMAIN("Domain", "tagged-domain"),
    /** A Unit or Department e.g., Warehouse, Sales-Office */
    UNIT("Units", "tagged-unit"),
    /** A Workflow or Process e.g., Booking, Delivery */
    WORKFLOW("Workflows", "tagged-workflow"),
    /** An Entity or Data e.g., Order, Invoice */
    ENTITY("Entities", "tagged-entity"),
    /** Communication or Data-Transfer with other systems e.g., Email, Payment-Service  */
    EXTERNAL("External", "tagged-external"),
    /** A Trigger for some Actions e.g., Receiving a Message, Reaching a Date */
    EVENT("Events", "tagged-event"),
    /** Actions performed regularly e.g., Backups, Reorganization */
    SCHEDULE("Schedules", "tagged-schedule"),
    /** A rule that must be adhered to or fulfilled e.g., Terms & Conditions, Tax-Regulations */
    RULE("Rules", "tagged-rule"),
    /** Any other notable thing */
    TAG("Tags", "tagged-tag");

    private final String title;
    private final String iconClass;

    StereoTypes(String title, String iconClass) {
        this.title = title;
        this.iconClass = iconClass;
    }

    public String getTitle() {
        return title;
    }

    public String getIconClass() {
        return iconClass;
    }

}
