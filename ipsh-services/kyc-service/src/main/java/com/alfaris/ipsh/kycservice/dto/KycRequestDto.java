package com.alfaris.ipsh.kycservice.dto;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KycRequestDto {

	private Long userId;
	@NotBlank( message = "User name must not be blank.")
	@Pattern(regexp = "^[A-Za-z]{1,10}$", message = "The input must contain exactly 10 letters.")
	private String userName;
	@NotBlank( message = "Document type must not be blank.")
	@Pattern(regexp = "^[A-Za-z]{1,10}$", message = "The input must contain exactly 10 letters.")
	private String documentType;
	@NotBlank( message = "Document number must not be blank.")
	@Pattern(regexp = "^[A-Za-z0-9/-]{1,10}$", message = "The input must be exactly 10 characters long and can include letters, digits, '/' or '-'.")
	private String documentNumber;
	@NotBlank( message = "Mobile number must not be blank.")
	@Pattern(regexp = "^[0-9]{10}$", message = "The mobile number must be exactly 10 digits.")
	private String mobileNumber;

	private String status;
	@NotBlank( message = "Email must not be blank.")
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email address format.")
	private String email;
	
	private String modifiedBy;

	private String comments;

	private LocalDateTime updatedAt;

	private String kycUniqueid;

	private String createdBy;

	private LocalDateTime createdAt;
}
