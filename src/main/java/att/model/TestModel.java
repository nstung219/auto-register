package att.model;

import lombok.Data;

@Data
public class TestModel {
    private String day;
    private String location;
    private String slot;
    private String band;
    private String languageType;

    public TestModel(String day, String location, String slot, String band, String languageType) {
        this.day = day;
        this.location = location;
        this.slot = slot;
        switch(band) {
            case "Band A":
                this.band = "A";
                break;
            case "Band B":
                this.band = "B";
                break;
            case "Band C":
                this.band = "C";
                break;
            default:
                this.band = "A";
                break;
        }
        switch(languageType) {
            case "Simplified":
                this.languageType = "2";
                break;
            case "Traditional":
                this.languageType = "1";
                break;
            default:
                this.languageType = "1";
                break;
        }
    }

    public static TestModel fromString(String s) {
        String[] parts = s.split("::");
        return new TestModel(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    public String toString() {
        return day + "::" + location + "::" + slot + "::"  + "::" + band + "::" + languageType;
    }
}
