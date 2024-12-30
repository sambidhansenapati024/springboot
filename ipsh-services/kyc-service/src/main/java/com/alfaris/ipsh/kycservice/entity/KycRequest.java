package com.alfaris.ipsh.kycservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "kyc_requests")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class KycRequest {
    @EmbeddedId
    private KycRequestPk kycPk;

    
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "document_type")
    private String documentType;

    @Column(name = "document_number")
    private String documentNumber;
    
    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "status")
    private String status;
    
    @Column(name = "email")
    private String email;
    

    @Column(name = "modified_by")
    private String modifiedBy;

    @Column(name = "comments")
    private String comments;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

