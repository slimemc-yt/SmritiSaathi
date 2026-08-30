package com.socklet.smritisaathi.domain.model;

import java.util.Date;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b&\n\u0002\u0010\b\n\u0000\n\u0002\u0010$\n\u0002\b\u0003\b\u0086\b\u0018\u0000 ?2\u00020\u0001:\u0001?B\u00a3\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000f\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\u0002\u0010\u0015J\t\u0010)\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00030\u000fH\u00c6\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010-\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u0010.\u001a\u00020\u0014H\u00c6\u0003J\t\u0010/\u001a\u00020\u0003H\u00c6\u0003J\t\u00100\u001a\u00020\u0006H\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\t\u00104\u001a\u00020\u000bH\u00c6\u0003J\t\u00105\u001a\u00020\u000bH\u00c6\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u00a7\u0001\u00107\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000f2\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u00c6\u0001J\u0013\u00108\u001a\u00020\u00142\b\u00109\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010:\u001a\u00020;H\u00d6\u0001J\u0014\u0010<\u001a\u0010\u0012\u0004\u0012\u00020\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00010=J\t\u0010>\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0013\u0010\u0010\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0019R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u0017R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0019R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0019R\u0011\u0010\u0013\u001a\u00020\u0014\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0013\u0010\t\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0019R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0019\u00a8\u0006@"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/User;", "", "id", "", "phoneNumber", "role", "Lcom/socklet/smritisaathi/domain/model/UserRole;", "name", "email", "profilePhotoUrl", "createdAt", "Ljava/util/Date;", "lastLoginAt", "fcmToken", "linkedPatientIds", "", "doctorCode", "hospitalName", "specialty", "profileCompleted", "", "(Ljava/lang/String;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/UserRole;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/Date;Ljava/util/Date;Ljava/lang/String;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", "getCreatedAt", "()Ljava/util/Date;", "getDoctorCode", "()Ljava/lang/String;", "getEmail", "getFcmToken", "getHospitalName", "getId", "getLastLoginAt", "getLinkedPatientIds", "()Ljava/util/List;", "getName", "getPhoneNumber", "getProfileCompleted", "()Z", "getProfilePhotoUrl", "getRole", "()Lcom/socklet/smritisaathi/domain/model/UserRole;", "getSpecialty", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toMap", "", "toString", "Companion", "app_debug"})
public final class User {
    @org.jetbrains.annotations.NotNull
    private final java.lang.String id = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String phoneNumber = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.UserRole role = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String name = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String email = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String profilePhotoUrl = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Date createdAt = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.Date lastLoginAt = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String fcmToken = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<java.lang.String> linkedPatientIds = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String doctorCode = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String hospitalName = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String specialty = null;
    private final boolean profileCompleted = false;
    @org.jetbrains.annotations.NotNull
    public static final com.socklet.smritisaathi.domain.model.User.Companion Companion = null;
    
    public User(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String phoneNumber, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.UserRole role, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.Nullable
    java.lang.String email, @org.jetbrains.annotations.Nullable
    java.lang.String profilePhotoUrl, @org.jetbrains.annotations.NotNull
    java.util.Date createdAt, @org.jetbrains.annotations.NotNull
    java.util.Date lastLoginAt, @org.jetbrains.annotations.Nullable
    java.lang.String fcmToken, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> linkedPatientIds, @org.jetbrains.annotations.Nullable
    java.lang.String doctorCode, @org.jetbrains.annotations.Nullable
    java.lang.String hospitalName, @org.jetbrains.annotations.Nullable
    java.lang.String specialty, boolean profileCompleted) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPhoneNumber() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.UserRole getRole() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getEmail() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getProfilePhotoUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date getCreatedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date getLastLoginAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getFcmToken() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> getLinkedPatientIds() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDoctorCode() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getHospitalName() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getSpecialty() {
        return null;
    }
    
    public final boolean getProfileCompleted() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Map<java.lang.String, java.lang.Object> toMap() {
        return null;
    }
    
    public User() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<java.lang.String> component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component13() {
        return null;
    }
    
    public final boolean component14() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.UserRole component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.Date component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.User copy(@org.jetbrains.annotations.NotNull
    java.lang.String id, @org.jetbrains.annotations.NotNull
    java.lang.String phoneNumber, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.UserRole role, @org.jetbrains.annotations.NotNull
    java.lang.String name, @org.jetbrains.annotations.Nullable
    java.lang.String email, @org.jetbrains.annotations.Nullable
    java.lang.String profilePhotoUrl, @org.jetbrains.annotations.NotNull
    java.util.Date createdAt, @org.jetbrains.annotations.NotNull
    java.util.Date lastLoginAt, @org.jetbrains.annotations.Nullable
    java.lang.String fcmToken, @org.jetbrains.annotations.NotNull
    java.util.List<java.lang.String> linkedPatientIds, @org.jetbrains.annotations.Nullable
    java.lang.String doctorCode, @org.jetbrains.annotations.Nullable
    java.lang.String hospitalName, @org.jetbrains.annotations.Nullable
    java.lang.String specialty, boolean profileCompleted) {
        return null;
    }
    
    @java.lang.Override
    public boolean equals(@org.jetbrains.annotations.Nullable
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\u0014\u0010\u0005\u001a\u0010\u0012\u0004\u0012\u00020\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006\u00a8\u0006\b"}, d2 = {"Lcom/socklet/smritisaathi/domain/model/User$Companion;", "", "()V", "fromMap", "Lcom/socklet/smritisaathi/domain/model/User;", "map", "", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final com.socklet.smritisaathi.domain.model.User fromMap(@org.jetbrains.annotations.NotNull
        java.util.Map<java.lang.String, ? extends java.lang.Object> map) {
            return null;
        }
    }
}