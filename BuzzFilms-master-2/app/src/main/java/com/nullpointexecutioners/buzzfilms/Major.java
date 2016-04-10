package com.nullpointexecutioners.buzzfilms;

/**
 * ENUM type for User's Major
 */
public enum Major {
    NONE("Select your major"),
    CS("Computer Science"),
    EE("Electrical Engineering"),
    ME("Mechanical Engineering"),
    ISYE("Industrial & Systems Engineering"),
    MATH("Mathematics"),
    PHYS("Physics"),
    CHEM("Chemistry"),
    CHEME("Chemical Engineering");

    private final String theMajor;

    /**
     * Sets the major of a user
     * @param aMajor string of a user major
     */
    Major(String aMajor) {
        theMajor = aMajor;
    }

    @Override
    public String toString() {
        return theMajor;
    }

    /**
     * Helper method for getting the enum from a String
     * @param value of String to extract enum from
     * @return major of enum type
     */
    public static Major fromString(String value) {
        for (final Major major : values()) {
            if (major.theMajor.equals(value)) {
                return major;
            }
        }
        return null;
    }
}
