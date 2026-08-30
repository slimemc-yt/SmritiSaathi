package com.socklet.smritisaathi.domain.repository;

import android.net.Uri;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.socklet.smritisaathi.domain.model.*;
import kotlinx.coroutines.flow.Flow;
import java.util.Date;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Singleton;

@javax.inject.Singleton
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u00b8\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001b\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b.\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J$\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J4\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001a\u0010\u001bJ,\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u001e\u001a\u00020\u001dH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u001f\u0010 J$\u0010!\u001a\b\u0012\u0004\u0012\u00020\t0\u000f2\u0006\u0010\"\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b#\u0010$J$\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000b0\u000f2\u0006\u0010&\u001a\u00020\u000bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\'\u0010(J<\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010*\u001a\u00020\u00172\u0006\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b-\u0010.J6\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u00100\u001a\u00020\u00172\b\b\u0002\u00101\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b2\u0010\u001bJB\u00103\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u00104\u001a\u00020\u00172\b\b\u0002\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u00105\u001a\u00020\u00172\b\b\u0002\u00106\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b7\u0010.J\u001c\u00108\u001a\b\u0012\u0004\u0012\u0002090\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b:\u0010;J$\u0010<\u001a\b\u0012\u0004\u0012\u0002090\u000f2\u0006\u0010=\u001a\u000209H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b>\u0010?J0\u0010@\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u00104\u001a\u00020\u00172\n\b\u0002\u0010A\u001a\u0004\u0018\u00010\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bB\u0010CJ\u001a\u0010D\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020F0\b0E2\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010G\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u001d0\b0E2\u0006\u0010\u0016\u001a\u00020\u0017J\u0016\u0010H\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00120E2\u0006\u0010\u0016\u001a\u00020\u0017J\"\u0010I\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bJ\u0010;J$\u0010K\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020L0\b0E2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010M\u001a\u00020NJ4\u0010O\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020P0\b0\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\b\b\u0002\u0010M\u001a\u00020QH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bR\u0010SJ\u001a\u0010T\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020P0\b0E2\u0006\u0010\u0016\u001a\u00020\u0017J\"\u0010U\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\b0\u000fH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bV\u0010;J\u0016\u0010W\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010X0E2\u0006\u0010\u0016\u001a\u00020\u0017J$\u0010Y\u001a\b\u0012\u0004\u0012\u0002090\u000f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bZ\u0010[J$\u0010\\\u001a\b\u0012\u0004\u0012\u0002090\u000f2\u0006\u0010]\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b^\u0010[J$\u0010_\u001a\b\u0012\u0004\u0012\u0002090\u000f2\u0006\u0010]\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b`\u0010[J\u0016\u0010a\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001090E2\u0006\u0010\u0016\u001a\u00020\u0017J*\u0010b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002090\b0\u000f2\u0006\u0010\u0019\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bc\u0010[J\u001a\u0010d\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002090\b0E2\u0006\u00105\u001a\u00020\u0017J*\u0010e\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002090\b0\u000f2\u0006\u00105\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bf\u0010[J.\u0010g\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\b0E2\u0006\u00105\u001a\u00020\u00172\b\b\u0002\u00106\u001a\u00020\u00172\b\b\u0002\u0010+\u001a\u00020\u0017J\u001a\u0010h\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020i0\b0E2\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010j\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020k0\b0E2\u0006\u0010\u0016\u001a\u00020\u0017J\u001a\u0010l\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020m0\b0E2\u0006\u0010\u0016\u001a\u00020\u0017JH\u0010n\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010o\u001a\u00020\u00172\u0006\u0010p\u001a\u00020\u00172\b\b\u0002\u0010q\u001a\u00020\u00172\b\b\u0002\u0010r\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bs\u0010tJ,\u0010u\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010v\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bw\u0010CJ,\u0010x\u001a\b\u0012\u0004\u0012\u00020X0\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010y\u001a\u00020XH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\bz\u0010{J,\u0010|\u001a\b\u0012\u0004\u0012\u00020L0\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010}\u001a\u00020LH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b~\u0010\u007fJ0\u0010\u0080\u0001\u001a\b\u0012\u0004\u0012\u00020P0\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0007\u0010\u0081\u0001\u001a\u00020PH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u0082\u0001\u0010\u0083\u0001J0\u0010\u0084\u0001\u001a\b\u0012\u0004\u0012\u00020i0\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0007\u0010\u0085\u0001\u001a\u00020iH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u0086\u0001\u0010\u0087\u0001J0\u0010\u0088\u0001\u001a\b\u0012\u0004\u0012\u00020k0\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0007\u0010\u0089\u0001\u001a\u00020kH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u008a\u0001\u0010\u008b\u0001J&\u0010\u008c\u0001\u001a\b\u0012\u0004\u0012\u00020\u00120\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u008d\u0001\u0010\u0014J0\u0010\u008e\u0001\u001a\b\u0012\u0004\u0012\u00020m0\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0007\u0010\u008f\u0001\u001a\u00020mH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u0090\u0001\u0010\u0091\u0001J0\u0010\u0092\u0001\u001a\b\u0012\u0004\u0012\u00020F0\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0007\u0010\u0093\u0001\u001a\u00020FH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u0094\u0001\u0010\u0095\u0001J&\u0010\u0096\u0001\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0016\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0097\u0001\u0010[J&\u0010\u0098\u0001\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010=\u001a\u000209H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0005\b\u0099\u0001\u0010?J7\u0010\u009a\u0001\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u000e\u0010\u009b\u0001\u001a\t\u0012\u0005\u0012\u00030\u009c\u00010\bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u009d\u0001\u0010\u009e\u0001J:\u0010\u009f\u0001\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0007\u0010\u00a0\u0001\u001a\u00020\u00172\b\u0010\u00a1\u0001\u001a\u00030\u00a2\u0001H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u00a3\u0001\u0010\u00a4\u0001J:\u0010\u00a5\u0001\u001a\b\u0012\u0004\u0012\u00020\u00170\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\u0007\u0010\u00a6\u0001\u001a\u00020\u00172\b\u0010\u00a7\u0001\u001a\u00030\u00a8\u0001H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u00a9\u0001\u0010\u00aa\u0001JD\u0010\u00ab\u0001\u001a\t\u0012\u0005\u0012\u00030\u009c\u00010\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u00ac\u0001\u001a\u00030\u00a8\u00012\u0007\u0010\u00ad\u0001\u001a\u00020\u00172\u0007\u0010\u00ae\u0001\u001a\u00020\u0017H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u00af\u0001\u0010\u00b0\u0001J1\u0010\u00b1\u0001\u001a\b\u0012\u0004\u0012\u00020\u00170\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u00a7\u0001\u001a\u00030\u00a8\u0001H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u00b2\u0001\u0010\u00b3\u0001J;\u0010\u00b4\u0001\u001a\b\u0012\u0004\u0012\u00020\u00170\u000f2\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u00b5\u0001\u001a\u00030\u00a8\u00012\b\u0010\u00b6\u0001\u001a\u00030\u00b7\u0001H\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0006\b\u00b8\u0001\u0010\u00b9\u0001R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u00ba\u0001"}, d2 = {"Lcom/socklet/smritisaathi/domain/repository/PatientRepository;", "", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "storage", "Lcom/google/firebase/storage/FirebaseStorage;", "(Lcom/google/firebase/firestore/FirebaseFirestore;Lcom/google/firebase/storage/FirebaseStorage;)V", "defaultDoctors", "", "Lcom/socklet/smritisaathi/domain/model/Doctor;", "defaultHospitals", "Lcom/socklet/smritisaathi/domain/model/Hospital;", "requestsCollection", "Lcom/google/firebase/firestore/CollectionReference;", "acceptDoctorRequest", "Lkotlin/Result;", "", "request", "Lcom/socklet/smritisaathi/domain/model/DoctorPatientRequest;", "acceptDoctorRequest-gIAlu-s", "(Lcom/socklet/smritisaathi/domain/model/DoctorPatientRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "acknowledgeAlert", "patientId", "", "alertId", "userId", "acknowledgeAlert-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addClinicalNote", "Lcom/socklet/smritisaathi/domain/model/ClinicalNote;", "note", "addClinicalNote-0E7RQCE", "(Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/ClinicalNote;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addDoctor", "doctor", "addDoctor-gIAlu-s", "(Lcom/socklet/smritisaathi/domain/model/Doctor;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "addHospital", "hospital", "addHospital-gIAlu-s", "(Lcom/socklet/smritisaathi/domain/model/Hospital;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "assignDoctorToPatient", "doctorUid", "doctorName", "hospitalName", "assignDoctorToPatient-yxL6bBk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "bindPatientDevice", "deviceUid", "deviceName", "bindPatientDevice-BWLJW6A", "cancelDoctorRequest", "requestId", "doctorId", "doctorCode", "cancelDoctorRequest-yxL6bBk", "createOrGetDemoPatient", "Lcom/socklet/smritisaathi/domain/model/Patient;", "createOrGetDemoPatient-IoAF18A", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "createPatient", "patient", "createPatient-gIAlu-s", "(Lcom/socklet/smritisaathi/domain/model/Patient;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "declineDoctorRequest", "reason", "declineDoctorRequest-0E7RQCE", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getBehavioralAlertsFlow", "Lkotlinx/coroutines/flow/Flow;", "Lcom/socklet/smritisaathi/domain/model/BehavioralAlert;", "getClinicalNotesFlow", "getDoctorRequestForPatient", "getDoctors", "getDoctors-IoAF18A", "getGamePerformancesFlow", "Lcom/socklet/smritisaathi/domain/model/GamePerformance;", "limit", "", "getGameResults", "Lcom/socklet/smritisaathi/domain/model/GameResult;", "", "getGameResults-0E7RQCE", "(Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getGameResultsFlow", "getHospitals", "getHospitals-IoAF18A", "getLatestCognitiveAssessmentFlow", "Lcom/socklet/smritisaathi/domain/model/CognitiveAssessment;", "getPatient", "getPatient-gIAlu-s", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPatientByFamilyInviteCode", "code", "getPatientByFamilyInviteCode-gIAlu-s", "getPatientByPairingCode", "getPatientByPairingCode-gIAlu-s", "getPatientFlow", "getPatientsByCreator", "getPatientsByCreator-gIAlu-s", "getPatientsByDoctorId", "getPatientsForDoctor", "getPatientsForDoctor-gIAlu-s", "getPendingRequestsForDoctor", "getRemindersFlow", "Lcom/socklet/smritisaathi/domain/model/Reminder;", "getReminiscenceContentFlow", "Lcom/socklet/smritisaathi/domain/model/ReminiscenceContent;", "getUnprocessedPendingActionsFlow", "Lcom/socklet/smritisaathi/domain/model/PendingAction;", "linkFamilyMember", "familyUid", "name", "relationship", "permissionLevel", "linkFamilyMember-hUnOzRk", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markActionProcessed", "actionId", "markActionProcessed-0E7RQCE", "saveCognitiveAssessment", "assessment", "saveCognitiveAssessment-0E7RQCE", "(Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/CognitiveAssessment;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveGamePerformance", "performance", "saveGamePerformance-0E7RQCE", "(Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/GamePerformance;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveGameResult", "result", "saveGameResult-0E7RQCE", "(Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/GameResult;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveReminder", "reminder", "saveReminder-0E7RQCE", "(Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/Reminder;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "saveReminiscenceContent", "content", "saveReminiscenceContent-0E7RQCE", "(Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/ReminiscenceContent;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sendDoctorRequest", "sendDoctorRequest-gIAlu-s", "sendPendingAction", "action", "sendPendingAction-0E7RQCE", "(Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/PendingAction;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "triggerAlert", "alert", "triggerAlert-0E7RQCE", "(Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/BehavioralAlert;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unlinkDoctorFromPatient", "unlinkDoctorFromPatient-gIAlu-s", "updatePatient", "updatePatient-gIAlu-s", "updatePatientReports", "reports", "Lcom/socklet/smritisaathi/domain/model/MedicalReport;", "updatePatientReports-0E7RQCE", "(Ljava/lang/String;Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateReminderStatus", "reminderId", "status", "Lcom/socklet/smritisaathi/domain/model/ReminderStatus;", "updateReminderStatus-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/ReminderStatus;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadFamilyContactPhoto", "contactId", "imageUri", "Landroid/net/Uri;", "uploadFamilyContactPhoto-BWLJW6A", "(Ljava/lang/String;Ljava/lang/String;Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadMedicalReport", "fileUri", "fileName", "reportType", "uploadMedicalReport-yxL6bBk", "(Ljava/lang/String;Landroid/net/Uri;Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadProfilePhoto", "uploadProfilePhoto-0E7RQCE", "(Ljava/lang/String;Landroid/net/Uri;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "uploadReminiscenceMedia", "mediaUri", "isAudio", "", "uploadReminiscenceMedia-BWLJW6A", "(Ljava/lang/String;Landroid/net/Uri;ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class PatientRepository {
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.storage.FirebaseStorage storage = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.CollectionReference requestsCollection = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.Hospital> defaultHospitals = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.Doctor> defaultDoctors = null;
    
    @javax.inject.Inject
    public PatientRepository(@org.jetbrains.annotations.NotNull
    com.google.firebase.firestore.FirebaseFirestore firestore, @org.jetbrains.annotations.NotNull
    com.google.firebase.storage.FirebaseStorage storage) {
        super();
    }
    
    /**
     * Listen in real-time to pending assignment requests for a doctor across multiple sources.
     * Uses comprehensive matching across Doctor ID, Doctor Code, and Doctor Name.
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.socklet.smritisaathi.domain.model.DoctorPatientRequest>> getPendingRequestsForDoctor(@org.jetbrains.annotations.NotNull
    java.lang.String doctorId, @org.jetbrains.annotations.NotNull
    java.lang.String doctorCode, @org.jetbrains.annotations.NotNull
    java.lang.String doctorName) {
        return null;
    }
    
    /**
     * Listen in real-time to the active/pending doctor request for a patient.
     * The patient document is the single source of truth.
     */
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.socklet.smritisaathi.domain.model.DoctorPatientRequest> getDoctorRequestForPatient(@org.jetbrains.annotations.NotNull
    java.lang.String patientId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.socklet.smritisaathi.domain.model.Patient> getPatientFlow(@org.jetbrains.annotations.NotNull
    java.lang.String patientId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.socklet.smritisaathi.domain.model.Patient>> getPatientsByDoctorId(@org.jetbrains.annotations.NotNull
    java.lang.String doctorId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.socklet.smritisaathi.domain.model.GamePerformance>> getGamePerformancesFlow(@org.jetbrains.annotations.NotNull
    java.lang.String patientId, int limit) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<com.socklet.smritisaathi.domain.model.CognitiveAssessment> getLatestCognitiveAssessmentFlow(@org.jetbrains.annotations.NotNull
    java.lang.String patientId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.socklet.smritisaathi.domain.model.GameResult>> getGameResultsFlow(@org.jetbrains.annotations.NotNull
    java.lang.String patientId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.socklet.smritisaathi.domain.model.Reminder>> getRemindersFlow(@org.jetbrains.annotations.NotNull
    java.lang.String patientId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.socklet.smritisaathi.domain.model.BehavioralAlert>> getBehavioralAlertsFlow(@org.jetbrains.annotations.NotNull
    java.lang.String patientId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.socklet.smritisaathi.domain.model.ReminiscenceContent>> getReminiscenceContentFlow(@org.jetbrains.annotations.NotNull
    java.lang.String patientId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.socklet.smritisaathi.domain.model.PendingAction>> getUnprocessedPendingActionsFlow(@org.jetbrains.annotations.NotNull
    java.lang.String patientId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.socklet.smritisaathi.domain.model.ClinicalNote>> getClinicalNotesFlow(@org.jetbrains.annotations.NotNull
    java.lang.String patientId) {
        return null;
    }
}