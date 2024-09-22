//package com.wish.dms_api.entity;
//
//
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import jakarta.persistence.Transient;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Table(name="secure_tokens")
//public class SecureToken {
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	private long id;
//	
//	@Column(unique = true)
//    private String token;
//
//    @CreationTimestamp
//    @Column(updatable = false)
//    private Timestamp timestamp;
//
//    @Column(updatable = false)
////    @Basic(optional = false)
//    private LocalDateTime expireAt;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User user;
//    
//    @Transient
//    private boolean isExpired;
//
//}
