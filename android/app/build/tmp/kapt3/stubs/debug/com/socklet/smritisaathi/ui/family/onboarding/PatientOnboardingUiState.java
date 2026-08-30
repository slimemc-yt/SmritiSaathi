package com.socklet.smritisaathi.ui.family.onboarding;

import android.net.Uri;
import androidx.lifecycle.ViewModel;
import com.socklet.smritisaathi.domain.model.*;
import com.socklet.smritisaathi.domain.repository.PatientRepository;
import com.socklet.smritisaathi.data.seeder.DatabaseSeeder;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.Date;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\bs\b\u0086\b\u0018\u00002\u00020\u0001B\u00f7\u0003\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007\u0012\b\b\u0002\u0010\n\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u0012\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0013\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0013\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u001b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001c\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u001d\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u000e\u0012\u000e\b\u0002\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0013\u0012\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0013\u0012\u000e\b\u0002\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u0013\u0012\b\b\u0002\u0010%\u001a\u00020\u0005\u0012\n\b\u0002\u0010&\u001a\u0004\u0018\u00010 \u0012\n\b\u0002\u0010\'\u001a\u0004\u0018\u00010\"\u0012\b\b\u0002\u0010(\u001a\u00020\u0005\u0012\b\b\u0002\u0010)\u001a\u00020\u0005\u0012\b\b\u0002\u0010*\u001a\u00020\u0007\u0012\b\b\u0002\u0010+\u001a\u00020\u0007\u0012\b\b\u0002\u0010,\u001a\u00020\u0007\u0012\u000e\b\u0002\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u0013\u0012\u000e\b\u0002\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u0013\u0012\u000e\b\u0002\u00101\u001a\b\u0012\u0004\u0012\u0002020\u0013\u0012\b\b\u0002\u00103\u001a\u00020\u0003\u0012\b\b\u0002\u00104\u001a\u00020\u0003\u0012\b\b\u0002\u00105\u001a\u00020\u0003\u0012\b\b\u0002\u00106\u001a\u00020\u0007\u0012\b\b\u0002\u00107\u001a\u00020\u0007\u0012\b\b\u0002\u00108\u001a\u00020\u0007\u0012\b\b\u0002\u00109\u001a\u00020\u0007\u0012\b\b\u0002\u0010:\u001a\u00020\u0005\u0012\b\b\u0002\u0010;\u001a\u00020\u0005\u0012\n\b\u0002\u0010<\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010=J\t\u0010u\u001a\u00020\u0003H\u00c6\u0003J\t\u0010v\u001a\u00020\u0011H\u00c6\u0003J\u000f\u0010w\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u00c6\u0003J\u000f\u0010x\u001a\b\u0012\u0004\u0012\u00020\u00160\u0013H\u00c6\u0003J\t\u0010y\u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010z\u001a\b\u0012\u0004\u0012\u00020\u00190\u0013H\u00c6\u0003J\t\u0010{\u001a\u00020\u0003H\u00c6\u0003J\t\u0010|\u001a\u00020\u0007H\u00c6\u0003J\t\u0010}\u001a\u00020\u0007H\u00c6\u0003J\t\u0010~\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010\u007f\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\n\u0010\u0080\u0001\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u00020 0\u0013H\u00c6\u0003J\u0010\u0010\u0082\u0001\u001a\b\u0012\u0004\u0012\u00020\"0\u0013H\u00c6\u0003J\u0010\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00020$0\u0013H\u00c6\u0003J\n\u0010\u0084\u0001\u001a\u00020\u0005H\u00c6\u0003J\f\u0010\u0085\u0001\u001a\u0004\u0018\u00010 H\u00c6\u0003J\f\u0010\u0086\u0001\u001a\u0004\u0018\u00010\"H\u00c6\u0003J\n\u0010\u0087\u0001\u001a\u00020\u0005H\u00c6\u0003J\n\u0010\u0088\u0001\u001a\u00020\u0005H\u00c6\u0003J\n\u0010\u0089\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u008a\u0001\u001a\u00020\u0007H\u00c6\u0003J\f\u0010\u008b\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\n\u0010\u008c\u0001\u001a\u00020\u0007H\u00c6\u0003J\u0010\u0010\u008d\u0001\u001a\b\u0012\u0004\u0012\u00020.0\u0013H\u00c6\u0003J\u0010\u0010\u008e\u0001\u001a\b\u0012\u0004\u0012\u0002000\u0013H\u00c6\u0003J\u0010\u0010\u008f\u0001\u001a\b\u0012\u0004\u0012\u0002020\u0013H\u00c6\u0003J\n\u0010\u0090\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0091\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0092\u0001\u001a\u00020\u0003H\u00c6\u0003J\n\u0010\u0093\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u0094\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u0095\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u0096\u0001\u001a\u00020\u0005H\u00c6\u0003J\n\u0010\u0097\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u0098\u0001\u001a\u00020\u0005H\u00c6\u0003J\n\u0010\u0099\u0001\u001a\u00020\u0005H\u00c6\u0003J\f\u0010\u009a\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\n\u0010\u009b\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u009c\u0001\u001a\u00020\u0007H\u00c6\u0003J\n\u0010\u009d\u0001\u001a\u00020\fH\u00c6\u0003J\f\u0010\u009e\u0001\u001a\u0004\u0018\u00010\u000eH\u00c6\u0003J\f\u0010\u009f\u0001\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u00fc\u0003\u0010\u00a0\u0001\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u00072\b\b\u0002\u0010\u000b\u001a\u00020\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u000e2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u00112\u000e\b\u0002\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u000e\b\u0002\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u00072\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00132\b\b\u0002\u0010\u001a\u001a\u00020\u00032\b\b\u0002\u0010\u001b\u001a\u00020\u00072\b\b\u0002\u0010\u001c\u001a\u00020\u00072\b\b\u0002\u0010\u001d\u001a\u00020\u00072\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\u000e2\u000e\b\u0002\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u00132\u000e\b\u0002\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u00132\u000e\b\u0002\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u00132\b\b\u0002\u0010%\u001a\u00020\u00052\n\b\u0002\u0010&\u001a\u0004\u0018\u00010 2\n\b\u0002\u0010\'\u001a\u0004\u0018\u00010\"2\b\b\u0002\u0010(\u001a\u00020\u00052\b\b\u0002\u0010)\u001a\u00020\u00052\b\b\u0002\u0010*\u001a\u00020\u00072\b\b\u0002\u0010+\u001a\u00020\u00072\b\b\u0002\u0010,\u001a\u00020\u00072\u000e\b\u0002\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u00132\u000e\b\u0002\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u00132\u000e\b\u0002\u00101\u001a\b\u0012\u0004\u0012\u0002020\u00132\b\b\u0002\u00103\u001a\u00020\u00032\b\b\u0002\u00104\u001a\u00020\u00032\b\b\u0002\u00105\u001a\u00020\u00032\b\b\u0002\u00106\u001a\u00020\u00072\b\b\u0002\u00107\u001a\u00020\u00072\b\b\u0002\u00108\u001a\u00020\u00072\b\b\u0002\u00109\u001a\u00020\u00072\b\b\u0002\u0010:\u001a\u00020\u00052\b\b\u0002\u0010;\u001a\u00020\u00052\n\b\u0002\u0010<\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001J\u0015\u0010\u00a1\u0001\u001a\u00020\u00052\t\u0010\u00a2\u0001\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\n\u0010\u00a3\u0001\u001a\u00020\u0003H\u00d6\u0001J\n\u0010\u00a4\u0001\u001a\u00020\u0007H\u00d6\u0001R\u0011\u0010>\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\b?\u0010@R\u0011\u0010\u001b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bA\u0010BR\u0011\u0010\u001d\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bC\u0010BR\u0013\u0010\u001e\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\bD\u0010ER\u0011\u0010\u001c\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bF\u0010BR\u0013\u0010<\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bG\u0010BR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bH\u0010IR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\bJ\u0010KR\u0017\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\bL\u0010MR\u0011\u0010\u001a\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bN\u0010IR\u0011\u00104\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bO\u0010IR\u0011\u00105\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bP\u0010IR\u0011\u00103\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\bQ\u0010IR\u0011\u00106\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bR\u0010BR\u0011\u00107\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bS\u0010BR\u0011\u00108\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bT\u0010BR\u0011\u0010:\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bU\u0010@R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bV\u0010BR\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\bW\u0010MR\u0017\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\bX\u0010MR\u0011\u0010\b\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010@R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010@R\u0011\u0010%\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010@R\u0017\u0010/\u001a\b\u0012\u0004\u0012\u0002000\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\bY\u0010MR\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\bZ\u0010MR\u0017\u00101\u001a\b\u0012\u0004\u0012\u0002020\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b[\u0010MR\u0017\u0010-\u001a\b\u0012\u0004\u0012\u00020.0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\\\u0010MR\u0011\u0010\n\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b]\u0010BR\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b^\u0010_R\u0011\u0010\t\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b`\u0010BR\u0013\u0010\r\u001a\u0004\u0018\u00010\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\ba\u0010ER\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bb\u0010BR\u0017\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\bc\u0010MR\u0017\u0010#\u001a\b\u0012\u0004\u0012\u00020$0\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\bd\u0010MR\u0011\u0010;\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\be\u0010@R\u0011\u0010*\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bf\u0010BR\u0013\u0010&\u001a\u0004\u0018\u00010 \u00a2\u0006\b\n\u0000\u001a\u0004\bg\u0010hR\u0013\u0010\'\u001a\u0004\u0018\u00010\"\u00a2\u0006\b\n\u0000\u001a\u0004\bi\u0010jR\u0011\u0010\u0017\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bk\u0010BR\u0011\u0010)\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bl\u0010@R\u0011\u0010(\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\bm\u0010@R\u0011\u0010n\u001a\u00020\u00058F\u00a2\u0006\u0006\u001a\u0004\bo\u0010@R\u0011\u0010,\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bp\u0010BR\u0011\u00109\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bq\u0010BR\u0011\u0010r\u001a\u00020\u00038F\u00a2\u0006\u0006\u001a\u0004\bs\u0010IR\u0011\u0010+\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\bt\u0010B\u00a8\u0006\u00a5\u0001"}, d2 = {"Lcom/socklet/smritisaathi/ui/family/onboarding/PatientOnboardingUiState;", "", "currentStep", "", "isLoading", "", "error", "", "isComplete", "patientName", "patientAge", "patientGender", "Lcom/socklet/smritisaathi/domain/model/Gender;", "patientPhotoUri", "Landroid/net/Uri;", "patientPhotoUrl", "dementiaStage", "Lcom/socklet/smritisaathi/domain/model/DementiaStage;", "medicalReports", "", "Lcom/socklet/smritisaathi/domain/model/MedicalReport;", "pendingMedicalReports", "Lcom/socklet/smritisaathi/ui/family/onboarding/PendingMedicalReport;", "selectedReportType", "familyContacts", "Lcom/socklet/smritisaathi/domain/model/FamilyContact;", "editingContactIndex", "contactName", "contactRelationship", "contactPhone", "contactPhotoUri", "doctors", "Lcom/socklet/smritisaathi/domain/model/Doctor;", "hospitals", "Lcom/socklet/smritisaathi/domain/model/Hospital;", "registeredDoctors", "Lcom/socklet/smritisaathi/domain/model/User;", "isSearchingDoctors", "selectedDoctor", "selectedHospital", "showAddHospitalDialog", "showAddDoctorDialog", "searchQuery", "wakeTime", "sleepTime", "napTimes", "Lcom/socklet/smritisaathi/domain/model/NapTime;", "mealTimes", "Lcom/socklet/smritisaathi/domain/model/MealTime;", "medicineTimes", "Lcom/socklet/smritisaathi/domain/model/MedicineTime;", "editingNapIndex", "editingMealIndex", "editingMedicineIndex", "emergencyContactName", "emergencyContactPhone", "emergencyContactRelationship", "sosNumber", "enhancedSupportEnabled", "screenPinningPermissionGranted", "createdPatientId", "(IZLjava/lang/String;ZLjava/lang/String;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/Gender;Landroid/net/Uri;Ljava/lang/String;Lcom/socklet/smritisaathi/domain/model/DementiaStage;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/util/List;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroid/net/Uri;Ljava/util/List;Ljava/util/List;Ljava/util/List;ZLcom/socklet/smritisaathi/domain/model/Doctor;Lcom/socklet/smritisaathi/domain/model/Hospital;ZZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Ljava/util/List;Ljava/util/List;IIILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZLjava/lang/String;)V", "canProceed", "getCanProceed", "()Z", "getContactName", "()Ljava/lang/String;", "getContactPhone", "getContactPhotoUri", "()Landroid/net/Uri;", "getContactRelationship", "getCreatedPatientId", "getCurrentStep", "()I", "getDementiaStage", "()Lcom/socklet/smritisaathi/domain/model/DementiaStage;", "getDoctors", "()Ljava/util/List;", "getEditingContactIndex", "getEditingMealIndex", "getEditingMedicineIndex", "getEditingNapIndex", "getEmergencyContactName", "getEmergencyContactPhone", "getEmergencyContactRelationship", "getEnhancedSupportEnabled", "getError", "getFamilyContacts", "getHospitals", "getMealTimes", "getMedicalReports", "getMedicineTimes", "getNapTimes", "getPatientAge", "getPatientGender", "()Lcom/socklet/smritisaathi/domain/model/Gender;", "getPatientName", "getPatientPhotoUri", "getPatientPhotoUrl", "getPendingMedicalReports", "getRegisteredDoctors", "getScreenPinningPermissionGranted", "getSearchQuery", "getSelectedDoctor", "()Lcom/socklet/smritisaathi/domain/model/Doctor;", "getSelectedHospital", "()Lcom/socklet/smritisaathi/domain/model/Hospital;", "getSelectedReportType", "getShowAddDoctorDialog", "getShowAddHospitalDialog", "showEnhancedSupportStep", "getShowEnhancedSupportStep", "getSleepTime", "getSosNumber", "totalSteps", "getTotalSteps", "getWakeTime", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component22", "component23", "component24", "component25", "component26", "component27", "component28", "component29", "component3", "component30", "component31", "component32", "component33", "component34", "component35", "component36", "component37", "component38", "component39", "component4", "component40", "component41", "component42", "component43", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "app_debug"})
public final class PatientOnboardingUiState {
    private final int currentStep = 0;
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String error = null;
    private final boolean isComplete = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String patientName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String patientAge = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.Gender patientGender = null;
    @org.jetbrains.annotations.Nullable
    private final android.net.Uri patientPhotoUri = null;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String patientPhotoUrl = null;
    @org.jetbrains.annotations.NotNull
    private final com.socklet.smritisaathi.domain.model.DementiaStage dementiaStage = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.MedicalReport> medicalReports = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.ui.family.onboarding.PendingMedicalReport> pendingMedicalReports = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String selectedReportType = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.FamilyContact> familyContacts = null;
    private final int editingContactIndex = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String contactName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String contactRelationship = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String contactPhone = null;
    @org.jetbrains.annotations.Nullable
    private final android.net.Uri contactPhotoUri = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.Doctor> doctors = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.Hospital> hospitals = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.User> registeredDoctors = null;
    private final boolean isSearchingDoctors = false;
    @org.jetbrains.annotations.Nullable
    private final com.socklet.smritisaathi.domain.model.Doctor selectedDoctor = null;
    @org.jetbrains.annotations.Nullable
    private final com.socklet.smritisaathi.domain.model.Hospital selectedHospital = null;
    private final boolean showAddHospitalDialog = false;
    private final boolean showAddDoctorDialog = false;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String searchQuery = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String wakeTime = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String sleepTime = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.NapTime> napTimes = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.MealTime> mealTimes = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.socklet.smritisaathi.domain.model.MedicineTime> medicineTimes = null;
    private final int editingNapIndex = 0;
    private final int editingMealIndex = 0;
    private final int editingMedicineIndex = 0;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String emergencyContactName = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String emergencyContactPhone = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String emergencyContactRelationship = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String sosNumber = null;
    private final boolean enhancedSupportEnabled = false;
    private final boolean screenPinningPermissionGranted = false;
    @org.jetbrains.annotations.Nullable
    private final java.lang.String createdPatientId = null;
    
