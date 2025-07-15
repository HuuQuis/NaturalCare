package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contract {
    private int contractId;
    private int userId;
    private String contractType;
    private long salary;
    private Date startDate;
    private Date endDate;
    private String contractStatus;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private String contractTerms;
    private Integer signedBy;
    
    // Utility methods
    public boolean isActive() {
        return "active".equals(contractStatus);
    }
    
    public boolean isExpired() {
        return "expired".equals(contractStatus);
    }
    
    public boolean isTerminated() {
        return "terminated".equals(contractStatus);
    }
    
    public String getFormattedSalary() {
        return String.format("%,d VND", salary);
    }
} 