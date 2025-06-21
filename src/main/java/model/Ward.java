package model;

public class Ward {
    private String code;
    private String name;
    private String fullName;
    private String districtCode;
    private Double latitude;
    private Double longitude;

    // Constructors
    public Ward() {}
    public Ward(String code, String name, String fullName, String districtCode, Double latitude, Double longitude) {
        this.code = code;
        this.name = name;
        this.fullName = fullName;
        this.districtCode = districtCode;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters & Setters
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDistrictCode() { return districtCode; }
    public void setDistrictCode(String districtCode) { this.districtCode = districtCode; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