    public PatientOnboardingUiState(int currentStep, boolean isLoading, @org.jetbrains.annotations.Nullable
    java.lang.String error, boolean isComplete, @org.jetbrains.annotations.NotNull
    java.lang.String patientName, @org.jetbrains.annotations.NotNull
    java.lang.String patientAge, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.Gender patientGender, @org.jetbrains.annotations.Nullable
    android.net.Uri patientPhotoUri, @org.jetbrains.annotations.Nullable
    java.lang.String patientPhotoUrl, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.DementiaStage dementiaStage, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MedicalReport> medicalReports, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.ui.family.onboarding.PendingMedicalReport> pendingMedicalReports, @org.jetbrains.annotations.NotNull
    java.lang.String selectedReportType, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.FamilyContact> familyContacts, int editingContactIndex, @org.jetbrains.annotations.NotNull
    java.lang.String contactName, @org.jetbrains.annotations.NotNull
    java.lang.String contactRelationship, @org.jetbrains.annotations.NotNull
    java.lang.String contactPhone, @org.jetbrains.annotations.Nullable
    android.net.Uri contactPhotoUri, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.Doctor> doctors, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.Hospital> hospitals, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.User> registeredDoctors, boolean isSearchingDoctors, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Doctor selectedDoctor, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Hospital selectedHospital, boolean showAddHospitalDialog, boolean showAddDoctorDialog, @org.jetbrains.annotations.NotNull
    java.lang.String searchQuery, @org.jetbrains.annotations.NotNull
    java.lang.String wakeTime, @org.jetbrains.annotations.NotNull
    java.lang.String sleepTime, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.NapTime> napTimes, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MealTime> mealTimes, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MedicineTime> medicineTimes, int editingNapIndex, int editingMealIndex, int editingMedicineIndex, @org.jetbrains.annotations.NotNull
    java.lang.String emergencyContactName, @org.jetbrains.annotations.NotNull
    java.lang.String emergencyContactPhone, @org.jetbrains.annotations.NotNull
    java.lang.String emergencyContactRelationship, @org.jetbrains.annotations.NotNull
    java.lang.String sosNumber, boolean enhancedSupportEnabled, boolean screenPinningPermissionGranted, @org.jetbrains.annotations.Nullable
    java.lang.String createdPatientId) {
        super();
    }
    
