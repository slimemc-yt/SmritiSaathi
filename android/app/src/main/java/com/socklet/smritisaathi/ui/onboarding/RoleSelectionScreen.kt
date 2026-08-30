package com.socklet.smritisaathi.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.socklet.smritisaathi.domain.localization.getAppStringsForLanguage
import com.socklet.smritisaathi.domain.model.UserRole
import com.socklet.smritisaathi.ui.components.RoleButton
import com.socklet.smritisaathi.ui.theme.*

@Composable
fun RoleSelectionScreen(
    onPatientSelected: () -> Unit,
    onFamilySelected: () -> Unit,
    onDoctorSelected: () -> Unit,
    onDemoSelected: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val strings = getAppStringsForLanguage("en") // Base role strings

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(Dimensions.ScreenPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = strings.whoAreYouTitle,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(Dimensions.Space8))

            Text(
                text = strings.whoAreYouSubtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space24))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(Dimensions.Space16)
        ) {
            RoleButton(
                title = strings.rolePatientTitle,
                description = strings.rolePatientDesc,
                icon = Icons.Default.Person,
                backgroundColor = PatientColor,
                contentColor = OnPrimary,
                onClick = {
                    viewModel.setSelectedRole(UserRole.PATIENT)
                    onPatientSelected()
                },
                modifier = Modifier.fillMaxWidth()
            )

            RoleButton(
                title = strings.roleFamilyTitle,
                description = strings.roleFamilyDesc,
                icon = Icons.Default.FamilyRestroom,
                backgroundColor = FamilyColor,
                contentColor = OnPrimary,
                onClick = {
                    viewModel.setSelectedRole(UserRole.FAMILY)
                    onFamilySelected()
                },
                modifier = Modifier.fillMaxWidth()
            )

            RoleButton(
                title = strings.roleDoctorTitle,
                description = strings.roleDoctorDesc,
                icon = Icons.Default.MedicalServices,
                backgroundColor = DoctorColor,
                contentColor = OnPrimary,
                onClick = {
                    viewModel.setSelectedRole(UserRole.DOCTOR)
                    onDoctorSelected()
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.Space24))

        // Instant Hackathon Demo Mode Action
        OutlinedButton(
            onClick = onDemoSelected,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.ButtonHeight),
            shape = RoundedCornerShape(Dimensions.ButtonCornerRadius)
        ) {
            Icon(Icons.Default.Bolt, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(Dimensions.Space8))
            Text(
                text = "⚡ Launch Demo Mode (Judge / Quick Test)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
