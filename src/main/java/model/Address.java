package model;

public class Address {
    private int addressId;
    private String provinceCode;
    private String districtCode;
    private String wardCode;
    private String detail;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Double distanceKm;
    private boolean defaultAddress;

    // Optional: Province/District/Ward objects (if you want nested info)
    private Province province;
    private District district;
    private Ward ward;

    // Constructors
    public Address() {}

    // Getters & Setters
    public int getAddressId() { return addressId; }
    public void setAddressId(int addressId) { this.addressId = addressId; }

    public String getProvinceCode() { return provinceCode; }
    public void setProvinceCode(String provinceCode) { this.provinceCode = provinceCode; }

    public String getDistrictCode() { return districtCode; }
    public void setDistrictCode(String districtCode) { this.districtCode = districtCode; }

    public String getWardCode() { return wardCode; }
    public void setWardCode(String wardCode) { this.wardCode = wardCode; }

    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public Province getProvince() { return province; }
    public void setProvince(Province province) { this.province = province; }

    public District getDistrict() { return district; }
    public void setDistrict(District district) { this.district = district; }

    public Ward getWard() { return ward; }
    public void setWard(Ward ward) { this.ward = ward; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;

    }

}