    public final int getCurrentStep() {
        return 0;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getError() {
        return null;
    }
    
    public final boolean isComplete() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPatientName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getPatientAge() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.Gender getPatientGender() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.net.Uri getPatientPhotoUri() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getPatientPhotoUrl() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DementiaStage getDementiaStage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MedicalReport> getMedicalReports() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.ui.family.onboarding.PendingMedicalReport> getPendingMedicalReports() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSelectedReportType() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.FamilyContact> getFamilyContacts() {
        return null;
    }
    
    public final int getEditingContactIndex() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getContactName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getContactRelationship() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getContactPhone() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.net.Uri getContactPhotoUri() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.Doctor> getDoctors() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.Hospital> getHospitals() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.User> getRegisteredDoctors() {
        return null;
    }
    
    public final boolean isSearchingDoctors() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.Doctor getSelectedDoctor() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.Hospital getSelectedHospital() {
        return null;
    }
    
    public final boolean getShowAddHospitalDialog() {
        return false;
    }
    
    public final boolean getShowAddDoctorDialog() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWakeTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSleepTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.NapTime> getNapTimes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MealTime> getMealTimes() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MedicineTime> getMedicineTimes() {
        return null;
    }
    
    public final int getEditingNapIndex() {
        return 0;
    }
    
    public final int getEditingMealIndex() {
        return 0;
    }
    
    public final int getEditingMedicineIndex() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getEmergencyContactName() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getEmergencyContactPhone() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getEmergencyContactRelationship() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSosNumber() {
        return null;
    }
    
    public final boolean getEnhancedSupportEnabled() {
        return false;
    }
    
    public final boolean getScreenPinningPermissionGranted() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getCreatedPatientId() {
        return null;
    }
    
    public final boolean getCanProceed() {
        return false;
    }
    
    public final boolean getShowEnhancedSupportStep() {
        return false;
    }
    
    public final int getTotalSteps() {
        return 0;
    }
    
    public PatientOnboardingUiState() {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.DementiaStage component10() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MedicalReport> component11() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.ui.family.onboarding.PendingMedicalReport> component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component13() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.FamilyContact> component14() {
        return null;
    }
    
    public final int component15() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component16() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component17() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component18() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.net.Uri component19() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.Doctor> component20() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.Hospital> component21() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.User> component22() {
        return null;
    }
    
    public final boolean component23() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.Doctor component24() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.socklet.smritisaathi.domain.model.Hospital component25() {
        return null;
    }
    
    public final boolean component26() {
        return false;
    }
    
    public final boolean component27() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component28() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component29() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component30() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.NapTime> component31() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MealTime> component32() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.socklet.smritisaathi.domain.model.MedicineTime> component33() {
        return null;
    }
    
    public final int component34() {
        return 0;
    }
    
    public final int component35() {
        return 0;
    }
    
    public final int component36() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component37() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component38() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component39() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component40() {
        return null;
    }
    
    public final boolean component41() {
        return false;
    }
    
    public final boolean component42() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component43() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.domain.model.Gender component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.net.Uri component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.socklet.smritisaathi.ui.family.onboarding.PatientOnboardingUiState copy(int currentStep, boolean isLoading, @org.jetbrains.annotations.Nullable
    java.lang.String error, boolean isComplete, @org.jetbrains.annotations.NotNull
    java.lang.String patientName, @org.jetbrains.annotations.NotNull
    java.lang.String patientAge, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.Gender patientGender, @org.jetbrains.annotations.Nullable
    android.net.Uri patientPhotoUri, @org.jetbrains.annotations.Nullable
    java.lang.String patientPhotoUrl, @org.jetbrains.annotations.NotNull
    com.socklet.smritisaathi.domain.model.DementiaStage dementiaStage, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MedicalReport> medicalReports, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.ui.family.onboarding.PendingMedicalReport> pendingMedicalReports, @org.jetbrains.annotations.NotNull
    java.lang.String selectedReportType, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.FamilyContact> familyContacts, int editingContactIndex, @org.jetbrains.annotations.NotNull
    java.lang.String contactName, @org.jetbrains.annotations.NotNull
    java.lang.String contactRelationship, @org.jetbrains.annotations.NotNull
    java.lang.String contactPhone, @org.jetbrains.annotations.Nullable
    android.net.Uri contactPhotoUri, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.Doctor> doctors, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.Hospital> hospitals, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.User> registeredDoctors, boolean isSearchingDoctors, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Doctor selectedDoctor, @org.jetbrains.annotations.Nullable
    com.socklet.smritisaathi.domain.model.Hospital selectedHospital, boolean showAddHospitalDialog, boolean showAddDoctorDialog, @org.jetbrains.annotations.NotNull
    java.lang.String searchQuery, @org.jetbrains.annotations.NotNull
    java.lang.String wakeTime, @org.jetbrains.annotations.NotNull
    java.lang.String sleepTime, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.NapTime> napTimes, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MealTime> mealTimes, @org.jetbrains.annotations.NotNull
    java.util.List<com.socklet.smritisaathi.domain.model.MedicineTime> medicineTimes, int editingNapIndex, int editingMealIndex, int editingMedicineIndex, @org.jetbrains.annotations.NotNull
    java.lang.String emergencyContactName, @org.jetbrains.annotations.NotNull
    java.lang.String emergencyContactPhone, @org.jetbrains.annotations.NotNull
    java.lang.String emergencyContactRelationship, @org.jetbrains.annotations.NotNull
    java.lang.String sosNumber, boolean enhancedSupportEnabled, boolean screenPinningPermissionGranted, @org.jetbrains.annotations.Nullable
    java.lang.String createdPatientId) {
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
}