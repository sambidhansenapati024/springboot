package com.alfaris.ipsh.subscription.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.validator.constraints.Range;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
public class SubscriptionDto {
	private Long uniqUserId;
	@Pattern(regexp = "\\+?[0-9]{10,15}", message = "Invalid phone number")
	private String mobileNumber;
	private String uniqSubId;
	@Pattern(regexp = "^[A-Za-z]{1,10}$", message = "The input must contain exactly 10 letters.")
	@NotBlank
	private String userName;
	@Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Invalid email address format.")
	@NotBlank
	private String email;
	@Pattern(regexp = "^[A-Za-z]{1,20}$", message = "The input must contain 1 to 10 letters.")
	@NotBlank
	private String platforms;
	@Pattern(regexp = "^[A-Za-z]{1,20}$", message = "The input must contain 1 to 10 letters.")
	@NotBlank
	private String fullName;
	@Pattern(regexp = "^[A-Z][A-Za-z ]{1,19}$", message = "The input must contain 1 to 20 letters.")
	private String createdBy;
	@Pattern(regexp = "^[A-Za-z]{1,10}$", message = "The input must contain 1 to 10 letters.")
	private String modifiedBy;
	@Pattern(regexp = "^[A-Za-z]{1,10}$", message = "The input must contain 1 to 10 letters.")
	private String verifiedBy;
	private String status;
	private String comments;
	@Pattern(regexp = "^[A-Za-z]{1,10}$", message = "The input must contain 1 to 10 letters.")
	@NotBlank
	private String paymentMethod;
	private String paymentBy;
	@Min(1)
	@Positive
	private Long duration;
	@Range(min = 1, max = 100000, message = "The value must be between 1 and 100,000.")
	private Double price;
	private Double fPrice;
	private LocalDateTime creationTime;
	private LocalDateTime modificationTime;
	private LocalDateTime verificationTime;
	private LocalDate subscriptionDate;
	private LocalDateTime patmentDate;
	private LocalDate endSubscriptionDate;
}