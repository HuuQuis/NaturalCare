package model;

public class Address {
    private int addressId;
    private int provinceCode;
    private String provinceName;
    private int districtCode;
    private String districtName;
    private int wardCode;
    private String wardName;
    private String detail;
    private String addressType;

    // Constructor
    public Address() {}

    public Address(int addressId, int provinceCode, String provinceName,
                   int districtCode, String districtName, int wardCode,
                   String wardName, String detail) {
        this.addressId = addressId;
        this.provinceCode = provinceCode;
        this.provinceName = provinceName;
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.wardCode = wardCode;
        this.wardName = wardName;
        this.detail = detail;
    }

    // Getters and Setters
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(int districtCode) {
        this.districtCode = districtCode;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getWardCode() {
        return wardCode;
    }

    public void setWardCode(int wardCode) {
        this.wardCode = wardCode;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getFullAddress() {
        StringBuilder fullAddress = new StringBuilder();
        if (detail != null && !detail.trim().isEmpty()) {
            fullAddress.append(detail).append(", ");
        }
        if (wardName != null && !wardName.trim().isEmpty()) {
            fullAddress.append(wardName).append(", ");
        }
        if (districtName != null && !districtName.trim().isEmpty()) {
            fullAddress.append(districtName).append(", ");
        }
        if (provinceName != null && !provinceName.trim().isEmpty()) {
            fullAddress.append(provinceName);
        }
        return fullAddress.toString();
    }
}